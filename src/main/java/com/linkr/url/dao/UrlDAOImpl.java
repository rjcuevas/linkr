package com.linkr.url.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkr.message.LinkrAppException;
import com.linkr.message.MessageConstant;
import com.linkr.message.MessageService;
import com.linkr.url.domain.LinkrUrl;
import com.linkr.url.domain.ShortUrl;
import com.linkr.user.domain.UserAccount;


@Repository
public class UrlDAOImpl implements UrlDAO{
	
	private static final Log logger = LogFactory.getLog(UrlDAOImpl.class); 
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MessageService msgService;
	
	/**
	 * Create URL
	 * 
	 * @param linkrUrl
	 * @return linkrUrl
	 * @throws LinkrAppException
	 */
	public LinkrUrl createUrl(LinkrUrl linkrUrl) throws LinkrAppException  {
		logger.info(this.getClass().getName() + " #createUrl");
		
		try {
			em.persist(linkrUrl);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.ERROR_URL_SAVE));	
		} finally{
			
		}
		
		return linkrUrl;
	}
	
	/**
	 * Save Short URL
	 * 
	 * @param shortUrl
	 * @return shortUrl
	 * @throws LinkrAppException
	 */
	public ShortUrl createShortUrl(ShortUrl shortUrl) throws LinkrAppException  {
		logger.info(this.getClass().getName() + " #createShortUrl");
		
		try {
			em.merge(shortUrl);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.ERROR_SHORTURL_SAVE));	
		} finally{
			
		}
		
		return shortUrl;
	}

	/**
	 * Get User URL address
	 * 
	 * @param userID
	 * @param urlAddress
	 * @return {@code false} if the user url address already exists
	 * @throws LinkrAppException
	 */
	@SuppressWarnings("unchecked")
	public boolean getUserUrl(Long userID, String urlAddress) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #getUserUrl");
		
		boolean isUrlExist = false;
		
		try{
			List<LinkrUrl> listLinkrUrls = new ArrayList<LinkrUrl>();

			Session session = em.unwrap(Session.class);			
			Criteria cr = session.createCriteria(LinkrUrl.class);
			cr.createAlias("user", "user");
			cr.add(Restrictions.eq("user.userID", userID));
			cr.add(Restrictions.eq("urlAddress", urlAddress));
						
			listLinkrUrls = cr.list();
			
			if (listLinkrUrls.size() > 0) {
				isUrlExist = true;
				logger.info("Unique user URL.");
			} 
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.INVALID_URL));	//throw encountered dao exception to service layer
		}
		
		return isUrlExist;
	}


	/**
	 * Get ShortURL by shortUrlID
	 * 
	 * @param userID
	 * @return user
	 * @throws LinkrAppException
	 */
	public ShortUrl getShortUrl(Long shortUrlID) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #getShortUrl");
		
		ShortUrl sUrl = new ShortUrl();
		
		try {
			sUrl = em.find(ShortUrl.class, shortUrlID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.SHORTURL_NOT_FOUND));
		}
		
		return sUrl;
	}

	/**
	 * Update Short URL
	 * 
	 * @param shortUrlID
	 * @param regenShortUrl
	 * @return
	 */
	public void updateShortUrl(Long shortUrlID, String regenShortUrl) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #updateShortUrl");
		
		try {
			
			Query q = em.createQuery("Update ShortUrl SET shortURLAddress = :shortURLAddress where shortUrlID = :shortUrlID");
			q.setParameter("shortURLAddress", regenShortUrl);
			q.setParameter("shortUrlID", shortUrlID);
			
		    q .executeUpdate();
		    
		    logger.info("Success update short URL.");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.ERROR_REGENERATE));
		}
	
	}


	/**
	 * Delete URL
	 * 
	 * @param urlID
	 * @return {@code true} if the url is deleted
	 */
	public void deleteUrl(LinkrUrl url) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #deleteUrl");
		
		try {
			em.remove(url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.ERROR_DELETE_URL));
		}
		
	}

	/**
	 * Find LinkrUrl by Url ID
	 * 
	 * @param urlID
	 * @return userUrl
	 */
	public LinkrUrl findLinkrUrl(Long urlID) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #findLinkrUrl");
		
		LinkrUrl userUrl = new LinkrUrl();
		
		try {
			userUrl = em.find(LinkrUrl.class, urlID);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.URL_NOT_FOUND));
		}
		
		return userUrl;
	}
	
	/**
	 * Get all short url 
	 * 
	 * @param urlID
	 * 
	 * @return List of ShortUrl
	 * @throws LinkrAppException
	 */
	@SuppressWarnings("unchecked")
	public List<ShortUrl> getShortUrlList(Long urlID) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #getShortUrlList");
		
		List<ShortUrl> listOfShortUrls = new ArrayList<ShortUrl>();
		
		try{	
			Session session = em.unwrap(Session.class);		
			
			Criteria cr = session.createCriteria(ShortUrl.class);
			cr.createAlias("linkrUrl", "linkrUrl");
			cr.add(Restrictions.eq("linkrUrl.urlID", urlID));
			
			listOfShortUrls = cr.list();
				
			if (listOfShortUrls.isEmpty()) {
				logger.info("Empty List of Short URL");
			} else{
				logger.info("Contains list of Shorten URLs");
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new LinkrAppException(MessageConstant.INVALID_URL);	//throw encountered dao exception to service layer
		}
		return listOfShortUrls;
	}
	
	/**
	 * Checks if Url Exists
	 * 
	 * @param urlID
	 * 
	 * @return List of ShortUrl
	 * @throws LinkrAppException
	 */
	public boolean isExistingUrl(Long urlID) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #isExistingUrl");
		
		boolean isExisting = false;
		LinkrUrl url = new LinkrUrl();
		
		try{
		
			url = em.find(LinkrUrl.class, urlID);
			if(url.getUrlAddress() != null){
				isExisting = true;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new LinkrAppException(MessageConstant.INVALID_URL);	//throw encountered dao exception to service layer
		}
		return isExisting;
	}
	
	/**
	 * Update URL public mode
	 * 
	 * @param urlID
	 * @param isPublicURL
	 * @return urlDTO
	 */
	public void updateUrl(Long urlID, Boolean isPublicURL) throws LinkrAppException {
		logger.info(this.getClass().getName() + " #updateUrl");
		
		try {
			
			Query q = em.createQuery("Update LinkrUrl SET is_public = :is_public  where urlID = :urlID");
			q.setParameter("is_public", isPublicURL);
			q.setParameter("urlID", urlID);
			
		    q .executeUpdate();
		    
		    logger.info("Update URL success.");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.ERROR_UPDATE_URL));
		}
		
	}

}
