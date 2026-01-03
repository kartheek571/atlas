package com.mycode.atlas.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mycode.atlas.security.user.ShopUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;



@Component
public class JwtUtil  {
		
	   @Value("${auth.token.jwtSecret}")
		private String jwtSecret;
	    
	    @Value("${auth.token.expirationInMils}")
		private int expirationInMils;
	
	

	
 
	
	public String generateTokenForUser(Authentication authentication )
	{
		ShopUserDetails userPrinciple=(ShopUserDetails)authentication.getPrincipal();
		List<String> roles= userPrinciple.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority).toList();
		return Jwts.builder()
				.setSubject(userPrinciple.getEmail())
				.claim("id", userPrinciple.getId())
				.claim("role", roles)
				
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expirationInMils))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
			
	}
	
	public String getUserNameFromToken(String token)
	{
		  return  Jwts.parserBuilder()
		.setSigningKey(getSigningKey())
		.build()
		.parseClaimsJws(token)
		.getBody().getSubject();
	}
	
	
	public boolean validateToken(String token)
	{
		try {
			Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody().getSubject();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException   e) {
			throw new JwtException(e.getMessage());
		}
		
		return true;
	}
	
	private Key  getSigningKey()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	
}
