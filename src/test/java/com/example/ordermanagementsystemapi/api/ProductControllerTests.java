package com.example.ordermanagementsystemapi.api;

import com.example.ordermanagementsystemapi.api.data.TestData;
import com.example.ordermanagementsystemapi.dto.ProductDto;
import com.example.ordermanagementsystemapi.model.Product;
import com.example.ordermanagementsystemapi.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.example.ordermanagementsystemapi"})
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    private final TestData data = new TestData();

    @Test
    public void getAllProducts_success() throws Exception {
        Mockito.when(productService.getAll()).thenReturn(List.of(data.productA, data.productB));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getProductById_success() throws Exception {
        Mockito.when(productService.getProduct(2L)).thenReturn(data.productB);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(data.productB.getId()), Long.class));
    }

    @Test
    public void updateProduct_success() throws Exception {
        Product old = data.productB;
        Product newProduct = new Product(old.getId(), old.getName(), old.getSkuCode(), old.getUnitPrice(), old.getOrderLines());
        newProduct.setUnitPrice(100d);

        Mockito.when(productService.updateProduct(Mockito.any(ProductDto.class))).thenReturn(newProduct);

        ProductDto dto = new ProductDto(newProduct);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.unitPrice", is(newProduct.getUnitPrice()), Double.class));
    }

    @Test
    public void createProduct_success() throws Exception {
        Product newProduct = new Product(null, "Epic Product!!!!", "429514620515", 10000d, List.of());
        Product expected = new Product(3L, "Epic Product!!!!", "429514620515", 10000d, List.of());

        Mockito.when(productService.createProduct(Mockito.any(ProductDto.class))).thenReturn(expected);

        ProductDto dto = new ProductDto(newProduct);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(expected.getId()), Long.class));
    }
}
