package org.penguinframework.example.test.database.assertion.inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.bean.assertion.BeanAssertion;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.type.Platform;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(PenguinExtension.class)
class BeanAssertionInjectTest {
    @Nested
    @SpringBootTest(classes = Application.class)
    @AutoConfigureTestDatabase(replace = Replace.NONE)
    @ActiveProfiles("h2")
    @DatabaseMeta(platform = Platform.H2)
    @DisplayName("DataSourceが存在する環境でBeanAssertionのオブジェクトの注入の検証")
    class InjectWithDataSource {
        @Load
        private BeanAssertion beanAssertionWithAnnotation;

        private BeanAssertion beanAssertionWithoutAnnotation;

        @Load
        private Object objectWithAnnotation;

        @Test
        @DisplayName("@Loadアノテーションが指定されたBeanAssertion型のフィールドが初期化されていること")
        void withAnnotationField() {
            Assertions.assertNotNull(this.beanAssertionWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されていないBeanAssertion型のフィールドが初期化されていないこと")
        void withoutAnnotationField() {
            Assertions.assertNull(this.beanAssertionWithoutAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたBeanAssertion型以外のフィールドが初期化されていないこと")
        void withAnnotationFieldNotSupportClass() {
            Assertions.assertNull(this.objectWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたBeanAssertion型の引数が初期化されていること")
        void withAnnotationParameter(@Load BeanAssertion beanAssertionParameterWithAnnotation) {
            Assertions.assertNotNull(beanAssertionParameterWithAnnotation);
        }
    }

    @Nested
    @DisplayName("DataSourceが存在しない環境でBeanAssertionのオブジェクトの注入の検証")
    class InjectWithoutDatasource {
        @Load
        private BeanAssertion beanAssertionWithAnnotation;

        private BeanAssertion beanAssertionWithoutAnnotation;

        @Load
        private Object objectWithAnnotation;

        @Test
        @DisplayName("@Loadアノテーションが指定されたBeanAssertion型のフィールドが初期化されていること")
        void withAnnotationField() {
            Assertions.assertNotNull(this.beanAssertionWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されていないBeanAssertion型のフィールドが初期化されていないこと")
        void withoutAnnotationField() {
            Assertions.assertNull(this.beanAssertionWithoutAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたBeanAssertion型以外のフィールドが初期化されていないこと")
        void withAnnotationFieldNotSupportClass() {
            Assertions.assertNull(this.objectWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたBeanAssertion型の引数が初期化されていること")
        void withAnnotationParameter(@Load BeanAssertion beanAssertionParameterWithAnnotation) {
            Assertions.assertNotNull(beanAssertionParameterWithAnnotation);
        }
    }
}
