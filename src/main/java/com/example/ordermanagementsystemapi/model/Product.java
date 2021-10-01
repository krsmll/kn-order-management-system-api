package com.example.ordermanagementsystemapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<OrderLine> orderLines;
}
