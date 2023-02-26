package com.jiashn.springbootproject.realize;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangjs
 * @date 2023-02-27 5:44
 * @description: ApplicationContext的实现
 */
public class ApplicationContextRealize {

    public static void main(String[] args) {
        //testClassPathXmlApplicationContext();
        //testFileSystemXmlApplicationContext();
        //beanFactoryLoadXML();
        //testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();
    }

    public static void beanFactoryLoadXML(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        System.out.println("----读取xml之前-------");
        for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
            System.out.println("----:"+beanDefinitionName);
        }
        System.out.println("----读取xml之后-------");
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("spring_bean.xml"));
        for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
            System.out.println(">>>>>:"+beanDefinitionName);
        }
    }

    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring_bean.xml");
        for (String definitionName : context.getBeanDefinitionNames()) {
            System.out.println("ClassPathXmlApplicationContext-类："+definitionName);
        }
        System.out.println(">>>>>>>>:"+context.getBean(Bean2.class).getBean1());
    }

    private static void testFileSystemXmlApplicationContext(){
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("src\\main\\resources\\spring_bean.xml");
        for (String definitionName : context.getBeanDefinitionNames()) {
            System.out.println("FileSystemXmlApplicationContext-类："+definitionName);
        }
        System.out.println("FileSystemXmlApplicationContext->>>>>>>>:"+context.getBean(Bean2.class).getBean1());
    }

    private static void testAnnotationConfigApplicationContext(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);
        for (String definitionName : context.getBeanDefinitionNames()) {
            System.out.println("AnnotationConfigApplicationContext-类："+definitionName);
        }
        System.out.println("AnnotationConfigApplicationContext->>>>>>>>:"+context.getBean(Bean2.class).getBean1());
    }

    private static void testAnnotationConfigServletWebServerApplicationContext(){
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig{
        //创建容器
        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }
        //SpringMvc web请求中心
        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }

        //将DispatcherServlet注入到容器中
        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }

        //创建控制层，实现测试
        @Bean("/testContext.do")
        public Controller testController(){
           return (request, response) -> {
               response.getWriter().println("hello,word！");
               return null;
           };
        }
    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }
        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }

    static class Bean2 {
        private Bean1 bean1;

        public Bean1 getBean1() {
            return bean1;
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }
    }
    static class Bean1 {
    }
}
