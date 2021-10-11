package com.example.ordermanagementsystemapi.dto;

import com.example.ordermanagementsystemapi.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long customerId;
    private Date submissionDate;
    private List<OrderLineDto> orderLines;

    public OrderDto(Order o) {
        this.id = o.getId();
        this.customerId = o.getCustomer().getId();
        this.submissionDate = o.getSubmissionDate();
        this.orderLines = o.getOrderLines().stream().map(OrderLineDto::new).collect(Collectors.toList());
    }
}
