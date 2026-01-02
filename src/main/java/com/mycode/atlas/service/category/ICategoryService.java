package com.mycode.atlas.service.category;

import java.util.List;

import com.mycode.atlas.model.Category;

public interface ICategoryService {
	
	Category getCategoryById(long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();
	Category addCategory(Category category);
	Category updateCategory(Category category, long id);
	void deleteCategory(long id);
	

}
