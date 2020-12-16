package com.baomidou.mybatisplus.samples.crud.config;

import java.util.List;
import java.util.function.BiFunction;

/**
 * mybatis resultMap中的对象实例构建策略.
 *
 * @author maxD
 */
@FunctionalInterface
public interface MybatisObjectSupplier<R> extends BiFunction<List<Class<?>>, List<Object>, R> {

}
