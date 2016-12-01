package com.linkr.util.validation;

import org.apache.commons.validator.routines.UrlValidator;

/**
 * Linkr Validations
 *
 */
public class LinkrValidator {
	
	
	/**
	 * Validates if URL is a valid URL address
	 * 
	 * @param urlAddress
	 * @return {@code true} if the url address is valid URL format 
	 */
	public static boolean isValidUrl(String urlVal) {
		
		String[] urlSchemes =  {"http","https"};
		UrlValidator urlValidator = new UrlValidator(urlSchemes);
		
		if (urlVal.length() > 400 || !urlValidator.isValid(urlVal)) {
			return false;
		}else{
			return true;
		}
		
	}

}
