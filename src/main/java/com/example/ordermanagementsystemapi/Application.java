package com.example.ordermanagementsystemapi;

import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            Customer customer = new Customer();
            customer.setFullName("Kristjan Mill");
            customer.setEmail("kristjanmmm@gmail.com");
            customer.setTelephone("53403290");
            customer.setRegistrationCode("18j4ruioj");

            customerRepository.save(customer);
        };
    }
}
