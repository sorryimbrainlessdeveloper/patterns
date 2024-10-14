package org.example.adapter.model;

public class ExternalProduct {
    private final String productName;
    private final double cost;
    private final String details;

    public ExternalProduct(String productName, double cost, String details) {
        this.productName = productName;
        this.cost = cost;
        this.details = details;
    }

    public String getProductName() {
        return productName;
    }

    public double getCost() {
        return cost;
    }

    public String getDetails() {
        return details;
    }
}
