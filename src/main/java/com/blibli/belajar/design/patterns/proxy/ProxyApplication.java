package com.blibli.belajar.design.patterns.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

public class ProxyApplication {

    public static interface CustomerService {
        void save(String id, String name);

        void update(String id, String name);
    }

    public static class CustomerServiceImpl implements CustomerService {
        @Override
        public void save(String id, String name) {
            System.out.println("Save to database");
        }

        @Override
        public void update(String id, String name) {
            System.out.println("Update database");
        }
    }

    public static class LogInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            try {
                System.out.println("Call method " + methodInvocation.getMethod());
                return methodInvocation.proceed();
            } finally {
                System.out.println("Finished method " + methodInvocation.getMethod());
            }
        }
    }

    @SpringBootApplication
    public static class Application {

        @Bean
        public ProxyFactoryBean customerService() {
            ProxyFactoryBean factoryBean = new ProxyFactoryBean();
            factoryBean.setInterfaces(CustomerService.class);
            factoryBean.setTarget(new CustomerServiceImpl());
            factoryBean.setInterceptorNames("logInterceptor");
            return factoryBean;
        }

        @Bean("logInterceptor")
        public LogInterceptor logInterceptor() {
            return new LogInterceptor();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class);

        CustomerService customerService = context.getBean(CustomerService.class);
        customerService.save("123", "Emmanuel");
        customerService.update("1234", "Emmanuel");
    }
}
