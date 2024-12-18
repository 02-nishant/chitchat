package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.Chat;
import com.chat.entity.User;
import com.chat.entity.singleChat;
import com.chat.exception.ChatException;
import com.chat.service.GroupChatRequest;
import com.chat.service.chatService;
import com.chat.service.userService;

@RestController
@RequestMapping("/api/chat")
public class chatController {
	
	@Autowired
	private chatService chatService;
	
	@Autowired
	private userService userService;

	public chatController(com.chat.service.chatService chatService, com.chat.service.userService userService) {
		super();
		this.chatService = chatService;
		this.userService = userService;
	}
	
	@PostMapping("/single")
	public ResponseEntity<Chat> createChat(@RequestBody singleChat singleChat, @RequestHeader("Authorization")String token){
		
		User user = userService.findUserProfile(token);
		
		Chat chat = chatService.creatChat(user, singleChat.getUserId());
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroup(@RequestBody GroupChatRequest groupChat, @RequestHeader("Authorization")String token){
		
		User user = userService.findUserProfile(token);
		
		Chat chat = chatService.createGroup(groupChat, user);
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@GetMapping("/search/{chatId}")
	public ResponseEntity<Chat> findChatById(@PathVariable Integer chatId, @RequestHeader("Authorization")String token) throws ChatException{
		
		Chat chat = chatService.findChatById(chatId);
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findChatByUser( @RequestHeader("Authorization")String token) throws ChatException{
		
		User user = userService.findUserProfile(token);
		
		List<Chat> chat = chatService.findAllChat(user.getId());
		
		
		return new ResponseEntity<List<Chat>>(chat, HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroup(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization")String token) throws ChatException{
		
		User user = userService.findUserProfile(token);
		
		Chat chat = chatService.addUserToGroup(userId, chatId, user);
		
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGroup(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization")String token) throws ChatException{
		
		User user = userService.findUserProfile(token);
		
		Chat chat = chatService.removeFromGroup(chatId, userId, user);
		
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@DeleteMapping("/{chatId}")
	public ResponseEntity<String> deleteChat(@PathVariable Integer chatId, @RequestHeader("Authorization")String token) throws ChatException{
		
		User user = userService.findUserProfile(token);
		
		chatService.deleteChat(chatId, user.getId());
		
		return new ResponseEntity<String>("Chat Record Is Deleted", HttpStatus.OK);
	}

}
