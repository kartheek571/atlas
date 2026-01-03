package com.mycode.atlas.security.jwt;

import java.io.IOException;

import javax.print.attribute.standard.RequestingUserName;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mycode.atlas.security.user.ShopUserDetails;
import com.mycode.atlas.security.user.ShopUserDetailsService;

import ch.qos.logback.core.util.StringUtil;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.NonFinal;

public class AuthTokenFilter extends OncePerRequestFilter {

	
	
			private JwtUtil jwtUtil;
			private ShopUserDetailsService shopUserDetailsService;
			@Override
			protected void doFilterInternal(@NonNull  HttpServletRequest request, @NonNull HttpServletResponse response,
					@NonNull FilterChain filterChain) throws ServletException, IOException {
				
				
				
				try {
					String jwt=parseJwt(request);
					
					if(StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt))
					{
						String username= jwtUtil.getUserNameFromToken(jwt);
						UserDetails userDetails =shopUserDetailsService.loadUserByUsername(username);
						Authentication auth=
								new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(auth);
						
						
						}
				} catch (JwtException e) {
					
					
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().write(e.getMessage()+" Invalid or expired token , please login and try again ");
					return ;
					
				} catch(Exception e)
				{
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write(e.getMessage());
					return ;
				}
				
				filterChain.doFilter(request, response);
			}
			
			private String parseJwt(HttpServletRequest request)
			{
				String headerAuth= request.getHeader("Authorization");
				if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
				{
					return headerAuth.substring(7);
					
				}
				
				return null;
			}
			
}
