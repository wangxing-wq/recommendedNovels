package com.novels.common.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动spring-web-common,让spring识别
 * @author 22343
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CommonImportSelector.class})
public @interface EnableCommon {
}
