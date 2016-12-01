package com.linkr.user.dto;

import com.linkr.authentication.JWTAuthentication;
import com.linkr.message.Result;


public class UserDTO {

	private Long userID;
	private String first_name;
	private String last_name;
	private String email;
	private String username;
	private String password;
	private Result result;
	private String accesstoken;
		
	
	public String getFirst_name() {
		return first_name;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Result getResult() {
		if(result == null){
			result = new Result();
		}
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(UserDTO userDTO) {
		userDTO.setPassword(null);
		JWTAuthentication jwt = new JWTAuthentication();
		this.accesstoken = jwt.setToken(userDTO);
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}
	
	
	

	
}
