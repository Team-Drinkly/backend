package com.drinkhere.infras3.aop.Interface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당 어노테이션을 DTO에 추가하면
 * filePath 필드를 Presigned URL로 변경
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransformToPresignedUrl {
}