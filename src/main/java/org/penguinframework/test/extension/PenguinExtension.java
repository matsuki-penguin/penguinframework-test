package org.penguinframework.test.extension;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.dbunit.dataset.DataSetException;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.penguinframework.test.bean.BeanLoader;
import org.penguinframework.test.bean.annotation.BeanValueSource;
import org.penguinframework.test.database.TableLoader;
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

    /** データソース。 */
    private DataSource dataSource;

    /** トランザクションマネージャ。 */
    private DataSourceTransactionManager transactionManager;

    /** トランザクションステータス。 */
    private TransactionStatus transactionStatus;

    /**
     * テストクラスの初期化処理。
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // データソースの取得
        ApplicationContext springContext = SpringExtension.getApplicationContext(context);

        try {
            this.dataSource = new TransactionAwareDataSourceProxy(springContext.getBean(DataSource.class));

            // トランザクションマネージャの生成
            this.transactionManager = new DataSourceTransactionManager(this.dataSource);
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

        if (this.dataSource != null) {
            Method testMethod = context.getRequiredTestMethod();
            TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            this.transactionStatus = this.transactionManager.getTransaction(transactionDefinition);

            // TableValueSourceアノテーションで指定されているExcelファイルをデータベースにClean & Insert
            TableLoader.load(testMethod, this.dataSource.getConnection());
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (this.dataSource != null) {
            this.transactionManager.rollback(this.transactionStatus);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getTestClass().isPresent() && parameterContext.isAnnotated(BeanValueSource.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        try {
            return BeanLoader.load(parameterContext.getParameter());
        } catch (ReflectiveOperationException | DataSetException | IOException e) {
            // TODO
            throw new ParameterResolutionException("", e);
        }
    }
}
