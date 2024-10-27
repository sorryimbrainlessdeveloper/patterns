package org.example.flyweight.exception;

public class CategoryDataNotFoundException extends RuntimeException {
    public CategoryDataNotFoundException() {
    }

    public CategoryDataNotFoundException(String message) {
        super(message);
    }
}
