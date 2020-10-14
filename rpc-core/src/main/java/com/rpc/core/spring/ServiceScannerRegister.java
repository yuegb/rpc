package com.rpc.core.spring;

import com.rpc.core.annotation.Service;
import com.rpc.core.annotation.ServiceScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR;

/**
 * @author dell
 * @Date: 2020-10-14 10:06
 * @Description:自定义扫描器
 */
@Slf4j
public class ServiceScannerRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware, BeanFactoryAware {

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    private BeanFactory beanFactory;

    public ServiceScannerRegister() {

    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes serviceScanAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ServiceScan.class.getName()));
        if (null == serviceScanAttrs) {
            return;
        }
        Set<String> basePackages = new HashSet<>();
        // 获取到所有属性的值
        basePackages.addAll(Arrays.stream(serviceScanAttrs.getStringArray("basePackage")).filter(StringUtils::hasText).collect(Collectors.toSet()));
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(annotationMetadata.getClassName()));
        }
        this.registerBeanDefinitions(basePackages, serviceScanAttrs, beanDefinitionRegistry);
    }

    /**
     * 注册beandefinitions到spring容器
     * @param basePackages
     * @param attributes
     * @param beanDefinitionRegistry
     * @return
     */
    private Set<BeanDefinitionHolder> registerBeanDefinitions(Set<String> basePackages, AnnotationAttributes attributes, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 创建扫描器
        ServiceScanner scanner = new ServiceScanner(beanDefinitionRegistry);
        BeanNameGenerator beanNameGenerator = resolveBeanNameGenerator(beanDefinitionRegistry);
        scanner.setBeanNameGenerator(beanNameGenerator);
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        return scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    /**
     * 生成BeanNameGenerator
     * @param beanDefinitionRegistry
     * @return
     */
    private BeanNameGenerator resolveBeanNameGenerator(BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanNameGenerator beanNameGenerator = null;
        if (beanDefinitionRegistry instanceof SingletonBeanRegistry) {
            SingletonBeanRegistry singletonBeanRegistry = SingletonBeanRegistry.class.cast(beanDefinitionRegistry);
            beanNameGenerator = (BeanNameGenerator) singletonBeanRegistry.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
        }
        if (beanNameGenerator == null) {
            if (log.isInfoEnabled()) {
                log.info("BeanNameGenerator bean can't be found in BeanFactory with name [" + CONFIGURATION_BEAN_NAME_GENERATOR + "]");
                log.info("BeanNameGenerator will be a instance of " + AnnotationBeanNameGenerator.class.getName() + " , it maybe a potential problem on bean name generation.");
            }
            beanNameGenerator = new AnnotationBeanNameGenerator();
        }
        return beanNameGenerator;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
