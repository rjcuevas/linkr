package com.linkr.user.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
import com.linkr.message.MessageConstant;
import com.linkr.message.MessageService;
import com.linkr.user.dto.UserDTO;
import com.linkr.user.service.UserService;


@RestController
public class UserController {
	
	private static final Log logger = LogFactory.getLog(UserController.class); 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService msgService;

	
	/**
	 * Create User
	 * This controller will be able to create user and userinfo
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)	
	public @ResponseBody UserDTO createUser(@RequestBody UserDTO userDTO){
		
		logger.info(this.getClass().getName() + " # creatingUser...");
		
		if(validateEmail(userDTO.getEmail())){
			if(!userService.isUserInfoExist(userDTO)){
				userService.createUser(userDTO);
				logger.info("Welcome User " + userDTO.getUsername());
			}
		} else {
			userService.setUserResult(false, msgService.getMessage(MessageConstant.INVALID_EMAIL, userDTO.getUsername()), userDTO);
			logger.info("Invalid Email " + userDTO.getUsername());
		}
		
		
		return userDTO;
	}
	

	/**
	 * Receive /Submits URL
	 * 
	 * @param userDTO
	 * @return userDTO
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody boolean recieveUser(@RequestParam(value = "userID", required = false) String userID, 
												@RequestParam(value = "accesstoken", required = false) String accessToken) {
		logger.info(this.getClass().getName() + " # recieveUser...");
		
		boolean isUserLogin = false;
		
		if(userID != null && accessToken != null) {
			JWTAuthentication jwt = new JWTAuthentication();
			isUserLogin = jwt.getVerifiedToken(accessToken).get("userID").toString().equals(userID);
		}
		
		return isUserLogin;
	}
	
	/**
	 * Login User
	 * 
	 * @param userDTO
	 * @return userDTO
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)	
	public @ResponseBody UserDTO login(@RequestBody UserDTO userDTO) {	
		
		logger.info(this.getClass().getName() + " # login");
		
		if (StringUtils.isNotBlank(userDTO.getUsername()) &&  StringUtils.isNotBlank(userDTO.getPassword())) {
			userDTO = userService.getUserLogin(userDTO);
			userDTO.setAccesstoken(userDTO);
		}else{
			userService.setUserResult(false, msgService.getMessage(MessageConstant.LOGIN_REQUIRED), userDTO);
		}
		
		logger.info("Result: " + userDTO.getResult().isResult() + " -- Msg: " + userDTO.getResult().getResultMessage());
		return userDTO;
		
	}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,5}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	        return matcher.find();
	}
	
}
