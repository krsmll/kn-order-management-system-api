package com.example.ordermanagementsystemapi.services;

import com.example.ordermanagementsystemapi.dto.ProductDto;
import com.example.ordermanagementsystemapi.exceptions.NotFoundException;
import com.example.ordermanagementsystemapi.model.Product;
import com.example.ordermanagementsystemapi.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new NotFoundException(Product.class.getSimpleName(), id));
    }

    public Product createProduct(ProductDto product) {
        Product created = new Product(null, product.getName(), product.getSkuCode(), product.getUnitPrice(), List.of());

        return productRepository.save(created);
    }

    public Product updateProduct(ProductDto product) {
        Product updated = new Product(product.getId(), product.getName(), product.getSkuCode(), product.getUnitPrice(), List.of());

        return productRepository.save(updated);
    }
}
