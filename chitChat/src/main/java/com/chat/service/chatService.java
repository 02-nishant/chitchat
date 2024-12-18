package com.chat.service;

import java.util.List;

import com.chat.entity.Chat;
import com.chat.entity.User;
import com.chat.exception.ChatException;

public interface chatService {
	
	public Chat creatChat(User userId1, Integer userId2);
	
	public Chat findChatById(Integer chatId) throws ChatException;
	
	public List<Chat> findAllChat(Integer userId);
	
	public Chat createGroup(GroupChatRequest req, User user);
	
	public Chat addUserToGroup(Integer userId, Integer chatId , User admin);
	
	public Chat renameGroup(Integer chatId, String name , User user) throws ChatException;
	
	public Chat removeFromGroup(Integer chatId, Integer user, User admin);
	
	public void deleteChat (Integer chatId, Integer userId) throws ChatException;
	
	

}
