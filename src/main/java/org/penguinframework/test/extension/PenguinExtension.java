package org.penguinframework.test.extension;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.penguinframework.test.database.TableLoader;
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
public class PenguinExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

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
        this.dataSource = new TransactionAwareDataSourceProxy(springContext.getBean(DataSource.class));

        // トランザクションマネージャの生成
        this.transactionManager = new DataSourceTransactionManager(this.dataSource);
    }

    /**
     * テストメソッドの初期化処理。
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Optional<Method> testMethod = context.getTestMethod();
        if (this.dataSource != null && testMethod.isPresent()) {
            TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            this.transactionStatus = this.transactionManager.getTransaction(transactionDefinition);

            // TableValueSourceアノテーションで指定されているExcelファイルをデータベースにClean & Insert
            TableLoader.load(testMethod.get(), this.dataSource.getConnection());
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (this.dataSource != null) {
            this.transactionManager.rollback(this.transactionStatus);
        }
    }
}
