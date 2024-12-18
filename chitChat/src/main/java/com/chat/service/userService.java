package com.chat.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chat.entity.User;
import com.chat.entity.updateUser;

public interface userService {
	
	User registerNewUser(User user);
	
	public User findUserById(Integer id);
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, updateUser req);
	public String updateProfileImage(String path, MultipartFile file);
	
	public List<User> search(String query);
	
	void deleteUser(Integer userId);

}
