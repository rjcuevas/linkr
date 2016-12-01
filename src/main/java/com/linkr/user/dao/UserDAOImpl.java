package com.linkr.user.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkr.message.LinkrAppException;
import com.linkr.message.MessageConstant;
import com.linkr.message.MessageService;
import com.linkr.user.domain.UserAccount;
import com.linkr.user.domain.UserInfo;
import com.linkr.user.dto.UserDTO;


@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Log logger = LogFactory.getLog(UserDAOImpl.class); 
		
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MessageService msgService;
	
	/**
	 * Create user info
	 * 
	 * @param userInfo
	 * @return UserInfo
	 * @throws Exception 
	 */
	public UserInfo createUserInfo(UserInfo userInfo) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #createUserAndUserInfo");
		
		try {
			em.persist(userInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.ERROR_SAVE_USER));	//throw encountered dao exception to service layer
		} 
		
		return userInfo;
		
	}

	
	/**
	 * Get user login using uname and pword
	 * 
	 * @param userDTO
	 * @return user
	 */
	public UserAccount getUserLogin(UserDTO userDTO) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #getUser");
		
		UserAccount user = new UserAccount();
		
		try {
			//Using entity manager createQuery
			Query q = em.createQuery("from UserAccount WHERE username = :name AND password = :password", UserAccount.class);
				q.setParameter("name", userDTO.getUsername());
				q.setParameter("password", userDTO.getPassword());
				
			user = (UserAccount) q.getSingleResult();	
			
			/**
			//Using hibernate criteria; for team code reference only
			Session session = em.unwrap(Session.class);
			
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", userDTO.getUsername()));
			cr.add(Restrictions.eq("password", userDTO.getPassword()));
			
			User user = (User) cr.uniqueResult();	//if user result is null - invalid user */
			       
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.INVALID_USER));	//throw encountered dao exception to service layer
		} 
		
		return user;
	}
	
	/**
	 * Get user login using uname and pword
	 * 
	 * @param userDTO
	 * @return user
	 */
	public UserInfo getUserInfo(UserDTO userDTO) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #getUserInfo");
		
		UserInfo userInfo = new UserInfo();
		
		try {
			//Using entity manager createQuery
			Query q = em.createQuery("from UserInfo WHERE (lower(first_name) = lower(:fname) AND lower(last_name) = lower(:lname) AND lower(email) = lower(:email)) OR lower(email) = lower(:email)", UserInfo.class);
				q.setParameter("fname", userDTO.getFirst_name());
				q.setParameter("lname", userDTO.getLast_name());
				q.setParameter("email", userDTO.getEmail());
				
				userInfo = (UserInfo) q.getSingleResult();	
			
			logger.info("UserInfo: " + userDTO.getFirst_name() + " " + userDTO.getLast_name() + " " + userDTO.getEmail());
			       
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.INVALID_USER));
		} 
		
		return userInfo;
	}
	
	
	/**
	 * Get User
	 * 
	 * @param userID
	 * @return user
	 */
	public UserAccount findUser(Long userID) throws LinkrAppException {
		UserAccount user = new UserAccount();
		
		try {
			user = em.find(UserAccount.class, userID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.USER_NOT_FOUND));
		}
		
		return user;
	}
	

}
