package com.linkr.url.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.linkr.authentication.JWTAuthentication;
import com.linkr.message.Result;
import com.linkr.user.dto.UserDTO;

public class UrlDTO {
	
	private Long urlID;
	private String urlAddress;
	private Boolean isPublicURL;
	private Long userID;
	private UserDTO userDTO;
	private Result result;
	private String username;
	private String accesstoken;
	
	//TODO: local dev only
	private Long shortUrlID;
	
	@NotNull(message = "User ID required.")
	public Long getUrlID() {
		return urlID;
	}
	public void setUrlID(Long urlID) {
		this.urlID = urlID;
	}
	
	public String getUrlAddress() {
		return urlAddress;
	}
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	public Boolean getIsPublicURL() {
		return isPublicURL;
	}
	public void setIsPublicURL(Boolean isPublicURL) {
		this.isPublicURL = isPublicURL;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public Result getResult() {
		if (result == null) {
			result = new Result();
		}
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Long getShortUrlID() {
		return shortUrlID;
	}
	public void setShortUrlID(Long shortUrlID) {
		this.shortUrlID = shortUrlID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(UrlDTO urlDTO) {
		JWTAuthentication jwt = new JWTAuthentication();
		this.accesstoken = jwt.setToken(urlDTO);
	}
	
	

}
