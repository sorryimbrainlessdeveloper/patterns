package org.example.flyweight.service;

import lombok.RequiredArgsConstructor;
import org.example.flyweight.entity.Category;
import org.example.flyweight.entity.CategoryData;
import org.example.flyweight.exception.CategoryNotFoundException;
import org.example.flyweight.flyweight_implementation.CategoryDataFlyweightFactory;
import org.example.flyweight.repository.CategoryDataRepository;
import org.example.flyweight.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import static org.example.flyweight.utils.StaticMessages.CATEGORY_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDataRepository categoryDataRepository;

    public Category getCategoryById(Long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND.formatted(categoryId)));

        final CategoryData categoryData = CategoryDataFlyweightFactory
                .getCategory(category.getId(), categoryDataRepository);
        category.setCategoryData(categoryData);

        return category;
    }

    public int getCacheSize() {
        return CategoryDataFlyweightFactory.getCacheSize();
    }
}
