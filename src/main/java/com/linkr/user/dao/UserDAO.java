package com.linkr.user.dao;

import com.linkr.message.LinkrAppException;
import com.linkr.user.domain.UserAccount;
import com.linkr.user.domain.UserInfo;
import com.linkr.user.dto.UserDTO;


public interface UserDAO {

	UserInfo createUserInfo(UserInfo userInfo) throws LinkrAppException;

	UserAccount getUserLogin(UserDTO userDTO) throws LinkrAppException;
	
	UserInfo getUserInfo(UserDTO userDTO) throws LinkrAppException;
	
	UserAccount findUser(Long userID) throws LinkrAppException;

}
