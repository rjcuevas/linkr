mainApp.controller('enquiryController', function($scope, $http, urlService, userService, $location){
	
	$scope.isDisabled = false;
	$scope.username = userService.getUsername();
	
	$http.get('userProfile', {
        params: {
        	userID: userService.getUserID(),
        	username: userService.getUsername(),
            accesstoken: $location.path().replace("/userProfile/","")
        }
     })
     .success(function (data,status) {
    	 if(!data.result.result) {
        	 if(data.accesstoken == null){
            	 userService.setMessage(data.result.resultMessage);
            	 $location.path('/');
        	 } else {
            	 userService.setMessage(data.result.resultMessage);
        		 $location.path('/userProfile/'+data.accesstoken);
        	 }
         }
     })
     .error(function(data, status) {
			console.log( "failure message ");
	 });
	
	//get user url function
	$http.get('userUrl', {
        params: {
        	userID: userService.getUserID(),
        	username: userService.getUsername(),
            accesstoken: userService.getAccesstoken()
        }
     })
     //response when user url are listed
     .success(function (data,status) {
    	 $scope.urlList = data;
    	 
     })   
     .error(function(data, status) {
			console.log( "failure message ");
	 });
		
	//delete url function
	$scope.deleteUrl = function(urlID) {
		
		//data object == backend DTO
		var dataObj = {
			urlID : urlID,
        	userID: userService.getUserID(),
        	username: userService.getUsername(),
            accesstoken: userService.getAccesstoken()
		}

		var res = $http.post('/deleteUrl', dataObj);

		res["Content-Type"] = "application/json; charset=utf-8";

		res.success(function(data, status, headers, config) {
			$scope.urlList = data;
			console.log("success url delete.");
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data : data
			}));
		});
	

	}
	
	//redirect url function
	$scope.redirectSUrl = function(urlID, urlAddress) {
		
		//data object == backend DTO
		var dataObj = {
			urlID : urlID,
        	userID: userService.getUserID(),
        	username: userService.getUsername(),
        	urlAddress: urlAddress
		}

		var res = $http.post('/redirectSUrl', dataObj);

		res["Content-Type"] = "application/json; charset=utf-8";

		res.success(function(data, status, headers, config) {
			urlService.setUrlId(data.urlID);
			urlService.setLongUrl(data.urlAddress);
   		 	$location.path('/shortenurl/' + data.accesstoken);
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message1: ");
		});
	

	}
	
});