package com.mycode.atlas.service.images;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mycode.atlas.dto.ImageDto;
import com.mycode.atlas.model.Image;

public interface IImageService {
	
	Image getImageById(long id);
	void deleteImageById(long id);
	List<ImageDto> saveImage(List<MultipartFile> files , long productid);
	void updateImage(MultipartFile file , long imageId);
	
	

}
