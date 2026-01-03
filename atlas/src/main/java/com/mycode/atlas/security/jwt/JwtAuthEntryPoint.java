package com.mycode.atlas.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class JwtAuthEntryPoint  implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    

        final Map<String, Object> body = new HashMap<>();
 
        body.put("error", "Unauthorized");
        body.put("message", "You may login and try again!");
        final ObjectMapper mapper = new ObjectMapper();

       
        mapper.writeValue(response.getOutputStream(), body);

		
	}

}
