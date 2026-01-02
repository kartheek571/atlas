package com.mycode.atlas.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycode.atlas.exception.ProductNotFound;
import com.mycode.atlas.model.Category;
import com.mycode.atlas.model.Product;
import com.mycode.atlas.repository.CategoryRepository;
import com.mycode.atlas.repository.ProductRepository;
import com.mycode.atlas.request.AddProductRequest;
import com.mycode.atlas.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductService  implements IProductService {
	
//	private final ModelMapper modelMapper;
	
	private final ProductRepository productRepo;
	private final CategoryRepository categoryRepo;

	@Override
	public Product addProduct(AddProductRequest request) {
		Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
				.orElseGet(
	()->{Category  newCategory= new Category(request.getCategory().getName());
	return categoryRepo.save(newCategory);
	});
		
	request.setCategory(category);
	
	return productRepo.save(createProduct(request,category));
			
		
		
		
	}
	
	
	
	private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

	@Override
	public Product getProductById(long id) {
		// TODO Auto-generated method stub
		return productRepo.findById(id).orElseThrow(
				()-> new ProductNotFound(String.format(" the product with given id %d is not found", id))
				
				);
	}

	@Override
	public void deleteProductById(long id) {
		// TODO Auto-generated method stub
		
		productRepo.findById(id).ifPresentOrElse(productRepo::delete,
				()->{throw new ProductNotFound(String.format(" the product with given id %d is not found", id)) ;}
				
				
				);
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, long productId) {
	return productRepo.findById(productId).map(existingproduct->updateExistingProduct(existingproduct,request) )
			.map(productRepo::save)
			.orElseThrow(()-> new ProductNotFound(String.format(" the product with given id %d is not found", productId)));
		
	}
	
	
	private Product updateExistingProduct(Product existingproduct,ProductUpdateRequest request)
	{
		existingproduct.setName(request.getName());
		existingproduct.setBrand(request.getBrand());
		existingproduct.setPrice(request.getPrice());
		existingproduct.setInventory(request.getInventory());
		existingproduct.setDescription(request.getDescription());
		Category category = categoryRepo.findByName(request.getCategory().getName());
		existingproduct.setCategory(request.getCategory());
		
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return productRepo.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return productRepo.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		// TODO Auto-generated method stub
		return productRepo.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		return productRepo.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return productRepo.findByBrandAndName(brand , name);
	}

	@Override
	public long countProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return  productRepo.countByBrandAndName(brand , name) ;
	}

}
