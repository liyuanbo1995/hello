package com.xinba.flow.config;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimbaLog {
    String description() default "";
}
