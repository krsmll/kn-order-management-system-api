package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.dto.ProductDto;
import com.example.ordermanagementsystemapi.model.Product;
import com.example.ordermanagementsystemapi.services.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@NoArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ProductDto> getAllProducts() {
        return this.productService.getAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ProductDto getProductById(@PathVariable long id) {
        return new ProductDto(this.productService.getProduct(id));
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto, HttpServletRequest request, HttpServletResponse response) {
        Product createdProduct = this.productService.createProduct(productDto);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdProduct.getId()).toString());

        return new ProductDto(createdProduct);
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody ProductDto productDto, HttpServletRequest request, HttpServletResponse response) {
        Product updatedProduct = this.productService.updateProduct(productDto);
        response.setHeader("Location", request.getRequestURL().append("/").append(updatedProduct.getId()).toString());

        return new ProductDto(updatedProduct);
    }
}
