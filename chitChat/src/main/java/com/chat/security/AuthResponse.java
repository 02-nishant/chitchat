package com.chat.security;

public class AuthResponse {
	
	private String jwt;
	private boolean isAuth;
	
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AuthResponse [jwt=" + jwt + ", isAuth=" + isAuth + "]";
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public boolean isAuth() {
		return isAuth;
	}

	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	public AuthResponse(String jwt, boolean isAuth) {
		super();
		this.jwt = jwt;
		this.isAuth = isAuth;
	}
	
	

}
