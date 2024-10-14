package org.example.adapter.service;

import org.example.adapter.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {
    private final ProductService productService = new ProductService();

    @Test
    void testGetProductFromExternalApi() {
        final Product product = productService.getProductFromExternalAPI();
        assertEquals("external name", product.getName());
        assertEquals(BigDecimal.valueOf(101.1), product.getPrice());
        assertEquals("external product details", product.getDescription());
    }
}