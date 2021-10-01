package com.example.ordermanagementsystemapi.services;

import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import com.example.ordermanagementsystemapi.model.Order;
import com.example.ordermanagementsystemapi.repositories.OrderRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new NotFoundException(Order.class.getName(), id));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }


    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
