package com.chat.service;

import java.util.List;

import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.exception.ChatException;

public interface messageService {
	
	public Message sendMessage(sendMessageRequest req)throws ChatException;
	public List<Message> getChatMessage(Integer chatId,User user) throws ChatException;
	public Message findMessageById(Integer messageId);
	public void deleteMessage(Integer messageId,User user);
	public List<Message> searchMessages(String query);
}
