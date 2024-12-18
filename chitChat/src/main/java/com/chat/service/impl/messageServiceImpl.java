package com.chat.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.entity.Chat;
import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.exception.ChatException;
import com.chat.repository.messageRepo;
import com.chat.service.chatService;
import com.chat.service.messageService;
import com.chat.service.sendMessageRequest;
import com.chat.service.userService;

@Service
public class messageServiceImpl implements messageService {
	
	@Autowired
	private  messageRepo messageRepo;
	
	@Autowired
	private userService userService;
	
	@Autowired
	private chatService chatService;
	
	
	
	public messageServiceImpl(messageRepo messageRepo, userService userService,chatService chatService) {
		super();
		this.messageRepo = messageRepo;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(sendMessageRequest req) throws ChatException {
		
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		
		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTime(LocalDateTime.now());
		
		return message;
	}

	@Override
	public List<Message> getChatMessage(Integer chatId,User user) throws ChatException {
		Chat chat = chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(user)) {
			throw new ChatException("user not related to this chat");
		}
		
		List<Message> message = messageRepo.findByChatId(chatId);
		
		return message;
	}

	@Override
	public Message findMessageById(Integer messageId) {
		
		Optional<Message> opt = messageRepo.findById(messageId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public void deleteMessage(Integer messageId,User user) {
		
		Message message = findMessageById(messageId);
		
		if(message.getUser().getId().equals(user.getId())) {
			messageRepo.deleteById(messageId);
		}

	}
	
	@Override
	public List<Message> searchMessages(String query) {
	    return messageRepo.searchMessagesByContent(query);
	}

}
