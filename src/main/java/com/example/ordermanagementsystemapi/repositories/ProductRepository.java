package com.example.ordermanagementsystemapi.repositories;

import com.example.ordermanagementsystemapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
