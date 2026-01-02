package com.mycode.atlas.controller;

import java.sql.SQLException;
import java.util.List;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycode.atlas.dto.ImageDto;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Image;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.service.images.IImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageContoller {
	
	private final IImageService imageService;
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam  List<MultipartFile> files,  @RequestParam  long  productid)
	{
		try {
			List<ImageDto>  imageDto= imageService.saveImage(files, productid) ;
			
			return ResponseEntity.ok(new ApiResponse("upload success!", imageDto));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", e.getMessage()));
		}
	
	}
	
	@GetMapping("/image/download/{imageid}")
	public ResponseEntity<Resource> downloadImage(@PathVariable long imageid ) throws SQLException
	{
		Image image = imageService.getImageById(imageid);
		ByteArrayResource resource= new ByteArrayResource(image.getImages().getBytes(1, (int)image.getImages().length()));
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFiletype()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "Attachment: filename=\""+image.getFilename()+"\"").body(resource);
		
		
	}
	
	
	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageid,  @RequestBody MultipartFile file )
	{
		Image image = imageService.getImageById(imageid);
		
	try {
		if(image!=null)
		{
			imageService.updateImage(file, imageid);
			return ResponseEntity.ok(new ApiResponse("update success!", image));
		}
	} catch (ResourceNotFound e) {

	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
	
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(" update failed", HttpStatus.INTERNAL_SERVER_ERROR));

	
	
	}
	
	
	
	@DeleteMapping("/image/{imageId}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageid   )
	{
		Image image = imageService.getImageById(imageid);
		
	try {
		if(image!=null)
		{
			imageService.deleteImageById( imageid);
			return ResponseEntity.ok(new ApiResponse("delete success!", image));
		}
	} catch (ResourceNotFound e) {

	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
	
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(" delete failed", HttpStatus.INTERNAL_SERVER_ERROR));

	
	
	}
	
	
	
	

}
