package com.linkr.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.linkr.user.domain.UserAccount;
import com.linkr.user.service.UserService;

@Service
public class UserDetailsSecurityService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserAccount user = userService.findByUsername(username);
		if(user == null) {
			// User Not Found
			return null;
		}
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		User userDetails = new User(user.getUsername(), user.getPassword(),true,true,true,true,grantedAuthorities);
		
		return userDetails;
	}

}
