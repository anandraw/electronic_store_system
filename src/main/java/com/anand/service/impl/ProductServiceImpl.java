package com.anand.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anand.dto.PageabaleResponse;
import com.anand.dto.ProductDTO;
import com.anand.entities.Product;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.helper.Helper;
import com.anand.repository.ProductRepo;
import com.anand.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		String pId = UUID.randomUUID().toString();
		productDTO.setId(pId);
		Product product = modelMapper.map(productDTO, Product.class);
		product.setAddedDate(new Date());
		Product saveProduct = productRepo.save(product);
		return modelMapper.map(saveProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, String productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product is not found with this ID: " + productId));
		product.setTitle(productDTO.getTitle());
		product.setDecscription(productDTO.getDecscription());
		product.setPrice(productDTO.getPrice());
		product.setQuantity(productDTO.getQuantity());
		product.setDiscountPrice(productDTO.getDiscountPrice());
		product.setLive(productDTO.isLive());
		product.setStock(productDTO.isStock());
		ProductDTO dto = modelMapper.map(product, ProductDTO.class);
		return dto;
	}

	@Override
	public ProductDTO getProductById(String productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product is not found with this ID: " + productId));
		ProductDTO dto = modelMapper.map(product, ProductDTO.class);
		return dto;
	}

//	@Override
//	public List<ProductDTO> getAllProduct() {
//		List<Product> products = productRepo.findAll();
//		List<ProductDTO> pDto = products.stream().map(product -> modelMapper.map(products, ProductDTO.class))
//				.collect(Collectors.toList());
//		return pDto;
//	}

	@Override
	public ProductDTO getProductByTitle(String title) {
		Product product = productRepo.findByProductTitle(title)
				.orElseThrow(() -> new ResourceNotFoundException("Product is not found with this ID: " + title));
		ProductDTO dto = modelMapper.map(product, ProductDTO.class);
		return dto;
	}

	@Override
	public void deleteProduct(String productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product is not found with this ID: " + productId));
		productRepo.delete(product);
	}

	@Override
	public PageabaleResponse<ProductDTO> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))? (Sort.by(sortBy).descending()): (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize,sort);
		Page<Product> product = productRepo.findAll(pageable);
		PageabaleResponse pageableResponse = Helper.getPageableResponse(product, ProductDTO.class);
		return pageableResponse;
		
	}
}
