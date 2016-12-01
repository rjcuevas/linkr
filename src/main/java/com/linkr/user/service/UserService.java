package com.linkr.user.service;

import com.linkr.user.domain.UserAccount;
import com.linkr.user.dto.UserDTO;


public interface UserService {
	
	UserAccount findByUsername(String username);
	
	void createUser(UserDTO userDTO);

	UserDTO getUserLogin(UserDTO userDTO);
	
	boolean isUserInfoExist(UserDTO userDTO);
	
	void setUserResult(boolean result, String msgResult, UserDTO userDTO);

}
