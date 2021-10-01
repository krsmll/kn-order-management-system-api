package com.example.ordermanagementsystemapi.repositories;

import com.example.ordermanagementsystemapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
