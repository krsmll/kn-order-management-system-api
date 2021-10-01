package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.services.CustomerService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@NoArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Customer> getAllCustomers() {
        return this.customerService.getAll();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Customer getCustomer(@PathVariable long id) {
        return this.customerService.getCustomer(id);
    }
}
