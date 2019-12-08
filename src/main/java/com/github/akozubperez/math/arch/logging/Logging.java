package com.github.akozubperez.math.arch.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logging {

    boolean disabled() default false;

    boolean logErrors() default false;

    boolean logParameters() default false;

    boolean logReturns() default false;
}
