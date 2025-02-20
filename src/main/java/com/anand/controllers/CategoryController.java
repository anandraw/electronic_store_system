package com.anand.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.anand.dto.CategoryDTO;
import com.anand.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// Create a new category
	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		log.info("Creating a new category with title: {}", categoryDTO.getTitle());
		CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}

	// Update an existing category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
			@PathVariable String categoryId) {
		log.info("Updating category with ID: {}", categoryId);
		CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	}

	// Delete a category by ID
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable String categoryId) {
		log.info("Deleting category with ID: {}", categoryId);
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
	}

	// Get all categories
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		log.info("Fetching all categories");
		List<CategoryDTO> categories = categoryService.getAllCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	// Get a category by ID
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String categoryId) {
		log.info("Fetching category with ID: {}", categoryId);
		CategoryDTO category = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	// Get a category by name (or keyword)
	@GetMapping("/search")
	public ResponseEntity<CategoryDTO> getCategoryByName(@RequestParam String keyword) {
		log.info("Searching for category with keyword: {}", keyword);
		CategoryDTO category = categoryService.getCategoryByName(keyword);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

}
