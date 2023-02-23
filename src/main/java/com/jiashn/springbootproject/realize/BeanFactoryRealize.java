package com.jiashn.springbootproject.realize;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author jiangjs
 * @date 2023-02-24 5:43
 * @description: 实现BeanFactory创建、加载类
 */
public class BeanFactoryRealize {
    public static void main(String[] args) {
        //创建BeanFactory的实现类DefaultListableBeanFactory
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        //bean的定义（即需要定义当前bean的描述信息，如：class,scope:单例或多例’,始化,销毁）
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        //factory实现方法创建
        factory.registerBeanDefinition("config",beanDefinition);
        System.out.println("----------没有后处理器-------------");
        for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        //添加常用后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(factory);
        System.out.println("----------加入后处理器-------------");
        for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        //使用BeanFactory后处理器：补充一些bean的定义
        //通过类型获取后处理器，Spring提供了一个BeanFactoryPostProcessor
        factory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(processor ->{
            System.out.println(processor);
            processor.postProcessBeanFactory(factory);
        });
        System.out.println("----------使用后处理器-------------");
        for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        System.out.println("打印bean2:"+factory.getBean(Bean1.class).getBean2());
        //bean处理器：针对bean的生命周期的各个阶段提供扩展,该类型BeanPostProcessor
        factory.getBeansOfType(BeanPostProcessor.class).values().forEach(factory::addBeanPostProcessor);
        System.out.println("添加bean后处理,打印bean2:"+factory.getBean(Bean1.class).getBean2());
    }

    @Configuration
    static class Config{

        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    @Slf4j
    static class Bean1 {
        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }
        public Bean1() {
            log.debug("构造 Bean1()");
        }
    }

    @Slf4j
    static class Bean2 {
        public Bean2() {
            log.debug("构造 Bean2()");
        }
    }

}
