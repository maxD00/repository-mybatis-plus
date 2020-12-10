package com.baomidou.mybatisplus.samples.crud.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解到仓库查询参数DTO的属性上,构建此属性的sql脚本时,使用value值作为起始的sql脚本.
 *
 * @author maxD
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WhereStartWithSql {
    String value() default "";
}
