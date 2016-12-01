package com.linkr.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkr.user.domain.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByUsername(String username);
	
}
