mainApp.service('userService', function() {
  var message;
  var userID;	
  var username;			//User object
  var accesstoken;

  var setMessage = function(msg) {
	  message = msg;
  };

  var getMessage = function(){
      return message;
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
	
  var clearUserService = function(){
	  message = undefined;
	  userID = undefined;
	  username = undefined;
  	  accesstoken = undefined;
  }
  
  return {
    setMessage: setMessage,
    getMessage: getMessage,
    setUserID: setUserID,
    getUserID: getUserID,
    setUsername: setUsername,
    getUsername: getUsername,
    setAccesstoken: setAccesstoken,
    getAccesstoken: getAccesstoken,
    clearUserService: clearUserService
  };

});