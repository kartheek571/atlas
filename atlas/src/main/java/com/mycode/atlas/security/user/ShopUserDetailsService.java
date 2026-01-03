package com.mycode.atlas.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycode.atlas.model.User;
import com.mycode.atlas.repository.UserRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class ShopUserDetailsService    implements UserDetailsService{

	
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		User user =Optional.ofNullable(userRepository.findByEmail(username)).orElseThrow(
				()->  new UsernameNotFoundException("user  does not exists")
				);
		
		
		return ShopUserDetails.buildUserDetails(user);
	}

}
