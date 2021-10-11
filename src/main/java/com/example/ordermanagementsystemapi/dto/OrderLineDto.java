package com.example.ordermanagementsystemapi.dto;

import com.example.ordermanagementsystemapi.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLineDto {
    private Long id;
    private Long orderId;
    private ProductDto product;
    private Integer quantity;

    public OrderLineDto(OrderLine o) {
        this.id = o.getId();
        this.orderId = o.getOrder().getId();
        this.product = new ProductDto(o.getProduct());
        this.quantity = o.getQuantity();
    }
}