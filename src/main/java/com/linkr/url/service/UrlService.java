package com.linkr.url.service;

import java.util.HashMap;

import com.linkr.url.dto.ShortenUrlDTO;
import com.linkr.url.dto.UrlDTO;
import com.linkr.url.dto.UserUrlDTO;
import com.linkr.user.dto.UserDTO;


public interface UrlService {
	
	UrlDTO createURL(UrlDTO urlDTO);
	
	void setUrlResult(boolean result, String msgResult, UrlDTO urlDTO);

	ShortenUrlDTO regenerateShortUrl(UrlDTO urlDTO);
	
	HashMap<String, String> getUserUrl(UserDTO userDTO);

	void setUserUrlResult(boolean result, String msgResult, UserUrlDTO userUrlDTO);

	UserUrlDTO deleteUrl(UserUrlDTO userUrlDTO);
	
	HashMap<String,String> retrieveShortenUrl(Long urlID);

	UrlDTO updateUrl(Long urlID, Boolean isPublicURL);
	
	UrlDTO getUrl(Long urlID);

}
