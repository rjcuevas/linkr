package com.linkr.url.dto;

import java.util.HashSet;
import java.util.Set;

import com.linkr.message.Result;
import com.linkr.url.domain.LinkrUrl;


public class UserUrlDTO {
	

	private Set<LinkrUrl> userUrls = new HashSet<LinkrUrl>();
	private Long urlID;
	private Result result;
	private Long userID;
	private String username;
	private String accesstoken;
	

	public Set<LinkrUrl> getUserUrls() {
		return userUrls;
	}
	public void setUserUrls(Set<LinkrUrl> userUrls) {
		this.userUrls = userUrls;
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
	public Long getUrlID() {
		return urlID;
	}
	public void setUrlID(Long urlID) {
		this.urlID = urlID;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
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
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
	
	

}
