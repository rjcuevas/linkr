package com.linkr.url.dto;

import com.linkr.message.Result;

public class ShortenUrlDTO {
	public String shortenURL;
	public String longUrl; // URL_ID
	private Result result; 
	private Long scrLookUpID; //SCR_LOOKUPID
	
	public String getShortenURL() {
		return shortenURL;
	}

	public void setShortenURL(String shortenURL) {
		this.shortenURL = shortenURL;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
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

	public Long getScrLookUpID() {
		return scrLookUpID;
	}

	public void setScrLookUpID(Long scrLookUpID) {
		this.scrLookUpID = scrLookUpID;
	}
}
