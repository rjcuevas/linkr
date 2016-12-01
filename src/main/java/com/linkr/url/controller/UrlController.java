package com.linkr.url.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkr.authentication.JWTAuthentication;
import com.linkr.message.LinkrAppException;
import com.linkr.message.MessageConstant;
import com.linkr.message.MessageService;
import com.linkr.url.dao.UrlDAO;
import com.linkr.url.dto.ShortenUrlDTO;
import com.linkr.url.dto.UrlDTO;
import com.linkr.url.dto.UserUrlDTO;
import com.linkr.url.service.UrlService;
import com.linkr.user.dto.UserDTO;
import com.linkr.user.service.UserService;
import com.linkr.util.validation.LinkrValidator;


@RestController
public class UrlController {

	private static final Log logger = LogFactory.getLog(UrlController.class); 

	@Autowired
	private UserService userService;
	
	@Autowired
	private UrlService urlService;
	
	@Autowired
	private MessageService msgService;
	
	/**
	 * Creates/Submits URL
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 * @throws LinkrAppException 
	 */
	@RequestMapping(value = "/urlhome", method = RequestMethod.POST)
	public @ResponseBody UrlDTO submitURL(@RequestBody UrlDTO urlDTO) {
		logger.info(this.getClass().getName() + " # submitURL...");
		
		
		boolean isHttp = urlDTO.getUrlAddress().startsWith("http://");
		boolean isHttps = urlDTO.getUrlAddress().startsWith("https://");
		

		if (!isHttp && !isHttps) {
			urlDTO.setUrlAddress("http://" + urlDTO.getUrlAddress());
		}
		
		if(LinkrValidator.isValidUrl(urlDTO.getUrlAddress())){
			urlDTO = urlService.createURL(urlDTO);
			urlDTO.setAccesstoken(urlDTO);
			logger.info("Succesfully Added " + urlDTO.getUrlAddress());
		}else{
			logger.error("Invalid long URL " + urlDTO.getUrlAddress());
			urlService.setUrlResult(false, msgService.getMessage(MessageConstant.INVALID_URL), urlDTO);
		}
		
		return urlDTO;

	}

	@RequestMapping(value = "/regenerate", method = RequestMethod.POST)
	public @ResponseBody ShortenUrlDTO regenerateShortUrl(@RequestBody UrlDTO urlDTO) {
		logger.info(this.getClass().getName() + " # regenerateShortUrl...");
		
		ShortenUrlDTO shortenUrlDTO = new ShortenUrlDTO(); 
		
		urlDTO.setShortUrlID(urlDTO.getShortUrlID());
		
		if (urlDTO.getShortUrlID() != null) {
			shortenUrlDTO = urlService.regenerateShortUrl(urlDTO);
			logger.info("Succesfully updated short URl.");
		}else{
			logger.error("No short URL ID.");
			urlService.setUrlResult(false, msgService.getMessage(MessageConstant.ERROR_REGENERATE), urlDTO);
		}
		
		return shortenUrlDTO;

	}
	
	/**
	 * Receive /Submits URL
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 * @throws LinkrAppException 
	 */
	@RequestMapping(value = "/urlhome", method = RequestMethod.GET)
	public @ResponseBody UserDTO recieveURL(@RequestParam(value = "userID", required = false) String userID, 
			@RequestParam(value = "username", required = false) String userName, 
				@RequestParam(value = "accesstoken", required = false) String accessToken) {
		logger.info(this.getClass().getName() + " # recieveUser...");
		
		UserDTO userDTO = new UserDTO()
		;
		boolean isUserLogin = false;

		if(userID != null && accessToken != null) {
			JWTAuthentication jwt = new JWTAuthentication();
			isUserLogin = jwt.getVerifiedToken(accessToken).get("userID").toString().equals(userID);
			if (isUserLogin) {
				userService.setUserResult(isUserLogin, "", userDTO);
			} else {
				userService.setUserResult(false, msgService.getMessage(MessageConstant.INVALID_ACCESS_URL), userDTO);
				userDTO.setUserID(new Long(userID));
				userDTO.setUsername(userName);
				userDTO.setAccesstoken(userDTO);
			}
		} else {
			userService.setUserResult(false, msgService.getMessage(MessageConstant.INVALID_ACCESS_URL), userDTO);
		}
		
		return userDTO;
	}

