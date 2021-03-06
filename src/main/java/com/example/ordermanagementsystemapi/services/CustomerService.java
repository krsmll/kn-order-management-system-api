package com.example.ordermanagementsystemapi.services;

import com.example.ordermanagementsystemapi.dto.CustomerDto;
import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.repositories.CustomerRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@NoArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomer(long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);

        return customer.orElseThrow(() -> new NotFoundException(Customer.class.getSimpleName(), id));
    }

    public Customer createCustomer(CustomerDto dto) {
        Customer newC = new Customer(
                dto.getId(), dto.getFullName(), dto.getEmail(), dto.getTelephone(), dto.getRegistrationCode(), List.of()
        );

        return this.customerRepository.save(newC);
    }
}
