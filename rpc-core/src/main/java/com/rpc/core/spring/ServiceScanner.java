package com.rpc.core.spring;

import com.rpc.core.annotation.Service;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @auther yuegb
 * @Date: 2020-10-14 10:37
 * @Description:
 */
public class ServiceScanner extends ClassPathBeanDefinitionScanner {

    public ServiceScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(Service.class));
        return super.doScan(basePackages);
    }
}