	/**
	 * Get User URL list
	 * 
	 * @param userID
	 * @param userName
	 * @param accessToken
	 * @return  urlMap
	 */
	@RequestMapping(value = "/userUrl", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, String> getUserUrl(
			@RequestParam(value = "userID", required = false) String userID,
			@RequestParam(value = "username", required = false) String userName, 
			@RequestParam(value = "accesstoken", required = false) String accessToken) {
		logger.info(this.getClass().getName() + " # getUserUrl...");
		logger.info(this.getClass().getName() + " # UserID..." + userID);
	
		UserDTO userDTO = new UserDTO();
		userDTO.setUserID(new Long(userID==null?"0":userID));
		
		HashMap<String, String> urlMap = new HashMap<String, String>();

		if (userDTO.getUserID() != null) {
			urlMap = urlService.getUserUrl(userDTO);
		}else{
			logger.error("Invalid user id.");
		}
		
		return urlMap;

	}
	
	
	/**
	 * Delete user URL
	 * 
	 * @param userUrlDTO
	 * @return HashMap
	 */
	@RequestMapping(value = "/deleteUrl", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> deleteUserUrl(@RequestBody UserUrlDTO userUrlDTO) {
		logger.info(this.getClass().getName() + " # deleteUserUrl...");
		
		if (userUrlDTO.getUrlID() != null) {
			userUrlDTO = urlService.deleteUrl(userUrlDTO);
		}else{
			logger.error("No Url ID.");
			urlService.setUserUrlResult(false, msgService.getMessage(MessageConstant.ERROR_USER_REQUIRED), userUrlDTO);
		}
		
		return getUserUrl(userUrlDTO.getUserID().toString(), userUrlDTO.getUsername(), userUrlDTO.getAccesstoken());

	}
	
	/**
	 * Delete URL from shorten page
	 * 
	 * @param userUrlDTO
	 * @return HashMap
	 */
	@RequestMapping(value = "/deleteUrlSurl", method = RequestMethod.POST)
	public @ResponseBody UserUrlDTO deleteUrlSurl(@RequestBody UserUrlDTO userUrlDTO) {
		logger.info(this.getClass().getName() + " # deleteUserUrl...");
		
		if (userUrlDTO.getUrlID() != null) {
			userUrlDTO = urlService.deleteUrl(userUrlDTO);
		}else{
			logger.error("No Url ID.");
			urlService.setUserUrlResult(false, msgService.getMessage(MessageConstant.URL_ID_REQUIRED), userUrlDTO);
		}
		
		return userUrlDTO;
	}
	
	
	/**
	 * Get shorten page
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 */
	@RequestMapping(value = "/shortenurl", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> receiveUrl(@RequestParam(value = "urlID", required = false) String urlID,
															@RequestParam(value = "accesstoken", required = false) String accessToken) {
		
		logger.info(this.getClass().getName() + " # receiveUrl..." );
		
		if(urlID == null && accessToken != null) {
			JWTAuthentication jwt = new JWTAuthentication();
			urlID = jwt.getVerifiedToken(accessToken).get("urlID").toString();
		}
		HashMap<String,String> mappedShortURL = new HashMap<String,String>();
		
		Long getUrlId = Long.parseLong(urlID);
		mappedShortURL = urlService.retrieveShortenUrl(getUrlId);
		
		logger.info("Succesfully retrieved Shorten Url.");
		
		return mappedShortURL; 

	}
	
	
	/**
	 * Update URL public mode
	 * 
	 * @param urlID
	 * @return urlDTO
	 */
	@RequestMapping(value = "/publicMode", method = RequestMethod.POST)
	public @ResponseBody UrlDTO publicMode(@RequestBody UrlDTO urlDTO) {
		logger.info(this.getClass().getName() + " # publicMode...");
		
		if (urlDTO.getUrlID() != null && urlDTO.getIsPublicURL() != null) {
			urlDTO = urlService.updateUrl(urlDTO.getUrlID(), urlDTO.getIsPublicURL());
			logger.info("Succesfully updated URL.");
		}else{
			logger.error("Error saving public URL.");
			urlService.setUrlResult(false, msgService.getMessage(MessageConstant.URL_ID_REQUIRED), urlDTO);
		}
		
		return urlDTO;

	}
	
	/**
	 * Receive /Submits URL
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 * @throws LinkrAppException 
	 */
	@RequestMapping(value = "/userProfile", method = RequestMethod.GET)
	public @ResponseBody UserDTO recieveUserProfile(@RequestParam(value = "userID", required = false) String userID, 
			@RequestParam(value = "username", required = false) String userName, 
				@RequestParam(value = "accesstoken", required = false) String accessToken) {
		logger.info(this.getClass().getName() + " # recieveUser...");
		
		UserDTO userDTO = new UserDTO()
		;
		boolean isUserLogin = false;

		if(userID != null && accessToken != null) {
			JWTAuthentication jwt = new JWTAuthentication();
			isUserLogin = jwt.getVerifiedToken(accessToken).get("userID").toString().equals(userID);
			if (isUserLogin) {
				userService.setUserResult(isUserLogin, "", userDTO);
			} else {
				userService.setUserResult(false, msgService.getMessage(MessageConstant.INVALID_ACCESS_URL), userDTO);
				userDTO.setUserID(new Long(userID));
				userDTO.setUsername(userName);
				userDTO.setAccesstoken(userDTO);
			}
		} else {
			userService.setUserResult(false, msgService.getMessage(MessageConstant.INVALID_ACCESS_URL), userDTO);
		}
		
		return userDTO;
	}



	/**
	 * redirect user URL
	 * 
	 * @param userUrlDTO
	 * @return HashMap
	 */
	@RequestMapping(value = "/redirectSUrl", method = RequestMethod.POST)
	public @ResponseBody UrlDTO redirectSUrl(@RequestBody UrlDTO urlDTO) {
		logger.info(this.getClass().getName() + " # redirectSUrl...");
		urlDTO.setAccesstoken(urlDTO);

		return urlDTO;

	}
	
	/**
	 * Get shorten page
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 */
	@RequestMapping(value = "/getUrlDetails", method = RequestMethod.GET)
	public @ResponseBody UrlDTO getUrlDetails(@RequestParam(value = "urlID", required = false) String urlID,
															@RequestParam(value = "accesstoken", required = false) String accessToken) {
		
		logger.info(this.getClass().getName() + " # getUrlDetails..." );
		
		UrlDTO urlDTO = new UrlDTO();
		
		if(urlID == null && accessToken != null) {
			JWTAuthentication jwt = new JWTAuthentication();
			urlID = jwt.getVerifiedToken(accessToken).get("urlID").toString();
			urlDTO = urlService.getUrl(new Long(urlID));
		} else {
			urlDTO = urlService.getUrl(new Long(urlID));
		}
		
		
		return urlDTO; 

	}
	
}
