package org.example.adapter.service;

import org.example.adapter.model.ExternalProduct;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductAdapterTest {

    @Test
    void testProductAdapter() {
        ExternalProduct externalProduct = new ExternalProduct(
                "external name",
                101.1,
                "external product details"
        );

        ProductAdapter adapter = new ProductAdapter(externalProduct);

        assertEquals("external name", adapter.getName());
        assertEquals(BigDecimal.valueOf(101.1), adapter.getPrice());
        assertEquals("external product details", adapter.getDescription());
    }
}