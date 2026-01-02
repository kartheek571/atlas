package com.mycode.atlas.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycode.atlas.exception.AlreadyExists;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Category;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

	
	private final ICategoryService categoryService;
	
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories()
	{
		try {
			List<Category> categories= categoryService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("found!", categories));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
		}
		
		
	}
	
	
	@PostMapping("/addcategory")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category)
	{
		try {
			Category addedcategory= categoryService.addCategory(category);
			return ResponseEntity.ok(new ApiResponse("success!", addedcategory));
		} catch (AlreadyExists e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	
	@GetMapping("/category/{id}/getcategoryById")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable long id)
	{
		

		try {
			Category addedcategory= categoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("found!", addedcategory));
		} catch (ResourceNotFound e) {
			// TODO Auto-generated catch block
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
		
	}
	
	
	
	@GetMapping("/category/{name}/getcategoryByName")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name)
	{
		

		try {
			Category addedcategory= categoryService.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("found!", addedcategory));
		} catch (ResourceNotFound e) {
			// TODO Auto-generated catch block
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
		
	}
	
	
	
	@DeleteMapping("/category/{id}/deletecategoryById")
	public ResponseEntity<ApiResponse> deleteCategoryByName(@PathVariable long id)
	{
		

		try {
			categoryService.deleteCategory(id);
			return ResponseEntity.ok(new ApiResponse("found!", null));
		} catch (ResourceNotFound e) {
			// TODO Auto-generated catch block
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
		
	}
	
	@PutMapping("/category/{id}/update")
	public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category Category, @PathVariable long id)
	{
		try {
			Category addedcategory= categoryService.updateCategory( Category, id );
			return ResponseEntity.ok(new ApiResponse("found!", addedcategory));
		} catch (ResourceNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
		
				
	}
	
	
}
