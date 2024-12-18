package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.User;
import com.chat.security.AuthRequest;
import com.chat.security.AuthResponse;
import com.chat.security.customUserService;
import com.chat.security.tokenGenerate;
import com.chat.service.userService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private userService userService;
	
	@Autowired
	private customUserService customUserService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private tokenGenerate tokenGenerate;
	
	public AuthController(userService userService,customUserService customUserService, PasswordEncoder passwordEncoder,tokenGenerate tokenGenerate) {
		super();
		this.userService = userService;
		this.customUserService = customUserService;
		this.passwordEncoder = passwordEncoder;
		this.tokenGenerate = tokenGenerate;
	}


	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) {
	    User newUser = this.userService.registerNewUser(user);

	  
	    String jwt = tokenGenerate.generateToken(newUser); 

	    AuthResponse authResponse = new AuthResponse(jwt, true);
	    
	    return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest req){
		
		String email = req.getEmail();
		String password = req.getPassword();
		
		Authentication authentication = authenticate(email,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenGenerate.generateToken(authentication);
		
		AuthResponse res = new AuthResponse(jwt,true);
		
		return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
	}
	
	private Authentication authenticate(String email, String password) {

		UserDetails userDetails = customUserService.loadUserByUsername(email);
		
		if(userDetails == null) {
			throw new BadCredentialsException("invalid user");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
			
		return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	}

}
