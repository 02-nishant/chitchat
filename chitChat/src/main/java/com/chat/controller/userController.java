package com.chat.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chat.entity.FileResponse;
import com.chat.entity.User;
import com.chat.entity.updateUser;
import com.chat.repository.userRepo;
import com.chat.service.userService;

@RestController
@RequestMapping("/api/user")
public class userController {
	
	 @Value("${project.image}")
	    private String path;
	
	@Autowired
	private userService userService;
	
	
	@Autowired
	private userRepo userRepo;
	
	
	public userController(userService userService) {
		super();
		this.userService = userService;
	}
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token){
		
		return new ResponseEntity<User>(userService.findUserProfile(token), HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id, Authentication authentication) {

		String currentUserEmail = authentication.getName();
	    
	    Integer currentUserId = userRepo.findByEmail(currentUserEmail).getId(); 
	    
	    if (!currentUserId.equals(id)) {
	        return new ResponseEntity<>("You can only delete your own account", HttpStatus.FORBIDDEN);
	    }

	    userService.deleteUser(id);
	    return new ResponseEntity<>("User Record Is Deleted", HttpStatus.OK);
	}
	
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {
	    List<User> users = userService.search(query);
	    
	    if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	    }
	    
	    return new ResponseEntity<>(users, HttpStatus.OK);
	}


	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody updateUser req, @RequestHeader("Authorization") String token) {
		User user = userService.findUserProfile(token);
	  
	    
	  
	    User updatedUser = userService.updateUser(user.getId(), req);
	    
	   
	    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	
	 @PostMapping("/updateImage")
	    public ResponseEntity<FileResponse> updateProfileImage(@RequestParam("image") MultipartFile image) {
	        // Call the service method to update the image
	        String fileName = this.userService.updateProfileImage(path, image);

	        // Create and return a response entity with the filename and success message
	        return new ResponseEntity<>(new FileResponse(fileName, "Image successfully updated"), HttpStatus.OK);
	    }


	

}
