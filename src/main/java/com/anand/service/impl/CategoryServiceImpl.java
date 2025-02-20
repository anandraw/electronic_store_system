package com.anand.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anand.dto.CategoryDTO;
import com.anand.entities.Category;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.repository.CategoryRepo;
import com.anand.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		log.info("Creating new category with title: {}", categoryDTO.getTitle());
		String cateId = UUID.randomUUID().toString();
		categoryDTO.setId(cateId);
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category cateDTO = categoryRepo.save(category);
		log.info("Category created successfully with ID: {}", cateDTO.getId());
		return modelMapper.map(cateDTO, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!" + categoryId));
		category.setDescription(categoryDTO.getDescription());
		category.setTitle(categoryDTO.getTitle());
		Category savedCategory = categoryRepo.save(category);
		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!:" + categoryId));
		categoryRepo.delete(category);
		log.info("Category deleted successfully with ID: {}", categoryId);

	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryById(String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!:" + categoryId));
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryByName(String keyword) {

		Category category = categoryRepo.findByTitleContainingIgnoreCase(keyword)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found with keyword: " + keyword));
		return modelMapper.map(category, CategoryDTO.class);
	}

}
