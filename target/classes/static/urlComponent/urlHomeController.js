mainApp.controller('urlHomeController', function($scope, $location, $http,
		urlService, userService) {
	

	$scope.disableButton = function() {
		$scope.isDisabled = true;
	}
	$scope.errorMessage;
	
	$http.get('urlhome', {
        params: {
        	userID: userService.getUserID(),
        	username: userService.getUsername(),
            accesstoken: $location.path().replace("/urlhome/","")
        }
     })
     .success(function (data,status) {
    	 if(data.result.result) {
    		 $location.path('/urlhome/'+userService.getAccesstoken());
         } else {
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

	$scope.shortenURL = function(url) {
		$scope.isDisabled = true;
		var dataObj = {
			userID: userService.getUserID(),
        	username: userService.getUsername(),
			urlAddress : url.text
		}
		
		if(url.text.length > 200){
			$scope.errorMessage = 'Exceed to Long url length';
		}
		var res = $http.post('/urlhome', dataObj);
		res["Content-Type"] = "application/json; charset=utf-8";
		
		res.success(function(data, status, headers, config) {
			$scope.isDisabled = false;
			$scope.result = data;
			
			if ($scope.result.result.result) {
				
				$location.path('/shortenurl/'+$scope.result.accesstoken);
				
				urlService.setUrlId($scope.result.urlID);
				urlService.setUserID($scope.result.userID);
				urlService.setUsername($scope.result.username);
				urlService.setLongUrl($scope.result.urlAddress);
				urlService.setAccesstoken($scope.result.accesstoken);
				
				$scope.isDisabled = false;
			} else {
				$scope.errorMessage = 'url is invalid, give another long url';
			}
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data : data
			}));
		});
	}
})