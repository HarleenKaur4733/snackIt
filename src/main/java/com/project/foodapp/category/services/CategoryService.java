package com.project.foodapp.category.services;

import java.util.List;

import com.project.foodapp.category.dtos.CategoryDTO;
import com.project.foodapp.response.Response;

public interface CategoryService {

    Response<CategoryDTO> addCategory(CategoryDTO categoryDTO);

    Response<CategoryDTO> updateCategory(CategoryDTO categoryDTO);

    Response<CategoryDTO> getCategoryById(Long id);

    Response<List<CategoryDTO>> getAllCategories();

    Response<?> deleteCategory(Long id);
}