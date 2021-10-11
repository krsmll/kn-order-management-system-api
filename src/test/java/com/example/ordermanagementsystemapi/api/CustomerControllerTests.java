package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.api.data.TestData;
import com.example.ordermanagementsystemapi.dto.CustomerDto;
import com.example.ordermanagementsystemapi.model.Customer;
import com.example.ordermanagementsystemapi.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.example.ordermanagementsystemapi"})
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;

    private final TestData data = new TestData();

    @Test
    public void getAllCustomers_success() throws Exception {
        Mockito.when(customerService.getAll()).thenReturn(List.of(data.customerA, data.customerB));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCustomerById_success() throws Exception {
        Mockito.when(customerService.getCustomer(1)).thenReturn(data.customerA);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(data.customerA.getId()), Long.class));
    }

    @Test
    public void createCustomer_success() throws Exception {
        Customer createdCustomer = new Customer(null, "Mark Robber", "mr.epic@coolemail.com", "23453789", "dcvgb90j89sdf", List.of());
        Customer expected = new Customer(3L, createdCustomer.getFullName(), createdCustomer.getEmail(), createdCustomer.getTelephone(), createdCustomer.getRegistrationCode(), createdCustomer.getOrders());

        CustomerDto dto = new CustomerDto(createdCustomer);

        Mockito.when(customerService.createCustomer(Mockito.any(CustomerDto.class))).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(expected.getId()), Long.class));
    }
}
