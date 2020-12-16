package com.baomidou.mybatisplus.samples.crud.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.apache.ibatis.session.Configuration;


/**
 * 自定义mybatis配置.
 * 可增加mybatis resultMap中的对象构造策略.
 *
 * @author maxD
 */
public abstract class AbstractMybatisConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        EnhanceObjectFactory enhanceObjectFactory = new EnhanceObjectFactory();
        addObjectCreateStrategy(new MybatisObjectCreateStrategyAdder(enhanceObjectFactory));
        configuration.setObjectFactory(enhanceObjectFactory);
    }

    /**
     * 增加resultMap的对象构造策略.
     *
     * @param adder 构造策略叠加器
     */
    public abstract void addObjectCreateStrategy(MybatisObjectCreateStrategyAdder adder);
}
