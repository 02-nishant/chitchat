package com.chat.entity;

public class singleChat {
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "singleChat [userId=" + userId + "]";
	}

	public singleChat(Integer userId) {
		super();
		this.userId = userId;
	}

	public singleChat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
