package com.linkr.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@Component
public class MessageServiceImpl implements MessageService {
	
	@Autowired
    private MessageSource messageSource;

	/**
	 * Get resource message defined in message.properties
	 * 
	 */
	public String getMessage(String id, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        
        return messageSource.getMessage(id,args,locale);
}

}
