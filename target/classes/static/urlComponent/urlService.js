mainApp.service('urlService', function() {
	var urlId;
    var userID;	
    var username;
	var longUrl;
	var accesstoken;

	var setLongUrl = function(lUrl) {
		longUrl = lUrl;
	};

	var getLongUrl = function(){
	    return longUrl;
	};
	
	var setUrlId = function(uID) {
		urlId = uID;
	};

	var getUrlId = function(){
	    return urlId;
	};
	  
	var setUserID = function(loggedUserID){
		userID = loggedUserID;
	};
	  
	var getUserID = function(){
		return userID;
	}
	  
	  
	var setUsername = function(loggedUsername){
		username = loggedUsername;
	};
	  
	var getUsername = function(){
		return username;
	}
	  
	var setAccesstoken = function(accessToken){
		accesstoken = accessToken;
	 };
  
    var getAccesstoken = function(){
	    return accesstoken;
    }
	
    var clearUrlService = function(){
    	urlId = undefined;
        userID = undefined;
        username = undefined;
    	longUrl = undefined;
    	accesstoken = undefined;
    }

	return {
		setLongUrl: setLongUrl,
	    getLongUrl: getLongUrl,
	    getUrlId: getUrlId,
	    setUrlId: setUrlId,
	    setUserID: setUserID,
	    getUserID: getUserID,
	    setUsername: setUsername,
	    getUsername: getUsername,
	    setAccesstoken: setAccesstoken,
	    getAccesstoken: getAccesstoken,
	    clearUrlService: clearUrlService
	  };
});