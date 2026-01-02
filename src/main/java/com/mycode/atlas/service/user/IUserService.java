package com.mycode.atlas.service.user;

import com.mycode.atlas.dto.UserDto;
import com.mycode.atlas.model.User;
import com.mycode.atlas.request.UserUpdateRequest;
import com.mycode.atlas.request.createUserRequest;

public interface IUserService {
	
	User getUserById(Long userId);
	
	User createUser(createUserRequest  request );
	
	User updateUser(UserUpdateRequest request, Long userId);
	void deleteUser(Long userId);

	UserDto convertUsertoDto(User user);

}
