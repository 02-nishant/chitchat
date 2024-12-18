package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.exception.ChatException;
import com.chat.repository.messageRepo;
import com.chat.service.messageService;
import com.chat.service.sendMessageRequest;
import com.chat.service.userService;

@RestController
@RequestMapping("/api/message")
public class messageController {
	
	@Autowired
	private messageService messageService;
	
	@Autowired
	private userService userService;
	
	@Autowired
	private messageRepo messageRepo;

	public messageController(messageService messageService, userService userService) {
		super();
		this.messageService = messageService;
		this.userService = userService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessage(@RequestBody sendMessageRequest req,  @RequestHeader("Authorization")String token) throws ChatException{
		
		User user = userService.findUserProfile(token);
		req.setUserId(user.getId());
		
		Message message = messageService.sendMessage(req);
		
		return new ResponseEntity<Message>(messageRepo.save(message), HttpStatus.OK);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getMessage(@PathVariable Integer chatId,@RequestHeader("Authorization")String token) throws ChatException {
		
		User user = userService.findUserProfile(token);
		
		List<Message> message = messageService.getChatMessage(chatId, user);
		
		return new ResponseEntity<>(message, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<String> deleteMessage(@PathVariable Integer messageId,@RequestHeader("Authorization")String token) throws ChatException {
		
		User user = userService.findUserProfile(token);
		
		messageService.deleteMessage(messageId, user);
		
	    return new ResponseEntity<>("User Record Is Deleted", HttpStatus.OK);
			
	}
	
	@GetMapping("/search/{query}")
	public ResponseEntity<List<Message>> searchMessages(@PathVariable("query") String query, @RequestHeader("Authorization") String token) {
	    User user = userService.findUserProfile(token);
	    
	    List<Message> messages = messageService.searchMessages(query);
	    
	    if (messages.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(messages, HttpStatus.OK);
	}

}
