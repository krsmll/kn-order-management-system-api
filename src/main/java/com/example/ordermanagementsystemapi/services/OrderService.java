package com.example.ordermanagementsystemapi.services;

import com.example.ordermanagementsystemapi.dto.OrderDto;
import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import com.example.ordermanagementsystemapi.model.Order;
import com.example.ordermanagementsystemapi.model.OrderLine;
import com.example.ordermanagementsystemapi.repositories.OrderRepository;
import com.example.ordermanagementsystemapi.repositories.specifications.OrderSpecifications;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class OrderService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new NotFoundException(Order.class.getSimpleName(), id));
    }

    public List<Order> getOrdersByCustomer(long id) {
        return customerService.getCustomer(id).getOrders();
    }

    public List<Order> getOrdersByProduct(long id) {


        return orderRepository.findAll(
                OrderSpecifications.ordersByProduct(productService.getProduct(id))
        );
    }

    public List<Order> getOrdersByDate(Date date) {
        return orderRepository.findAll(
                OrderSpecifications.ordersByDate(date)
        );
    }

    public Order createOrder(OrderDto order) {
        Order newO = new Order(null, customerService.getCustomer(order.getCustomerId()), order.getSubmissionDate(), List.of());

        newO.setOrderLines(
                order.getOrderLines().stream().map(it ->
                        new OrderLine(null, newO, this.productService.getProduct(it.getProduct().getId()), it.getQuantity())
                ).collect(Collectors.toList())
        );
        return orderRepository.save(newO);
    }

    @Transactional
    public Order updateOrder(OrderDto order) {
        Order toReturn = entityManager.find(Order.class, order.getId());
        toReturn.setCustomer(customerService.getCustomer(order.getCustomerId()));
        toReturn.setSubmissionDate(order.getSubmissionDate());

        List<OrderLine> toRemove = toReturn.getOrderLines()
                .stream()
                .filter(it -> order.getOrderLines()
                        .stream()
                        .noneMatch(that -> it.getId().equals(that.getId())))
                .collect(Collectors.toList());

        List<OrderLine> toAdd = order.getOrderLines()
                .stream()
                .filter(it -> toReturn.getOrderLines()
                        .stream()
                        .noneMatch(that -> it.getId().equals(that.getId())))
                .map(it -> new OrderLine(it.getId(), toReturn, productService.getProduct(it.getProduct().getId()), it.getQuantity()))
                .collect(Collectors.toList());

        toReturn.getOrderLines().removeAll(toRemove);
        toReturn.getOrderLines().addAll(toAdd);

        entityManager.flush();
        entityManager.clear();

        return entityManager.find(Order.class, order.getId());
    }
}
