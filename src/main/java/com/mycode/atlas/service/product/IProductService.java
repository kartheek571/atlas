package com.mycode.atlas.service.product;

import java.util.List;

import com.mycode.atlas.model.Product;
import com.mycode.atlas.request.AddProductRequest;
import com.mycode.atlas.request.ProductUpdateRequest;

public interface IProductService {
	
	Product addProduct(AddProductRequest request);
	Product getProductById(long id);
	void deleteProductById(long id);
	Product updateProduct(ProductUpdateRequest request, long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	long countProductsByBrandAndName(String brand, String name);
	
	

}
