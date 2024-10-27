package org.example.flyweight.flyweight_implementation;

import org.example.flyweight.entity.CategoryData;
import org.example.flyweight.exception.CategoryDataNotFoundException;
import org.example.flyweight.repository.CategoryDataRepository;
import org.example.flyweight.utils.StaticMessages;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryDataFlyweightFactory {
    private static final Map<Long, CategoryData> categoryDataCache = new HashMap<>();

    public static CategoryData getCategory(Long id, CategoryDataRepository repository) {
        return categoryDataCache.computeIfAbsent(
                id,
                key -> repository
                        .findById(key)
                        .orElseThrow(() -> new CategoryDataNotFoundException(StaticMessages.CATEGORY_DATA_NOT_FOUND.formatted(key)))
        );
    }

    public static int getCacheSize() {
        return categoryDataCache.size();
    }
}
