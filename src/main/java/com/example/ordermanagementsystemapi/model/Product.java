package com.example.ordermanagementsystemapi.model;

import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product", orphanRemoval = true)
    private List<OrderLine> orderLines;
}
