package com.baomidou.mybatisplus.samples.crud.config;

/**
 * mybatis resultMap对象构造策略叠加器.
 *
 * @author maxD
 */
public class MybatisObjectCreateStrategyAdder {
    private final EnhanceObjectFactory enhanceObjectFactory;

    protected MybatisObjectCreateStrategyAdder(EnhanceObjectFactory enhanceObjectFactory) {
        this.enhanceObjectFactory = enhanceObjectFactory;
    }

    public <T> MybatisObjectCreateStrategyAdder add(Class<T> c, MybatisObjectSupplier<T> supplier) {
        enhanceObjectFactory.addCreateStrategy(c, supplier);
        return this;
    }
}
