package org.example.flyweight.service;

import lombok.RequiredArgsConstructor;
import org.example.flyweight.entity.Category;
import org.example.flyweight.flyweight_implementation.CategoryFlyweightFactory;
import org.example.flyweight.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category getCategoryById(Long id) {
        return CategoryFlyweightFactory.getCategory(id, repository);
    }

    public int getCacheSize() {
        return CategoryFlyweightFactory.getCacheSize();
    }
}
