package com.anand.service;

import java.util.List;
import com.anand.dto.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, String categoryId);
	void deleteCategory(String categoryId);
	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryById(String categoryId);
	CategoryDTO getCategoryByName(String keyword);
	
}
