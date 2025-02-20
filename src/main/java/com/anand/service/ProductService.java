package com.anand.service;

import java.util.List;

import com.anand.dto.PageabaleResponse;
import com.anand.dto.ProductDTO;

public interface ProductService {

	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO updateProduct(ProductDTO productDTO, String productId);

	ProductDTO getProductById(String productId);

	PageabaleResponse<ProductDTO> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

	ProductDTO getProductByTitle(String title);

	void deleteProduct(String productId);
}
