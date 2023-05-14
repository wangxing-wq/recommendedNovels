package com.novels.common.annotation;

import org.slf4j.event.Level;

import java.lang.annotation.*;

/**
 * 日志辅助注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogHelper {

    /**
     * 默认是否格式化json
     */
    boolean isPretty() default false;

    /**
     * 默认记录日志的最大长度
     */
    int maxLength() default 1000;

    /**
     * 默认启用的日志级别
     */
    Level level() default Level.INFO;

    /**
     * 是否记录方法签名,默认true
     */
    boolean methodSignature() default true;

    /**
     * 是否记录参数,默认true
     */
    boolean parameter() default true;

    /**
     * 是否记录返回值,默认true
     */
    boolean returnValue() default true;

    /**
     * 是否处理异常,默认true
     */
    boolean exception() default true;
    /**
     * 是否启用
     */
    boolean enable() default true;
}
