package org.example.flyweight.service;

import jakarta.transaction.Transactional;
import org.example.flyweight.entity.Category;
import org.example.flyweight.entity.CategoryData;import org.example.flyweight.exception.CategoryNotFoundException;
import org.example.flyweight.repository.CategoryDataRepository;
import org.example.flyweight.repository.CategoryRepository;
import org.example.flyweight.utils.StaticMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDataRepository categoryDataRepository;

    @BeforeEach
    void setUp() {
        CategoryData categoryData1 = new CategoryData("Electronics");
        CategoryData categoryData2 = new CategoryData("Books");

        categoryDataRepository.save(categoryData1);
        categoryDataRepository.save(categoryData2);

        Category category1 = new Category(categoryData1);
        Category category2 = new Category(categoryData2);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    @Test
    @Transactional
    void testCategoryCaching() {
        // The first request to the category with id 1 - should be loaded from the database and cached
        final Category category1 = categoryService.getCategoryById(1L);
        assertNotNull(category1);
        assertEquals("Electronics", category1.getCategoryData().getName());

        // Repeated request to the same category - must be taken from cache
        final Category category1Cached = categoryService.getCategoryById(1L);
        assertNotNull(category1Cached);
        assertEquals("Electronics", category1Cached.getCategoryData().getName());

        // Checking cache size because one unique category was loaded
        assertEquals(1, categoryService.getCacheSize());
    }

    @Test
    @Transactional
    void testMultiplyCategoryCaching() {
        // Request both categories (requests must be included in the database)
        final Category category1 = categoryService.getCategoryById(1L);
        final Category category2 = categoryService.getCategoryById(2L);

        assertNotNull(category1);
        assertNotNull(category2);

        assertEquals("Electronics", category1.getCategoryData().getName());
        assertEquals("Books", category2.getCategoryData().getName());

        // Check that both categories are added to the cache
        assertEquals(2, categoryService.getCacheSize());

        // We re-query the same categories that should already be in the cache
        final Category category1Cached = categoryService.getCategoryById(1L);
        final Category category2Cached = categoryService.getCategoryById(2L);

        assertNotNull(category1Cached);
        assertNotNull(category2Cached);

        // We check that the cache has not been updated and contains only two entries
        assertEquals(2, categoryService.getCacheSize());
    }

    @Test
    @Transactional
    void testCategoryNotFoundException() {
        // check correct processing not existing category
        final String expected = StaticMessages.CATEGORY_NOT_FOUND.formatted(3);
        final CategoryNotFoundException actual = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(3L)
        );
        assertEquals(expected, actual.getMessage());
    }
}