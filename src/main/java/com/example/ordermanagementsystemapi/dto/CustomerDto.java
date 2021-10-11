package com.example.ordermanagementsystemapi.dto;

import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String fullName;
    private String email;
    private String telephone;
    private String registrationCode;
    private List<OrderDto> orders;

    public CustomerDto(Customer o) {
        this.id = o.getId();
        this.fullName = o.getFullName();
        this.email = o.getEmail();
        this.telephone = o.getTelephone();
        this.registrationCode = o.getRegistrationCode();
        this.orders = o.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
