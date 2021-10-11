package com.example.ordermanagementsystemapi.model;

import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Customer customer;

    @Column(name = "submission_date", nullable = false)
    private Date submissionDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private List<OrderLine> orderLines;

    public Order(Order order) {
        this.id = order.getId();
        this.customer = order.getCustomer();
        this.submissionDate = order.getSubmissionDate();
        this.orderLines = order.getOrderLines().stream().map(OrderLine::new).collect(Collectors.toList());
    }
}
