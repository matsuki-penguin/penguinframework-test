package org.penguinframework.test.extension;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.dbunit.dataset.DataSetException;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstances;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.bean.BeanLoader;
import org.penguinframework.test.bean.annotation.BeanValueSource;
import org.penguinframework.test.bean.assertion.BeanAssertion;
import org.penguinframework.test.database.TableLoader;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.database.assertion.TableAssertion;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * JUnit 5のExtension Model
 */
public class PenguinExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, ParameterResolver {

    /**
     * テストクラスの初期化処理。
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // データソースの取得
        ApplicationContext springContext = SpringExtension.getApplicationContext(context);

        try {
            DataSource dataSource = new TransactionAwareDataSourceProxy(springContext.getBean(DataSource.class));
            this.setDataSource(context, dataSource);

            // トランザクションマネージャの生成
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
            this.setTransactionManager(context, transactionManager);
        } catch (NoSuchBeanDefinitionException e) {
        }
    }

    /**
     * テストメソッドの初期化処理。
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        // フィールドに値を設定
        BeanLoader.initFields(context.getRequiredTestInstance());

        DataSource dataSource = this.getDataSource(context);
        if (dataSource != null) {
            Method testMethod = context.getRequiredTestMethod();
            TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            TransactionStatus transactionStatus = this.getTransactionManager(context)
                    .getTransaction(transactionDefinition);
            this.setTransactionStatus(context, transactionStatus);

            // テストクラスに指定されたDatabaseMetaアノテーションを取得
            DatabaseMeta databaseMeta = context.getRequiredTestClass().getAnnotation(DatabaseMeta.class);
            if (databaseMeta == null) {
                databaseMeta = context.getRequiredTestInstances().getEnclosingInstances().stream().map(Object::getClass)
                        .map(clazz -> clazz.getAnnotation(DatabaseMeta.class)).filter(Objects::nonNull).findFirst()
                        .orElse(null);
            }

            // TableValueSourceアノテーションで指定されているExcelファイルをデータベースにClean & Insert
            Connection connection = dataSource.getConnection();
            TableLoader.load(testMethod, connection, databaseMeta);

            // TableAssertionのインスタンス注入
            Class<?> testClass = context.getRequiredTestClass();
            TestInstances instances = context.getRequiredTestInstances();
            TableAssertion tableAssertion = new TableAssertion(connection, testClass, databaseMeta);
            this.setTableAssertion(context, tableAssertion);
            for (Object instance : instances.getAllInstances()) {
                Field[] fields = FieldUtils.getFieldsListWithAnnotation(instance.getClass(), Load.class).stream()
                        .filter(f -> f.getType() == TableAssertion.class).toArray(Field[]::new);
                for (Field field : fields) {
                    FieldUtils.writeField(field, instance, tableAssertion, true);
                }
            }

            // BeanAssertionのインスタンス注入
            BeanAssertion beanAssertion = new BeanAssertion(testClass);
            this.setBeanAssertion(context, beanAssertion);
            for (Object instance : instances.getAllInstances()) {
                Field[] fields = FieldUtils.getFieldsListWithAnnotation(instance.getClass(), Load.class).stream()
                        .filter(f -> f.getType() == BeanAssertion.class).toArray(Field[]::new);
                for (Field field : fields) {
                    FieldUtils.writeField(field, instance, beanAssertion, true);
                }
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (this.getDataSource(context) != null) {
            this.getTransactionManager(context).rollback(this.getTransactionStatus(context));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getTestClass().isPresent()
                && (parameterContext.isAnnotated(BeanValueSource.class) || (parameterContext.isAnnotated(Load.class)
                        && (parameterContext.getParameter().getType() == TableAssertion.class
                                || parameterContext.getParameter().getType() == BeanAssertion.class)));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        if (parameterContext.isAnnotated(BeanValueSource.class)) {
            try {
                return BeanLoader.load(parameterContext.getParameter());
            } catch (ReflectiveOperationException | DataSetException | IOException e) {
                throw new ParameterResolutionException("Failed to create bean instance.", e);
            }
        } else if (parameterContext.isAnnotated(Load.class)) {
            if (parameterContext.getParameter().getType() == TableAssertion.class) {
                return this.getTableAssertion(extensionContext);
            } else if (parameterContext.getParameter().getType() == BeanAssertion.class) {
                return this.getBeanAssertion(extensionContext);
            }
        }

        return null;
    }

    private void setDataSource(ExtensionContext context, DataSource dataSource) {
        Store store = this.getTestClassStore(context);
        store.put(DataSource.class, dataSource);
    }

    private DataSource getDataSource(ExtensionContext context) {
        Store store = this.getTestClassStore(context);
        return store.get(DataSource.class, DataSource.class);
    }

    private void setTransactionManager(ExtensionContext context, DataSourceTransactionManager transactionManager) {
        Store store = this.getTestClassStore(context);
        store.put(DataSourceTransactionManager.class, transactionManager);
    }

    private DataSourceTransactionManager getTransactionManager(ExtensionContext context) {
        Store store = this.getTestClassStore(context);
        return store.get(DataSourceTransactionManager.class, DataSourceTransactionManager.class);
    }

    private void setTransactionStatus(ExtensionContext context, TransactionStatus transactionStatus) {
        Store store = this.getTestMethodStore(context);
        store.put(TransactionStatus.class, transactionStatus);
    }

    private TransactionStatus getTransactionStatus(ExtensionContext context) {
        Store store = this.getTestMethodStore(context);
        return store.get(TransactionStatus.class, TransactionStatus.class);
    }

    private void setTableAssertion(ExtensionContext context, TableAssertion tableAssertion) {
        Store store = this.getTestMethodStore(context);
        store.put(TableAssertion.class, tableAssertion);
    }

    private TableAssertion getTableAssertion(ExtensionContext context) {
        Store store = this.getTestMethodStore(context);
        return store.get(TableAssertion.class, TableAssertion.class);
    }

    private void setBeanAssertion(ExtensionContext context, BeanAssertion beanAssertion) {
        Store store = this.getTestMethodStore(context);
        store.put(BeanAssertion.class, beanAssertion);
    }

    private BeanAssertion getBeanAssertion(ExtensionContext context) {
        Store store = this.getTestMethodStore(context);
        return store.get(BeanAssertion.class, BeanAssertion.class);
    }

    private Store getTestClassStore(ExtensionContext context) {
        return context.getStore(Namespace.create(context.getRequiredTestClass()));
    }

    private Store getTestMethodStore(ExtensionContext context) {
        return context.getStore(Namespace.create(context.getRequiredTestClass(), context.getRequiredTestMethod()));
    }
}
