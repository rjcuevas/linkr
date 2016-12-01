package com.linkr.user.service;


import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkr.authentication.UserRepository;
import com.linkr.message.MessageConstant;
import com.linkr.message.MessageService;
import com.linkr.user.dao.UserDAO;
import com.linkr.user.domain.UserAccount;
import com.linkr.user.domain.UserInfo;
import com.linkr.user.dto.UserDTO;


@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class); 
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageService msgService;
	
	
	public UserAccount findByUsername(String username){
		UserAccount user = userRepository.findByUsername(username);
		return user;
	}
	
	
	/**
	 * Creates user
	 * 
	 * @param userDTO
	 * @return User
	 */
	public void createUser(UserDTO userDTO){
		logger.info(this.getClass().getName() + " #createUser");
		
		UserAccount user = new UserAccount();
		UserInfo userInfo = new UserInfo();
		
		try {
			//Set User Entity
			user.setUsername(userDTO.getUsername());
			user.setPassword(getSecurePassword(userDTO.getPassword(),userDTO.getUsername()));
			
			//Set UserInfo
			userInfo.setFirst_name(userDTO.getFirst_name());
			userInfo.setLast_name(userDTO.getLast_name());
			userInfo.setEmail(userDTO.getEmail());
			userInfo.setUser(user);
			
			//Execution of saving 
			userDAO.createUserInfo(userInfo);
			
			setUserResult(true, "User " + userDTO.getUsername() + " successfully created", userDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			setUserResult(false, "User " + userDTO.getUsername() + "  failed to create", userDTO);
		}
		
	}


	/**
	 * Get user login using uname and pword
	 * 
	 * @param userDTO
	 * @return user
	 */
	@Transactional(readOnly=true)
	public UserDTO getUserLogin(UserDTO userDTO) {
		logger.info(this.getClass().getName() + " #getUserLogin");
		
		UserAccount user = null;
		
		try {
			
			userDTO.setPassword(getSecurePassword(userDTO.getPassword(),userDTO.getUsername()));
			user = userDAO.getUserLogin(userDTO);
			userDTO.setUserID(user.getUserID());
			
			if (user != null) {
				setUserResult(true, msgService.getMessage(MessageConstant.VALID_USER), userDTO);
			}
		} catch (Exception e) {
			logger.error("error msg service test ---" + e.getMessage());
			setUserResult(false, e.getMessage() , userDTO);
		}
		
		return userDTO;
	}
	
	/**
	 * Get user login using uname and pword
	 * 
	 * @param userDTO
	 * @return user
	 */
	@Transactional(readOnly=true)
	public boolean isUserInfoExist(UserDTO userDTO) {
		logger.info(this.getClass().getName() + " #isUserInfoExist");
		
		UserInfo userInfo = null;
		boolean isExist = false;
		
		try {
		
			userInfo = userDAO.getUserInfo(userDTO);
			
			if (userInfo != null) {
				setUserResult(false, "User already exist", userDTO);
				isExist = true;
			}
			
		} catch (Exception e) {
			logger.error("error msg service test ---" + e.getMessage());
			setUserResult(false, e.getMessage() , userDTO);
		}
		
		return isExist;
	}


	/**
	 * Common user result method for setting results
	 * 
	 * @param msgResult 
	 * @param result 
	 * @param userDTO 
	 * 
	 */
	public void setUserResult(boolean result, String msgResult, UserDTO userDTO) {
		userDTO.getResult().setResult(result);
		userDTO.getResult().setResultMessage(msgResult);
	}
	
	public String getSecurePassword(String passwordToHash, String salt) {
		logger.info(this.getClass().getName() + " #SecurePassword");
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			generatedPassword = sb.toString();
		} catch (Exception e) {
			logger.error("error msg service test ---" + e.getMessage());
		}
		return generatedPassword;
	}
	

}
