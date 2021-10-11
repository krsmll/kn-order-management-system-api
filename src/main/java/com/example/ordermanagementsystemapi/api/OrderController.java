package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.dto.OrderDto;
import com.example.ordermanagementsystemapi.model.Order;
import com.example.ordermanagementsystemapi.services.OrderService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@NoArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<OrderDto> getAllOrders() {
        return this.orderService.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    OrderDto getOrderById(@PathVariable long id) {
        return new OrderDto(this.orderService.getOrder(id));
    }

    @RequestMapping(value = "/customer/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<OrderDto> getOrdersByCustomer(@PathVariable long id) {
        return this.orderService.getOrdersByCustomer(id).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/product/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<OrderDto> getOrdersByProduct(@PathVariable long id) {
        return this.orderService.getOrdersByProduct(id).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/date/{date}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<OrderDto> getOrdersByDate(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return this.orderService.getOrdersByDate(date).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderDto order, HttpServletRequest request, HttpServletResponse response) {
        Order createdOrder = this.orderService.createOrder(order);

        response.setHeader("Location", request.getRequestURL().append("/").append(createdOrder.getId()).toString());

        return new OrderDto(createdOrder);
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public OrderDto updateOrder(@RequestBody OrderDto order, HttpServletRequest request, HttpServletResponse response) {
        Order updatedOrder = this.orderService.updateOrder(order);
        response.setHeader("Location", request.getRequestURL().append("/").append(updatedOrder.getId()).toString());

        return new OrderDto(updatedOrder);
    }
}
