package com.linkr.url.service;


import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkr.enums.URLShortnerEnum;
import com.linkr.message.LinkrAppException;
import com.linkr.message.MessageConstant;
import com.linkr.message.MessageService;
import com.linkr.url.dao.UrlDAO;
import com.linkr.url.domain.LinkrUrl;
import com.linkr.url.domain.ShortUrl;
import com.linkr.url.dto.ShortenUrlDTO;
import com.linkr.url.dto.UrlDTO;
import com.linkr.url.dto.UserUrlDTO;
import com.linkr.user.dao.UserDAO;
import com.linkr.user.domain.UserAccount;
import com.linkr.user.dto.UserDTO;


@Service
@Transactional
public class UrlServiceImpl implements UrlService{

	private static final Log logger = LogFactory.getLog(UrlServiceImpl.class); 
	
	@Autowired
	private UrlDAO urlDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MessageService msgService;
	
	
	/** URL Shortener Constants **/
	//Google URl shortener
	private static final String GOOGLE_URL_SHORT_API = "https://www.googleapis.com/urlshortener/v1/url";
	private static final String GOOGLE_API_KEY = "AIzaSyANg4SCvVXgeME-9YL0-uujulEjJYUhrSs";
	
	//Hecsu URL shortener
	private static final String HECSU_URL_SHORT_API = "https://hec.su/api?url=";
	private static final String HECSU_RESPONSE_TYPE = "&method=json";
	
	//lknSuite URL shortener
	private static final String LKNSUITE_URL_SHORT_API = "https://www.lknsuite.com/shortener/api/create?url=";
	private static final String LKNSUITE_RESPONSE_TYPE = "&slug=";
	
