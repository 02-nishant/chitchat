package com.chat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.entity.Chat;
import com.chat.entity.User;
import com.chat.exception.ChatException;
import com.chat.service.GroupChatRequest;
import com.chat.service.chatService;
import com.chat.repository.chatRepo;
import com.chat.service.userService;


@Service
public class chatServiceImpl implements chatService {
	
	@Autowired
	private chatRepo chatRepo;
	
	@Autowired
	private userService userService;
	
	public chatServiceImpl(chatRepo chatRepo, userService userService) {
		super();
		this.chatRepo = chatRepo;
		this.userService = userService;
	}

	@Override
	public Chat creatChat(User user1, Integer userId2) {
		
		User user2 = userService.findUserById(userId2);
		
		Chat isChatExist = chatRepo.findSingleChat(user2, user1);
		
		if(isChatExist != null) {
			return isChatExist;
		}
		
		Chat chat = new Chat();		
		chat.setCreatedBy(user1);
		chat.getUsers().add(user2);
		chat.getUsers().add(user1);
		chat.setGroup(false);
		
		 return chatRepo.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		
		Optional<Chat> chat = chatRepo.findById(chatId);
		
		if(chat.isPresent()) {
			return chat.get();
		}
		
		throw new ChatException("chat not found");
	}

	@Override
	public List<Chat> findAllChat(Integer userId) {
		
		List<Chat> chat = chatRepo.findChatByUserId(userId);
		
		return chat;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User user) {
		
		Chat group = new Chat();
		group.setGroup(true);
		group.setChat_name(req.getChatName());
		group.setChat_image(req.getChatImage());
		group.setCreatedBy(user);
		group.getAdmins().add(user);
		 
		
		for(Integer userId: req.getUserIds()) {
			User users = userService.findUserById(userId);
			group.getUsers().add(users);
		}
		
		return chatRepo.save(group);
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId, User admin) {
		
		Optional<Chat> optionalChat = chatRepo.findById(chatId); 
	    if (!optionalChat.isPresent()) {
	        throw new RuntimeException("Chat not found");
	    }

	    Chat chat = optionalChat.get();

	    User user = userService.findUserById(userId);
	    
	    if(chat.getAdmins().contains(admin)) {
	    	chat.getUsers().add(user);
	    }

	    

	    return chat;
	}

	@Override
	public Chat renameGroup(Integer chatId, String name, User user) throws ChatException {
		
		Optional<Chat> optionalChat = chatRepo.findById(chatId); 
	    if (!optionalChat.isPresent()) {
	        throw new RuntimeException("Chat not found");
	    }

	    Chat chat = optionalChat.get();
	    
	    if(chat.getUsers().contains(user)) {
	    	chat.setChat_name(name);
	    }
		
		
		
		return chatRepo.save(chat);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User admin) {
		
		Optional<Chat> optionalChat = chatRepo.findById(chatId); 
	    if (!optionalChat.isPresent()) {
	        throw new RuntimeException("Chat not found");
	    }

	    Chat chat = optionalChat.get();
	    
	    if(chat.getAdmins().contains(admin) || userId == admin.getId()) {
	    	User user = userService.findUserById(userId);
	    	chat.getUsers().remove(user);
	    }
	    
		return chatRepo.save(chat);
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException {
		
		Optional<Chat> optionalChat = chatRepo.findById(chatId); 
	    if (!optionalChat.isPresent()) {
	        throw new RuntimeException("Chat not found");
	    }

	    Chat chat = optionalChat.get();
	    
	    chatRepo.deleteById(chat.getId());
		
	
	}

}
