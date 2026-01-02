package com.mycode.atlas.data;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.mycode.atlas.model.User;
import com.mycode.atlas.repository.UserRepository;
import com.mycode.atlas.request.createUserRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Datainitializer  implements ApplicationListener<ApplicationEvent>{

	
	private final UserRepository userRepository;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		createDefaultUserIfNotExists();
		
	
		
	}
	
	private void createDefaultUserIfNotExists()
	{
		for(int i=1;i<=5;i++)
		{
			String defaultEmail="user"+i+"@gmail.com";
			if(userRepository.existsByEmail(defaultEmail))
			{
				continue;
			}
			
			User user =new User();
			user.setFirstname("The User");
			user.setLastname("User"+i);
			user.setEmail(defaultEmail);
			user.setPassword("123456");
			userRepository.save(user);
			System.out.println("Default user "+i+"created successfully");
			
		}
	}
	
	

}
