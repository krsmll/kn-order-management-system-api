package com.example.ordermanagementsystemapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()
    private Customer customer;

    @Column(name = "submission_date", nullable = false)
    private Date submissionDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderLine> orderLines;
}
