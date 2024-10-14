package org.example.adapter.controller;

import org.example.adapter.model.Product;
import org.example.adapter.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/external")
    public ResponseEntity<Product> getExternalProduct() {
        final Product product = productService.getProductFromExternalAPI();
        return ResponseEntity.ok(product);
    }
}
