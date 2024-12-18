package com.chat.entity;

public class updateUser {
	
	private String name;
	private String picture;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "updateUser [name=" + name + ", picture=" + picture + "]";
	}
	public updateUser(String name, String picture) {
		super();
		this.name = name;
		this.picture = picture;
	}
	public updateUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
