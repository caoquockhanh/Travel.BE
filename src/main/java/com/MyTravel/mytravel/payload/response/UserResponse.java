package com.MyTravel.mytravel.payload.response;

import java.util.List;

public class UserResponse {
	private String token;
	private String type = "Bearer";
	private String id;

	private String fullName;
	private String username;
	private String email;
	private List<String> roles;

	private String phoneNumber;

	public UserResponse(String accessToken, String id, String fullName, String username, String email, String phoneNumber, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.phoneNumber = phoneNumber;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getRoles() {
		return roles;
	}
}
