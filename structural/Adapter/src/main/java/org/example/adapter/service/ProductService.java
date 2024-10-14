package org.example.adapter.service;

import org.example.adapter.model.ExternalProduct;
import org.example.adapter.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public Product getProductFromExternalAPI() {
        ExternalProduct externalProduct = new ExternalProduct(
                "external name",
                101.1,
                "external product details"
        );
        return new ProductAdapter(externalProduct);
    }
}
