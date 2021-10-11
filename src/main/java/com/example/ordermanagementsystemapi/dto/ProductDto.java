package com.example.ordermanagementsystemapi.dto;

import com.example.ordermanagementsystemapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String skuCode;
    private Double unitPrice;

    public ProductDto(Product o) {
        this.id = o.getId();
        this.name = o.getName();
        this.skuCode = o.getSkuCode();
        this.unitPrice = o.getUnitPrice();
    }
}
