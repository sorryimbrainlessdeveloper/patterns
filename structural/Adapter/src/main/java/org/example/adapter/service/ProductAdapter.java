package org.example.adapter.service;

import org.example.adapter.model.ExternalProduct;
import org.example.adapter.model.Product;

import java.math.BigDecimal;

public class ProductAdapter implements Product {

    private final ExternalProduct externalProduct;

    public ProductAdapter(ExternalProduct externalProduct) {
        this.externalProduct = externalProduct;
    }

    @Override
    public String getName() {
        return externalProduct.getProductName();
    }

    @Override
    public BigDecimal getPrice() {
        return BigDecimal.valueOf(externalProduct.getCost());
    }

    @Override
    public String getDescription() {
        return externalProduct.getDetails();
    }
}
