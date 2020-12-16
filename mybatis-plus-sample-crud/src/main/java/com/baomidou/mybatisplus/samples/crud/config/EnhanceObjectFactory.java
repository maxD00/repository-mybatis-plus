package com.baomidou.mybatisplus.samples.crud.config;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link ObjectFactory}的实现,用于对特定Class配置其构造策略.
 * TODO 有bug存在,在resultMap中的对象使用延迟加载时,mybatis会对其使用javassist进行代理,通过代理类生成实例后,再将被代理对象的属性数据合并.
 * 实例化代理类时,使用了Class.getConstructor(Class<?>[] paramTypes)寻找构造器,但是代理类中可能不存在这个构造器(因为被代理类可能并不需要
 * 存在与参数类型相匹配的构造器),所以导致代理类实例化出错.
 * 详见{@link org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory}中的crateProxy方法.
 * 修改方式,可在生成代理类前,创建一个默认构造器(如果被代理类中没有的话),使用与参数类型相匹配的代理类构造器实例化,并没有意义(之后对象的属性数据会被合并).
 *
 * @author maxD
 */
@Component
class EnhanceObjectFactory extends DefaultObjectFactory {

    private static final long serialVersionUID = 3667633320347458888L;

    /**
     * 储存Class与构造策略的对应关系.
     */
    private static final Map<Class, MybatisObjectSupplier> CREATE_STRATEGY_MAP = new HashMap<>();

    @Override
    public <T> T create(Class<T> type) {
        return this.create(type, null, null);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        MybatisObjectSupplier<T> supplier = CREATE_STRATEGY_MAP.get(type);
        if (supplier != null) {
            return supplier.apply(constructorArgTypes, constructorArgs);
        }
        return super.create(type, constructorArgTypes, constructorArgs);
    }


    /**
     * 增加构造策略.
     *
     * @param c        对象类型对应的Class对象
     * @param strategy 策略
     * @param <T>      针对的对象类型
     */
    <T> void addCreateStrategy(Class<T> c, MybatisObjectSupplier<T> strategy) {
        CREATE_STRATEGY_MAP.put(c, strategy);
    }
}
