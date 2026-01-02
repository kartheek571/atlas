package com.mycode.atlas.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mycode.atlas.dto.UserDto;
import com.mycode.atlas.exception.AlreadyExists;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.User;
import com.mycode.atlas.repository.UserRepository;
import com.mycode.atlas.request.UserUpdateRequest;
import com.mycode.atlas.request.createUserRequest;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService  implements IUserService {

	
	private final UserRepository userRepo;
	
	private final ModelMapper modelMapper;
	
	
	@Override
	public User getUserById(Long userId) {
		
		
		
		return userRepo.findById(userId).orElseThrow(()->new  ResourceNotFound("User not found"));
	}

	@Override
	public User createUser(createUserRequest request) {
	
		return Optional.of(request).filter(user-> !userRepo.existsByEmail(request.getEmail()))
				.map(req->{
					User user = new User();
					user.setEmail(request.getEmail());
					user.setPassword(req.getPassword());
					user.setFirstname(req.getFirstname());
					user.setLastname(req.getLastname());
					return userRepo.save(user);
				}).orElseThrow(()-> new AlreadyExists(request.getEmail()+"User Already exists"));
		
		
	
	}

	@Override
	public User updateUser(UserUpdateRequest request, Long userId) {
		
		return 	userRepo.findById(userId).map(existingUser->{
			existingUser.setFirstname(request.getFirstname());
			existingUser.setLastname(request.getLastname());
			return userRepo.save(existingUser);
		}).orElseThrow(()->new  ResourceNotFound("User not found"));
		
		
	}

	@Override
	public void deleteUser(Long userId) {
		userRepo.findById(userId)
		.ifPresentOrElse(userRepo::delete, ()->{  throw new  ResourceNotFound("User not found") ;});
	}
	
	@Override
	public  UserDto convertUsertoDto(User user)
	{
		
		
		return modelMapper.map(user, UserDto.class);
	}
	
	
	

}
