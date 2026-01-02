package com.mycode.atlas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.mycode.atlas.dto.UserDto;
import com.mycode.atlas.exception.AlreadyExists;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.User;
import com.mycode.atlas.request.UserUpdateRequest;
import com.mycode.atlas.request.createUserRequest;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.service.order.IOrderService;
import com.mycode.atlas.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	
	
	private final IUserService userService;
	
	@GetMapping("/{userId}/user")
	public ResponseEntity<ApiResponse> getUserById( @PathVariable  Long userId)
	{
		try {
			User user= userService.getUserById(userId);
			
			UserDto userDto = userService.convertUsertoDto(user);
			return ResponseEntity.ok(new ApiResponse("Success", userDto));
		} catch (ResourceNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Opps!",e.getMessage()));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createUser(@RequestBody   createUserRequest request)
	{
		try {
			User user =userService.createUser(request);
			UserDto userDto = userService.convertUsertoDto(user);
			return ResponseEntity.ok(new ApiResponse("User created successfully",userDto ));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ApiResponse("error occured while creating user ", e.getMessage()));
		}
		
	}
	
	
	
	@PutMapping("/{userId}/update")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,  @PathVariable Long userId)
	{
		try {
			User user =userService.updateUser(request, userId);
			UserDto userDto = userService.convertUsertoDto(user);
			return ResponseEntity.ok(new ApiResponse("updated user Sucessfully", userDto));
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("error occured while updating user ", e.getMessage()));
			
			
		}
		
	}
	
	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable  Long userId)
	{
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("delete user sucessfull", null));
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("error occured while deleting user ", e.getMessage()));
			
			
			
		}
	}
	
	

}
