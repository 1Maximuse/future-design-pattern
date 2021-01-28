package com.blibli.belajar.design.patterns.prototype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class PrototypeApplication {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Customer {
        private String id;
        private String name;
        private String category;
        private Integer discount;
    }

    @SpringBootApplication
    public static class Configuration {

        @Bean("standardCustomer")
        @Scope("prototype")
        public Customer standardCustomer() {
            return Customer.builder()
                    .category("standard")
                    .discount(10)
                    .build();
        }

        // Default bean name: function name
        @Bean("premiumCustomer")
        @Scope("prototype")
        public Customer premiumCustomer() {
            return Customer.builder()
                    .category("premium")
                    .discount(50)
                    .build();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Configuration.class);

        Customer customerStandard1 = context.getBean("standardCustomer", Customer.class);
        Customer customerStandard2 = context.getBean("standardCustomer", Customer.class);
        Customer customerStandard3 = context.getBean("standardCustomer", Customer.class);

        Customer premiumCustomer1 = context.getBean("premiumCustomer", Customer.class);
        Customer premiumCustomer2 = context.getBean("premiumCustomer", Customer.class);
        Customer premiumCustomer3 = context.getBean("premiumCustomer", Customer.class);

        System.out.println(customerStandard1 == customerStandard2);
        System.out.println(customerStandard2 == customerStandard3);

        System.out.println(customerStandard1);
        System.out.println(customerStandard2);
        System.out.println(customerStandard3);

        System.out.println(premiumCustomer1);
        System.out.println(premiumCustomer2);
        System.out.println(premiumCustomer3);
    }
}
