package com.linkr.url.dao;

import java.util.List;

import com.linkr.message.LinkrAppException;
import com.linkr.url.domain.LinkrUrl;
import com.linkr.url.domain.ShortUrl;

public interface UrlDAO {
	
	LinkrUrl createUrl(LinkrUrl url) throws LinkrAppException ;
	
	ShortUrl createShortUrl(ShortUrl url) throws LinkrAppException ;

	boolean getUserUrl(Long userID, String urlAddress) throws LinkrAppException;

	ShortUrl getShortUrl(Long shortUrlID)  throws LinkrAppException;

	void updateShortUrl(Long shortUrlID, String regenShortUrl) throws LinkrAppException;

	void deleteUrl(LinkrUrl url) throws LinkrAppException;

	LinkrUrl findLinkrUrl(Long urlID) throws LinkrAppException;
	
	List<ShortUrl> getShortUrlList(Long urlID) throws LinkrAppException;
	
	boolean isExistingUrl(Long urlID) throws LinkrAppException;

	void updateUrl(Long urlID, Boolean isPublicURL) throws LinkrAppException;

	
}
