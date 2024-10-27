package org.example.flyweight.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "category_data_id")
    private CategoryData categoryData;

    public Category() {
    }

    public Category(CategoryData categoryData) {
        this.categoryData = categoryData;
    }
}

