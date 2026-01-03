package com.mycode.atlas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycode.atlas.exception.AlreadyExists;
import com.mycode.atlas.exception.ProductNotFound;
import com.mycode.atlas.model.Product;
import com.mycode.atlas.request.AddProductRequest;
import com.mycode.atlas.request.ProductUpdateRequest;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	
	private final IProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts()
	{
		List<Product> products= productService.getAllProducts();
		
		return ResponseEntity.ok(new ApiResponse("Success", products));
		
	}
	
	
	@GetMapping("/product/{productid}/product")
	public ResponseEntity<ApiResponse>  getProductById(@PathVariable long productid)
	{
		try {
			Product product= productService.getProductById(productid);
			return ResponseEntity.ok(new ApiResponse("success", product));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed"+e.getMessage(),null));
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/product/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest  product)
	{
		try {
			Product  sproduct=productService.addProduct(product);
			return  ResponseEntity.ok(new ApiResponse("product added successfully", sproduct));
		} catch (AlreadyExists e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("failed to add  product ", null));
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")

	@PutMapping("/product/{productid}/update")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable long  productid)
	{
		try {
			Product theproduct =productService.updateProduct(product, productid);
			
			
			return ResponseEntity.ok( new ApiResponse("Successfully updated the product", theproduct));
		} catch (ProductNotFound e) {
		
			
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
			
		}
		
		
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")

	
	@DeleteMapping("/product/{id}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable long productid)
	{
		
		try {
			productService.deleteProductById( productid);
			return ResponseEntity.ok(new ApiResponse("deleted the product successfully", null));
		} catch (ProductNotFound e) {
			// TODO Auto-generated catch block
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	
	@GetMapping("/product/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName)
	{
		
		
		
		try {
			List<Product>  products=productService.getProductsByBrandAndName(brandName, productName);
			
			if(products.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed to fetch the products", null));

			}
			return ResponseEntity.ok(new ApiResponse("fetched producteds by Brand and  Name", products));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

		}
	}
	
	
	
	@GetMapping("/product/by/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName)
	{
		
		
		
		try {
			List<Product>  products=productService.getProductsByCategoryAndBrand(categoryName, brandName);
			
			if(products.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed to fetch the products", null));

			}
			return ResponseEntity.ok(new ApiResponse("fetched producteds by category  and  Brand", products));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(  "Error" ,e.getMessage()));

		}
	}
	
	
	

	@GetMapping("/product/{categoryName}/by-name")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String categoryName)
	{
		
		
		
		try {
			List<Product>  products=productService.getProductsByName(categoryName);
			
			if(products.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed to fetch the products", null));

			}
			return ResponseEntity.ok(new ApiResponse("fetched producteds by Name", products));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(  "Error" ,e.getMessage()));

		}
	}
	
	
	


	@GetMapping("/product/{brand}/by-brand")
	public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand)
	{
		
		try {
			List<Product>  products=productService.getProductsByBrand(brand);
			
			if(products.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed to fetch the products", null));

			}
			return ResponseEntity.ok(new ApiResponse("fetched producteds by  Brand", products));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(  "Error" ,e.getMessage()));

		}
	}
	
	
	
	
	
	
	

	@GetMapping("/product/{category}/all/by-category")
	public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category)
	{
		
		try {
			List<Product>  products=productService.getProductsByBrand(category);
			
			if(products.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("failed to fetch the products", null));

			}
			return ResponseEntity.ok(new ApiResponse("fetched producteds by  Brand", products));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(  "Error" ,e.getMessage()));

		}
	}
	
	
	
	
	
	

	
	@GetMapping("/product/count/by-brand/by-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String barndName, @RequestParam String Name)
	{
		
		
		
		try {
			var  productcount=productService.countProductsByBrandAndName(barndName, Name);
			
			
			
			return ResponseEntity.ok(new ApiResponse("fetched producteds by Brand and  Name", productcount));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
