package com.linkr;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;



@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.linkr"})
public class LinkrAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkrAppApplication.class, args);
	}
	
	
	/**
	 * Locale
	 * 
	 * @return SessionLocaleResolver
	 */
	@Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);	//set default locale to US
        
        return slr;
    }
	
	/**
	 * Message Resource Bundle
	 * 
	 * @return ReloadableResourceBundleMessageSource
	 */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(3600); //refresh cache once per hour
        
        return messageSource;
    }

}
