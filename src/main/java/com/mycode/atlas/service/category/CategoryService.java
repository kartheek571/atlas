package com.mycode.atlas.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycode.atlas.exception.AlreadyExists;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Category;
import com.mycode.atlas.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service


public class CategoryService  implements ICategoryService  {

	
	private final  CategoryRepository categoryRepository;
	@Override
	public Category getCategoryById(long id) {
	
		return categoryRepository.findById(id).orElseThrow(
				
				()-> new ResourceNotFound(String.format("No Category found with the given id %d", id))
				
				);
	}

	@Override
	public Category getCategoryByName(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByName(name) ;
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		
		return Optional.ofNullable(category).filter(c-> !categoryRepository.existsByName(category.getName())).map(categoryRepository::save).orElseThrow(
				()->new AlreadyExists(String.format(" Category found with the given name"))
				
				);
	}

	@Override
	public Category updateCategory(Category category , long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(getCategoryById(id)).map(oldcategory->{
			oldcategory.setName(category.getName());
		return 	categoryRepository.save(oldcategory);
		}
		).orElseThrow(
				
				()-> new ResourceNotFound(String.format("No Category found with the given id %d", id))
				);
	}

	@Override
	public void deleteCategory(long id) {
		categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, 
()-> new ResourceNotFound(String.format("No Category found with the given id %d", id))
				);
	
	}

}