	//lknSuite constant
	private static SecureRandom random = new SecureRandom();
	private static final String SUCCESS = "1";
	private static final String FAILED = "0";
	
	
	/**
	 * Save User Url and 3 shortened urls
	 * 
	 * @param urlDTO
	 * @return UrlDTO
	 * @throws LinkrAppException 
	 */
	public UrlDTO createURL(UrlDTO urlDTO) {
		logger.info(this.getClass().getName() + " #createUrl");
		String longURL = urlDTO.getUrlAddress();
		
		try {
			
			//Validate first if long URL already exist to user
			boolean isLongUrlExist = urlDAO.getUserUrl(urlDTO.getUserID(), urlDTO.getUrlAddress());
			
			
			if (!isLongUrlExist) {
				
				//Generate 3 shortened URL
				String strGoogle = generateGoogleURL(longURL);
				String strHecsu = generateHecsuURL(longURL);
				String strLkn = generateLknSuiteURL(longURL);
				
				ShortUrl googleShortenedURL = setShortUrl(strGoogle, Long.valueOf(URLShortnerEnum.GOOGLE.getSrcID()));
				ShortUrl hecsuShortenedURL = setShortUrl(strHecsu,Long.valueOf(URLShortnerEnum.HECSU.getSrcID()));
				ShortUrl lknSuiteShortenedURL = setShortUrl(strLkn,Long.valueOf(URLShortnerEnum.LKNSUITE.getSrcID()));
				
				Set<ShortUrl> shortUrlSet = new HashSet<ShortUrl>();
				shortUrlSet.add(googleShortenedURL);
				shortUrlSet.add(hecsuShortenedURL);
				shortUrlSet.add(lknSuiteShortenedURL);
				
				//Save url and shortened URL
				LinkrUrl linkrUrl = saveURL(urlDTO, shortUrlSet);
				
				//assign created URL id back to response object
				urlDTO.setUrlID(linkrUrl.getUrlID());
				setUrlResult(true, "URL " + urlDTO.getUrlAddress() + " succesfully added", urlDTO);
			}else{
				setUrlResult(false, msgService.getMessage(MessageConstant.URL_EXIST),urlDTO);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			setUrlResult(false, "URL " + urlDTO.getUrlAddress()  + "  failed to add", urlDTO);
		}
		
		return urlDTO;
		
	}


	/**
	 * @param strShortURL
	 * @param srcLookupID
	 * @return
	 */
	private ShortUrl setShortUrl(String strShortURL, Long srcLookupID) {
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setSrcLookUpID(srcLookupID);
		shortUrl.setShortURLAddress(strShortURL);
		
		return shortUrl;
	}
	
	/**
	 * Save URL and shortened URLs
	 * 
	 * @param urlDTO
	 * @param shortUrlSet
	 * @return linkrUrl
	 * @throws LinkrAppException 
	 */
	private LinkrUrl saveURL(UrlDTO urlDTO, Set<ShortUrl> shortUrlSet) throws LinkrAppException {
		UserAccount user = userDAO.findUser(urlDTO.getUserID());
		
		LinkrUrl linkrUrl = new LinkrUrl();
		linkrUrl.setUrlAddress(urlDTO.getUrlAddress());
		linkrUrl.setPublic(false); // value is originally set false
		//linkrUrl.setShortUrlSet(shortUrlSet);
		linkrUrl.setUser(user);
		
		//Save URL
		urlDAO.createUrl(linkrUrl);
		
		//Save shortURL
		for (Iterator<ShortUrl> iterator = shortUrlSet.iterator(); iterator.hasNext();) {
			ShortUrl shortUrl = (ShortUrl) iterator.next();
			shortUrl.setLinkrUrl(linkrUrl);
			
			urlDAO.createShortUrl(shortUrl);
		}
		
		
		return linkrUrl;
	}


	/**
	 * Generate Google shortened URL
	 * 
	 * @param longURL
	 * @return shortUrl
	 * @throws LinkrAppException 
	 */
	private String generateGoogleURL(String longURL) throws LinkrAppException {
		String json = "{\"longUrl\": \"" + longURL + "\"}";
		String apiURL = GOOGLE_URL_SHORT_API + "?key=" + GOOGLE_API_KEY;
		
		return shortenURL(json, apiURL, URLShortnerEnum.GOOGLE);
	}


	/**
	 * Generate Hecsu shortened URL
	 * 
	 * @param longURL
	 * @return shortUrl
	 * @throws LinkrAppException 
	 */
	private String generateHecsuURL(String longURL) throws LinkrAppException {
		String json = "{\"long\": \"" + longURL + "\"}";
		String apiURL = HECSU_URL_SHORT_API + longURL + HECSU_RESPONSE_TYPE;
		
		return shortenURL(json, apiURL, URLShortnerEnum.HECSU);
	}


	/**
	 * Generate lknSuite shortened URL
	 * 
	 * @param longURL
	 * @return shortUrl
	 * @throws LinkrAppException 
	 */
	private String generateLknSuiteURL(String longURL) throws LinkrAppException {
		String json = "{\"long\": \"" + longURL + "\"}";
		String apiURL = LKNSUITE_URL_SHORT_API + longURL + LKNSUITE_RESPONSE_TYPE  + nextSessionId().substring(0, 10);
		
		return shortenURL(json, apiURL, URLShortnerEnum.LKNSUITE);
	}


	/**
	 * Common user result method for setting results
	 * 
	 * @param msgResult 
	 * @param result 
	 * @param urlDTO 
	 */
	public void setUrlResult(boolean result, String msgResult, UrlDTO urlDTO) {
		urlDTO.getResult().setResult(result);
		urlDTO.getResult().setResultMessage(msgResult);
	}
	
	/**
	 * Common user result method for setting results
	 * 
	 * @param msgResult 
	 * @param result 
	 * @param shortenUrlDTO 
	 * 
	 */
	public void setUrlResult(boolean result, String msgResult, ShortenUrlDTO shortenUrlDTO) {
		shortenUrlDTO.getResult().setResult(result);
		shortenUrlDTO.getResult().setResultMessage(msgResult);
	}
	
	
	/**
	 * Common url shortener logic
	 * 
	 * @param json
	 * @param apiURL
	 * @param toUrlShortener
	 * @return
	 */
	private static String shortenURL(String json, String apiURL, URLShortnerEnum toUrlShortener) throws LinkrAppException {	
		String strShortenedURL = "";
		
		try {
			
			HttpPost postRequest = new HttpPost(apiURL);						//POST i.e. [https://hec.su/api?url=http://www.jisho.org&method=json HTTP/1.1]
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setEntity(new StringEntity(json, "UTF-8"));
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpResponse response = httpClient.execute(postRequest);
			System.out.println(response.getEntity());
			String responseText = EntityUtils.toString(response.getEntity());	//string json result i.e. {"long":"http:\/\/www.jisho.org","short":"https:\/\/hec.su\/eMSV"}

			//Convert string result to JSONObject to extract the short url result
			JSONObject jsonObject = new JSONObject(responseText);
			
			if (toUrlShortener == URLShortnerEnum.GOOGLE) {
				strShortenedURL = jsonObject.getString("id");
			}else if (toUrlShortener == URLShortnerEnum.HECSU) {
				strShortenedURL = jsonObject.getString("short");
			}else if (toUrlShortener == URLShortnerEnum.LKNSUITE) {
				if(jsonObject.get("status").toString().equalsIgnoreCase(SUCCESS)) {
					strShortenedURL = jsonObject.getString("url");
				} else {
					UrlServiceImpl urlServiceImpl = new UrlServiceImpl();
					strShortenedURL = urlServiceImpl.generateLknSuiteURL(apiURL.substring(apiURL.lastIndexOf("url=")+4,apiURL.lastIndexOf("&")));
				}
			}
			
	
		} catch (MalformedURLException me) {
			logger.error(me.getMessage());
			return  me.getMessage();
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			return ioe.getMessage();
		}	
		
		logger.info("strShortenedURL: " + strShortenedURL);
		
		return strShortenedURL;
		
	}
	
	
	public static String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}
	
	
	/**
	 * Regenerate and save shortened URL
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 */
	public ShortenUrlDTO regenerateShortUrl(UrlDTO urlDTO) {
		ShortenUrlDTO shortenUrlDTO = new ShortenUrlDTO();
		
		try {
			ShortUrl sUrl = urlDAO.getShortUrl(urlDTO.getShortUrlID());
			
			if (sUrl != null) {
				boolean isGoogleUrl = sUrl.getSrcLookUpID().equals(Long.valueOf(URLShortnerEnum.GOOGLE.getSrcID()));
				boolean isHecsuUrl = sUrl.getSrcLookUpID().equals(Long.valueOf(URLShortnerEnum.HECSU.getSrcID()));
				boolean isLknUrl = sUrl.getSrcLookUpID().equals(Long.valueOf(URLShortnerEnum.LKNSUITE.getSrcID()));
				
				String regenShortUrl = "";
				String longUrl = sUrl.getLinkrUrl().getUrlAddress();
				
				if (isGoogleUrl) {
					regenShortUrl = generateGoogleURL(longUrl);
				}else if (isHecsuUrl) {
					regenShortUrl = generateHecsuURL(longUrl);
				}else if (isLknUrl) {
					regenShortUrl = generateLknSuiteURL(longUrl);
				}
				
				urlDAO.updateShortUrl(sUrl.getShortUrlID(), regenShortUrl);
				
				//assign URL id back to response object
				shortenUrlDTO.setShortenURL(regenShortUrl);
				setUrlResult(true, msgService.getMessage(MessageConstant.SUCCESS_REGENERATED), shortenUrlDTO);
			}else{
				setUrlResult(false, msgService.getMessage(MessageConstant.SHORTURL_NOT_FOUND), shortenUrlDTO);
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			setUrlResult(false, msgService.getMessage(MessageConstant.ERROR_REGENERATE), urlDTO);
		}
			
		return shortenUrlDTO;
	}


	/**
	 * Get User URL list
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 */
	public HashMap<String, String> getUserUrl(UserDTO userDTO) {
		logger.info(this.getClass().getName() + " #getUserUrl");
		
		//UserUrlDTO userUrlDTO = new UserUrlDTO();
		HashMap<String, String> urlMap = new HashMap<String, String>();
		
		try {
			UserAccount user = userDAO.findUser(userDTO.getUserID());

			Set<LinkrUrl> urls = user.getLinkUrls();
			
			for (LinkrUrl linkrUrl : urls) {
				urlMap.put(linkrUrl.getUrlID().toString(), linkrUrl.getUrlAddress());
			}
				
			logger.info("Success listing user URL");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return urlMap;
	}
	
	
	/**
	 * Result setting method for userUrlDTO
	 * 
	 * @param msgResult 
	 * @param result 
	 * @param userUrlDTO 
	 * 
	 */
	public void setUserUrlResult(boolean result, String msgResult, UserUrlDTO userUrlDTO) {
		userUrlDTO.getResult().setResult(result);
		userUrlDTO.getResult().setResultMessage(msgResult);
	}


	/**
	 * Delete URL
	 * 
	 * @param userUrlDTO
	 * @return userUrlDTO
	 */
	public UserUrlDTO deleteUrl(UserUrlDTO userUrlDTO) {
		logger.info(this.getClass().getName() + " #deleteUrl");
			
		try {
			//Find LinkrUrl by urlID
			LinkrUrl url = new LinkrUrl();
			url = urlDAO.findLinkrUrl(userUrlDTO.getUrlID());

			if (url != null) {
				//delete user URL
				urlDAO.deleteUrl(url);
				setUserUrlResult(true, msgService.getMessage(MessageConstant.SUCCESS_DELETE_URL), userUrlDTO);
			}else{
				setUserUrlResult(false, msgService.getMessage(MessageConstant.URL_NOT_FOUND), userUrlDTO);
			}

			logger.info("Success");
		} catch (Exception e) {
			logger.error(e.getMessage());
			setUserUrlResult(false, e.getMessage(), userUrlDTO);
		}
		
		return userUrlDTO;
	}
	
	
	/**
	 * Retrieve 3 shortened urls verify Long Url
	 * 
	 * @param urlID
	 * @return List
	 * @throws LinkrAppException
	 */
	public HashMap<String,String> retrieveShortenUrl(Long urlID) {
		logger.info(this.getClass().getName() + " #retrieveShortenUrl");
		
		List<ShortUrl> listOfShortUrls = new ArrayList<ShortUrl>();
		HashMap<String,String> shortUrlMap = new HashMap<String, String>();
		
		boolean isUrlExists = false;
		
		try {
			isUrlExists = urlDAO.isExistingUrl(urlID);
			
			if (isUrlExists) {
				listOfShortUrls = urlDAO.getShortUrlList(urlID);
		
				for (ShortUrl shortUrl : listOfShortUrls) {
					shortUrlMap.put("surl"+shortUrl.getSrcLookUpID().toString(), shortUrl.getShortURLAddress());
					shortUrlMap.put("surlid"+shortUrl.getSrcLookUpID().toString(), shortUrl.getShortUrlID().toString());
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			// setUrlResult(false, "Failed to retrieve shorten Url", urlID);
		}

		return shortUrlMap;
	}
	

	/**
	 * Update URL public mode
	 * 
	 * @param urlID
	 * @param isPublicURL
	 * @return urlDTO
	 */
	public UrlDTO updateUrl(Long urlID, Boolean isPublicURL) {
		logger.info(this.getClass().getName() + " #updateUrl");
		
		UrlDTO urlDTO = new UrlDTO();
		
		try {
			//find linkrUrl
			LinkrUrl url = new LinkrUrl();
			url = urlDAO.findLinkrUrl(urlID);
			
			boolean isModeChanged = isPublicURL != url.isPublic();
			
			if (url != null && isModeChanged) {
				//update URL
				urlDAO.updateUrl(urlID, isPublicURL);
			}else{
				if (!isModeChanged) {
					setUrlResult(true, msgService.getMessage(MessageConstant.INFO_NO_URL_CHANGES), urlDTO);
				}else {
					setUrlResult(true, msgService.getMessage(MessageConstant.URL_NOT_FOUND), urlDTO);
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			setUrlResult(false, e.getMessage(), urlDTO);
		}
		
		return urlDTO;
	}
	
	/**
	 * Get User URL list
	 * 
	 * @param urlDTO
	 * @return urlDTO
	 */
	public UrlDTO getUrl(Long urlID) {
		logger.info(this.getClass().getName() + " #getUrl");

		LinkrUrl user = new LinkrUrl();
		UrlDTO urlDTO = new UrlDTO();
		
		try {
			user = urlDAO.findLinkrUrl(urlID);
			urlDTO.setUrlAddress(user.getUrlAddress());
			urlDTO.setIsPublicURL(user.isPublic());
				
			logger.info("Success get URL");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return urlDTO;
	}
}
