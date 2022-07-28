package org.penguinframework.test.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.penguinframework.test.type.Platform;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DatabaseMeta {
    Platform platform() default Platform.DEFAULT;
}
