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

import com.anand.dto.PageabaleResponse;
import com.anand.dto.ProductDTO;
import com.anand.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO product = productService.createProduct(productDTO);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<PageabaleResponse<ProductDTO>> getAll(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir) 
	{
		
		PageabaleResponse<ProductDTO> dto=productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
		ProductDTO productById = productService.getProductById(productId);
		return new ResponseEntity<>(productById, HttpStatus.OK);
	}

	@GetMapping("/title/{title}")
	public ResponseEntity<ProductDTO> getProductByTitle(@PathVariable String title) {
		ProductDTO productByTitle = productService.getProductByTitle(title);
		return new ResponseEntity<>(productByTitle, HttpStatus.OK);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductDTO> updateUser(@RequestBody ProductDTO productDTO, @PathVariable String productId) {
		ProductDTO updateProduct = productService.updateProduct(productDTO, productId);
		return new ResponseEntity<>(updateProduct, HttpStatus.OK);
	}

}
