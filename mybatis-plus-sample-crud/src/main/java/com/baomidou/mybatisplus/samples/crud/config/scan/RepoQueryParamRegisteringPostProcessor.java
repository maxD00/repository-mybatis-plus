package com.baomidou.mybatisplus.samples.crud.config.scan;

import cn.hutool.core.lang.ClassScanner;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.samples.crud.config.AbstractQueryParam;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * {@link BeanFactoryPostProcessor} 通过包扫描注册RepoQueryParam.
 *
 * @author maxD
 */
class RepoQueryParamRegisteringPostProcessor implements BeanFactoryPostProcessor {
    private final Set<String> packagesToScan;

    public RepoQueryParamRegisteringPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        MapperBuilderAssistant assistant = new MapperBuilderAssistant(new MybatisConfiguration(), "");
        for (String packageToScan : packagesToScan) {
            Optional.ofNullable(ClassScanner.scanPackageBySuper(packageToScan, AbstractQueryParam.class))
                    .orElseGet(Collections::emptySet)
                    .forEach(paramClass -> TableInfoHelper.initTableInfo(assistant, paramClass));
        }

    }
}
