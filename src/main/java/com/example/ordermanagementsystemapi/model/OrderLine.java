package com.example.ordermanagementsystemapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_line")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Order order;

    @ManyToOne()
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public OrderLine(OrderLine o) {
        this.id = o.id;
        this.order = o.order;
        this.product = o.product;
        this.quantity = o.quantity;
    }
}
