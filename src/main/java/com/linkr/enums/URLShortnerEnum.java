package com.linkr.enums;

/**
 * URL Shortener Enums
 *
 */
public enum URLShortnerEnum {
	
	GOOGLE(1),
	HECSU(2),
	LKNSUITE(3);
	
	private final Integer srcID;
	
	URLShortnerEnum(Integer srcID) {
        this.srcID = srcID;
    }
	    
    public Integer getSrcID() {
        return this.srcID;
    }

}
