package com.project.foodapp.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
