package com.mycode.atlas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycode.atlas.request.LoginRequest;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.response.JwtResponse;
import com.mycode.atlas.security.jwt.JwtUtil;
import com.mycode.atlas.security.user.ShopUserDetails;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
@Log4j2
public class AuthController {
	
	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;
	
   
	


		
	
	
    @PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid  @RequestBody  LoginRequest loginRequest)
	{
        log.warn("Login failed for " );
		
		try {
			
			System.out.println("Hello from auth login");
			Authentication authentication =authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt=jwtUtil.generateTokenForUser(authentication);
			ShopUserDetails userDetails = (ShopUserDetails)authentication.getPrincipal();
			JwtResponse jwtResponse=new JwtResponse(userDetails.getId(),  jwt);
			return ResponseEntity.ok(new ApiResponse("Login Successfull", jwtResponse));
		} catch (AuthenticationException e) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse( e.getMessage()+"error exists here", null));
		}
	}
	
	
	
	

}
