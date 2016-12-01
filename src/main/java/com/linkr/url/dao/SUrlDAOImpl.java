package com.linkr.url.dao;

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
import com.linkr.url.domain.LinkrUrl;
import com.linkr.url.domain.ShortUrl;
import com.linkr.url.dto.ShortenUrlDTO;

@Repository
public class SUrlDAOImpl implements SUrlDAO {
	
	private static final Log logger = LogFactory.getLog(SUrlDAOImpl.class); 

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MessageService msgService;
	
	

	/**
	 * Get Shorten Url
	 * 
	 * @param shortenUrlDTO
	 * @return shortenUrlDTO
	 */
	public ShortUrl getGenShortUrl(ShortenUrlDTO shortenUrlDTO) throws LinkrAppException {
		
		logger.info(this.getClass().getName() + " #getGenShortUrl");
		
		ShortUrl shortUrl = new ShortUrl();
		
		try{
			
			Query q = em.createQuery("from SHORT_URL WHERE short_url_address = : shortUrlVal and urlID = :urlID", LinkrUrl.class);
			q.setParameter("short_url_address", shortenUrlDTO.getShortenURL());
			
			shortUrl = (ShortUrl) q.getSingleResult();	
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new LinkrAppException(msgService.getMessage(MessageConstant.INVALID_SHORTURL));	
		}
		
		return shortUrl;
	}

}
