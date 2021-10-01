package com.example.ordermanagementsystemapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_line")
public class OrderLine {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()
    private Order order;

    @ManyToOne()
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
