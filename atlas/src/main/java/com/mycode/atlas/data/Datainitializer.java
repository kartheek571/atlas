package com.mycode.atlas.data;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mycode.atlas.model.Role;
import com.mycode.atlas.model.User;
import com.mycode.atlas.repository.UserRepository;
import com.mycode.atlas.request.createUserRequest;

import lombok.RequiredArgsConstructor;


@Transactional
@Component
@RequiredArgsConstructor
public class Datainitializer  implements ApplicationListener<ApplicationEvent>{

	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		Set<String> defaultRoles=Set.of("ROLE_ADMIN", "ROLE_CUSTOMER");
		createDefaultUserIfNotExists();
		createDefaultRolesIfNotExists(defaultRoles);
	       createDefaultAdminIfNotExits();
		
	
		
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
			user.setPassword(passwordEncoder.encode("123456"));
			userRepository.save(user);
			System.out.println("Default user "+i+"created successfully");
			
		}
	}
	

    private void createDefaultAdminIfNotExits(){
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i<=2; i++){
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstname("Admin");
            user.setLastname("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully.");
        }
    }
	
	private void createDefaultRolesIfNotExists(Set<String> roles)
	{
		roles.stream()
		.filter(role->roleRepository.findByName(role).isEmpty())
		.map(Role::new ).forEach(roleRepository::save);
		
	}

}
