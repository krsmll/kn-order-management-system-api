package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.dto.CustomerDto;
import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.services.CustomerService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@NoArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<CustomerDto> getAllCustomers() {
        List<Customer> all = this.customerService.getAll();
        return all.stream().map(CustomerDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CustomerDto getCustomerById(@PathVariable long id) {
        Customer customer = this.customerService.getCustomer(id);
        return new CustomerDto(customer);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody CustomerDto customer, HttpServletRequest request, HttpServletResponse response) {
        Customer createdCustomer = this.customerService.createCustomer(customer);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdCustomer.getId()).toString());

        return new CustomerDto(createdCustomer);
    }
}
