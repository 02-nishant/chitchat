package com.chat.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chat.entity.User;
import com.chat.entity.updateUser;
import com.chat.exception.ResourceNotFound;
import com.chat.repository.userRepo;
import com.chat.security.tokenGenerate;
import com.chat.service.FileStorage;
import com.chat.service.userService;

@Service
public class userServiceImpl implements userService{
	@Autowired
	private userRepo userRepo;
	
	@Autowired
	private tokenGenerate tokenGenerate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @Autowired
	 private FileStorage fileStorageService;

	
	public userServiceImpl(userRepo userRepo, tokenGenerate tokenGenertate) {
		super();
		this.userRepo = userRepo;
		this.tokenGenerate = tokenGenerate;
	}
	
	

	@Override
	public User findUserById(Integer id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}else {
			throw new ResourceNotFound("User","Id",id);
		}
	}

	@Override
	public User findUserProfile(String jwt) {
		String email = tokenGenerate.getEmailFromToken(jwt);
		
		if(email == null) {
			throw new BadCredentialsException("Recieved invalid token");
		}
		
		User user = userRepo.findByEmail(email);
		return user;
	}

	@Override
	public User updateUser(Integer userId, updateUser req){
		
		User user  =  findUserById(userId);
		
		if(req.getName() !=null) {
			user.setName(req.getName());
		}
		
		if(req.getPicture() != null) {
			user.setPicture(req.getPicture());
		}	
		
		return userRepo.save(user);
	}
	
	 @Override
	    public String updateProfileImage(String path, MultipartFile file) {
	      
	        String originalFileName = file.getOriginalFilename();

	        String randomId = UUID.randomUUID().toString();

	        String newFileName = randomId + originalFileName.substring(originalFileName.lastIndexOf("."));

	        String filePath = path + File.separator + newFileName;

	        File directory = new File(path);
	        if (!directory.exists()) {
	            directory.mkdir();
	        }

	        try {
	            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to store file", e);
	        }

	        return newFileName; 
	    }


	@Override
	public List<User> search(String query) {
		
		List<User> users = userRepo.searchUser(query);

		return users;
	}



	@Override
	public void deleteUser(Integer userId) {
		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
		userRepo.deleteById(userId);
		
	}



	@Override
	public User registerNewUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		return userRepo.save(user);
	}
	
}
