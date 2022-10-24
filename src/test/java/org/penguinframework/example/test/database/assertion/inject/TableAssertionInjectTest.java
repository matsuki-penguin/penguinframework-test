package org.penguinframework.example.test.database.assertion.inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.database.assertion.TableAssertion;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.type.Platform;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(PenguinExtension.class)
class TableAssertionInjectTest {
    @Nested
    @SpringBootTest(classes = Application.class)
    @AutoConfigureTestDatabase(replace = Replace.NONE)
    @ActiveProfiles("h2")
    @DatabaseMeta(platform = Platform.H2)
    @DisplayName("DataSourceが存在する環境でTableAssertionのオブジェクトの注入の検証")
    class InjectWithDataSource {
        @Load
        private TableAssertion tableAssertionWithAnnotation;

        private TableAssertion tableAssertionWithoutAnnotation;

        @Load
        private Object objectWithAnnotation;

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型のフィールドが初期化されていること")
        void withAnnotationField() {
            Assertions.assertNotNull(this.tableAssertionWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されていないTableAssertion型のフィールドが初期化されていないこと")
        void withoutAnnotationField() {
            Assertions.assertNull(this.tableAssertionWithoutAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型以外のフィールドが初期化されていないこと")
        void withAnnotationFieldNotSupportClass() {
            Assertions.assertNull(this.objectWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型の引数が初期化されていること")
        void withAnnotationParameter(@Load TableAssertion tableAssertionParameterWithAnnotation) {
            Assertions.assertNotNull(tableAssertionParameterWithAnnotation);
        }
    }

    @Nested
    @DisplayName("DataSourceが存在しない環境でTableAssertionのオブジェクトの注入の検証")
    class InjectWithoutDatasource {
        @Load
        private TableAssertion tableAssertionWithAnnotation;

        private TableAssertion tableAssertionWithoutAnnotation;

        @Load
        private Object objectWithAnnotation;

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型のフィールドが初期化されていないこと")
        void withAnnotationField() {
            Assertions.assertNull(this.tableAssertionWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されていないTableAssertion型のフィールドが初期化されていないこと")
        void withoutAnnotationField() {
            Assertions.assertNull(this.tableAssertionWithoutAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型以外のフィールドが初期化されていないこと")
        void withAnnotationFieldNotSupportClass() {
            Assertions.assertNull(this.objectWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型の引数が初期化されていないこと")
        void withAnnotationParameter(@Load TableAssertion tableAssertionParameterWithAnnotation) {
            Assertions.assertNull(tableAssertionParameterWithAnnotation);
        }
    }
}
