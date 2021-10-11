package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.api.data.TestData;
import com.example.ordermanagementsystemapi.dto.OrderDto;
import com.example.ordermanagementsystemapi.model.Order;
import com.example.ordermanagementsystemapi.model.OrderLine;
import com.example.ordermanagementsystemapi.services.OrderService;
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

import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.example.ordermanagementsystemapi"})
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private OrderService orderService;

    private final TestData data = new TestData();

    @Test
    public void getAllOrders_success() throws Exception {
        Mockito.when(orderService.getAll()).thenReturn(List.of(data.orderA, data.orderB, data.orderC, data.orderD));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getOrderById_success() throws Exception {
        Mockito.when(orderService.getOrder(2L)).thenReturn(data.orderB);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(data.orderB.getId()), Long.class));
    }

    @Test
    public void getOrdersByDate_success() throws Exception {
        Mockito.when(orderService.getOrdersByDate(Date.valueOf("2021-06-26"))).thenReturn(List.of(data.orderA));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/date/2021-06-26"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].submissionDate", is("2021-06-26")));
    }

    @Test
    public void getOrdersByCustomer_success() throws Exception {
        Mockito.when(orderService.getOrdersByCustomer(1L)).thenReturn(List.of(data.orderA, data.orderB, data.orderC));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1L, 2L, 3L), Long.class).exists());
    }

    @Test
    public void getOrdersByProduct_success() throws Exception {
        Mockito.when(orderService.getOrdersByProduct(2L)).thenReturn(List.of(data.orderA, data.orderD));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/product/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1L, 4L), Long.class).exists());
    }

    @Test
    public void updateQuantityOfProduct_success() throws Exception {
        Order updatedOrderD = new Order(data.orderD);
        List<OrderLine> updatedOrderLines = updatedOrderD.getOrderLines().stream().map(OrderLine::new).collect(Collectors.toList());
        updatedOrderLines.get(0).setQuantity(4); // Changing quantity from 1 to 4.
        updatedOrderD.setOrderLines(updatedOrderLines);

        OrderDto dto = new OrderDto(updatedOrderD);

        Mockito.when(orderService.updateOrder(Mockito.any(OrderDto.class))).thenReturn(updatedOrderD);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.orderLines[0].quantity", is(4)));
    }

    @Test
    public void updateOrder_success() throws Exception {
        Order updatedOrderD = new Order(data.orderA);
        updatedOrderD.getOrderLines().remove(updatedOrderD.getOrderLines().stream().filter(it -> it.getId().equals(1L)).findAny().get());
        updatedOrderD.getOrderLines().get(0).setQuantity(1000);
        OrderDto dto = new OrderDto(updatedOrderD);

        Mockito.when(orderService.updateOrder(Mockito.any(OrderDto.class))).thenReturn(updatedOrderD);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.orderLines[0].id", is(2L), Long.class))
                .andExpect(jsonPath("$.orderLines[0].quantity", is(1000)));
    }

    @Test
    public void createOrder_success() throws Exception {
        Order newOrder = new Order(null, data.customerB, Date.valueOf("2021-10-10"), List.of());
        List<OrderLine> orderLines = List.of(
                new OrderLine(null, newOrder, data.productA, 10),
                new OrderLine(null, newOrder, data.productB, 5)
        );
        newOrder.setOrderLines(orderLines);

        OrderDto newOrderDto = new OrderDto(newOrder);
        Order expected = new Order(newOrder);
        expected.setId(5L);

        AtomicLong id = new AtomicLong(6);
        expected.setOrderLines(expected.getOrderLines().stream().peek(it -> it.setId(id.getAndIncrement())).collect(Collectors.toList()));


        Mockito.when(orderService.createOrder(Mockito.any(OrderDto.class))).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newOrderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(expected.getId()), Long.class))
                .andExpect(jsonPath("$.orderLines", hasSize(2)))
                .andExpect(jsonPath("$.orderLines[*].id", containsInAnyOrder(6L, 7L), Long.class).exists());

    }
}
