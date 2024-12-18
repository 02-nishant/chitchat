package com.chat.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;



@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String content;
	
	private LocalDateTime time;
	
	@ManyToOne
	private User user;
	
	@ManyToOne 
	private Chat chat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(Integer id, String content, LocalDateTime time, User user, Chat chat) {
		super();
		this.id = id;
		this.content = content;
		this.time = time;
		this.user = user;
		this.chat = chat;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", time=" + time + ", user=" + user + ", chat=" + chat
				+ "]";
	}
	

}
