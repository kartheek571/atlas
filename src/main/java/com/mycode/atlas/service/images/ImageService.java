package com.mycode.atlas.service.images;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycode.atlas.dto.ImageDto;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Image;
import com.mycode.atlas.model.Product;
import com.mycode.atlas.repository.ImageRepository;
import com.mycode.atlas.service.product.IProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

@Service
public class ImageService implements IImageService {
	
	private final ImageRepository imageRepo;
	private final IProductService iProductService;
	
	
	
	@Override
	public Image getImageById(long id) {
		// TODO Auto-generated method stub
		return imageRepo.findById(id).orElseThrow(
				
				()-> new ResourceNotFound(String.format("no image found with given id %d", id))
				
				);
	}

	@Override
	public void deleteImageById(long id) {
		
		imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,       
				()->{
					throw new ResourceNotFound(String.format("no image found with given id %d", id));
				}	);
		
		
		
		
		
	}

	@Override
	public List<ImageDto> saveImage(List<MultipartFile> files, long productid) {

		Product product = iProductService.getProductById(productid);
		List<ImageDto> imagedtos= new ArrayList<>();
		for(MultipartFile file:files)
		{
			try {
				Image image= new Image();
				image.setFilename(file.getOriginalFilename());
				image.setFiletype(file.getContentType());
				image.setImages(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				
				String buildDownloadUrl="/api/v1/images/image/download";
				String downloadUrl=buildDownloadUrl+image.getId();
				image.setDownloadUrl(downloadUrl);
				
				Image savedImage=    imageRepo.save(image);
				
				savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());///why am i again saving here  for downloadurl
				
				
				ImageDto imagedto = new ImageDto();
				imagedto.setId(savedImage.getId());
				imagedto.setFilename(savedImage.getFilename());
				imagedto.setDownloadUrl(savedImage.getDownloadUrl());
				imagedtos.add(imagedto);
				
				
			} catch (IOException | SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
			
			return imagedtos;
		}
		
		
		
		
		
		
		
		
		return null;
	}

	@Override
	public void updateImage(MultipartFile file, long imageId) {
		
		Image image = getImageById(imageId);
		try {
			image.setFilename(file.getOriginalFilename());
			image.setFilename(file.getOriginalFilename());
			image.setImages(new SerialBlob(file.getBytes()));
			imageRepo.save(image);
		} catch (IOException | SQLException e ) {
			throw new RuntimeException(e.getMessage());
		
		}
		
		
	}

	
	
	
}
