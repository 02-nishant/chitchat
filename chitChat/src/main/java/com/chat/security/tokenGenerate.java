package com.chat.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.chat.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class tokenGenerate {
	
	SecretKey key = Keys.hmacShaKeyFor(Constant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication authentication) {
		
		String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis())).
				setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.claim("email", authentication.getName())
				.signWith(key).compact();
			
		return token;
	}
	
	
	public String generateToken(User user) {
	    // Assuming the User class has a method getEmail() and getAuthorities()
	    String email = user.getEmail(); 
	    // You can also extract additional information like roles from the user if needed

	    // Create a JWT token
	    String token = Jwts.builder()
	            .setSubject(email) // Set the subject to the user's email or ID
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 1 hour
	            .claim("email", email) // Optional: add claims
	            // Add roles/authorities if you want to include them in the claims
	            .signWith(key) // Use your secret key here
	            .compact();

	    return token;
	}

	
	public String getEmailFromToken(String token) {
		token = token.substring(7);
		Claims claim = Jwts 
                .parserBuilder() 
                .setSigningKey(key) 
                .build() 
                .parseClaimsJws(token) 
                .getBody();
		
		String email = String.valueOf(claim.get("email"));
		
		return email;
	}
	
	

}
