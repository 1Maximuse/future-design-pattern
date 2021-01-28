package com.blibli.belajar.design.patterns.facade;

import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class FacadeApplication {

    public static interface AddressService {
        void save(String customerId, String address, String city);

        void remove(String customerId, String address, String city);

        void update(String customerId, String address, String city);
    }

    public static class AddressController {

        @Setter
        private AddressService addressService;

        public void saveAdress(String customerId, String address, String city) {
            System.out.println("controller");
            addressService.save(customerId, address, city);
        }
    }

    public static class AddressServiceImplPostgres implements AddressService {

        @Override
        public void save(String customerId, String address, String city) {
            System.out.println("Complicated implementation postgres");
        }

        @Override
        public void remove(String customerId, String address, String city) {
            System.out.println("Complicated implementation postgres");
        }

        @Override
        public void update(String customerId, String address, String city) {
            System.out.println("Complicated implementation postgres");
        }
    }

    public static class AddressServiceImplMongo implements AddressService {

        @Override
        public void save(String customerId, String address, String city) {
            System.out.println("Complicated implementation mongo");
        }

        @Override
        public void remove(String customerId, String address, String city) {
            System.out.println("Complicated implementation mongo");
        }

        @Override
        public void update(String customerId, String address, String city) {
            System.out.println("Complicated implementation mongo");
        }
    }

    @SpringBootApplication
    public static class Application {

//        @Bean
        public AddressService addressServicePostgre() {
            return new AddressServiceImplPostgres();
        }

        @Bean
        public AddressService addressServiceMongo() {
            return new AddressServiceImplMongo();
        }

        @Bean
        public AddressController addressController(AddressService addressService) {
            AddressController controller = new AddressController();
            controller.setAddressService(addressService);
            return controller;
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class);
        AddressController controller = context.getBean(AddressController.class);

        controller.saveAdress("1", "Indonesia", "Jakarta");
    }
}
