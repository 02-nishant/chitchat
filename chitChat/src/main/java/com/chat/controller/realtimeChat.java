package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.entity.Message;

@Controller
public class realtimeChat {
	
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
    public realtimeChat(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
	
	@MessageMapping("/message")
	@SendTo("/group/public")
	public Message recieveMessage(@Payload  Message message) {
		
		simpMessagingTemplate.convertAndSend("/group/" + message.getChat().getId().toString(), message);
		
		return message;
	}

}
