package org.example.flyweight.flyweight_implementation;

import org.example.flyweight.entity.Category;
import org.example.flyweight.exception.CategoryNotFoundException;
import org.example.flyweight.repository.CategoryRepository;
import org.example.flyweight.utils.StaticMessages;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryFlyweightFactory {
    private static final Map<Long, Category> categoryCache = new HashMap<>();

    public static Category getCategory(Long id, CategoryRepository repository) {
        return categoryCache.computeIfAbsent(
                id,
                key -> repository
                        .findById(key)
                        .orElseThrow(() -> new CategoryNotFoundException(StaticMessages.CATEGORY_NOT_FOUND.formatted(key)))
        );
    }

    public static int getCacheSize() {
        return categoryCache.size();
    }
}
