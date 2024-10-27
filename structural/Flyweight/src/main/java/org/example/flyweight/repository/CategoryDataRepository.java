package org.example.flyweight.repository;

import org.example.flyweight.entity.CategoryData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDataRepository extends JpaRepository<CategoryData, Long> {
}
