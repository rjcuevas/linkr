package com.linkr.message;

/**
 * Message Constants
 *
 */
public class MessageConstant {
	
	/*** Success Messages ***/
	public static final String SUCCESS_REGENERATED = "succ.regenerate";
	public static final String SUCCESS_USER_URL = "succ.user.url";
	public static final String SUCCESS_DELETE_URL = "succ.delete.url";

	
	/*** Error Messages ***/
	
	//USER
	public static final String LOGIN_REQUIRED = "err.required.login";
	public static final String INVALID_USER = "err.invalid.user";
	public static final String USER_NOT_FOUND = "err.notfound.user";
	public static final String ERROR_USER_REQUIRED = "err.required.userID";
	public static final String INVALID_EMAIL = "err.invalid.email";
	public static final String ERROR_SAVE_USER = "err.save.user";
	

	//URL
	public static final String URL_REQUIRED = "err.required.url";
	public static final String SHORTURL_REQUIRED = "err.required.shorturl";
	public static final String INVALID_URL = "err.invalid.url";
	public static final String INVALID_SHORTURL = "err.invalid.shorturl";
	public static final String URL_EXIST = "err.exist.url";
	public static final String ERROR_SHORTURL_SAVE = "err.save.shorturl";
	public static final String SHORTURL_NOT_FOUND = "err.notfound.shorturl";
	public static final String ERROR_URL_SAVE = "err.save.url";
	public static final String ERROR_REGENERATE = "err.regenerate.shorturl";
	public static final String ERROR_USER_URL = "err.list.url";
	public static final String URL_NOT_FOUND = "err.notfound.url";
	public static final String ERROR_DELETE_URL = "err.delete.url";
	public static final String INVALID_ACCESS_URL = "err.invalidaccess.url";
	public static final String ERROR_UPDATE_URL = "err.update.url";
	public static final String URL_ID_REQUIRED = "err.required.urlID";

	
	/*** Info Messages ***/
	public static final String VALID_USER = "info.valid.user";
	public static final String VALID_URL = "info.valid.url";
	public static final String VALID_SHORTURL = "info.valid.shorturl";
	public static final String INFO_NO_URL_CHANGES = "info.nochanges.url";
	
	
}
