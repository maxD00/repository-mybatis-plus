package com.baomidou.mybatisplus.samples.crud.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对request param名进行 蛇形 到 驼峰的转换.
 * 注意:转换出来的参数会追加进requestparams中,一般情况下参数数量会有增加.
 *
 * @author maxD
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RequestParamSnakeCaseConverter.class)
public @interface EnableRequestParamSnake {
}
