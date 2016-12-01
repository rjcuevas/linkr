mainApp.controller('loginController', function($scope, $http, userService, $location){
	$scope.resultMessage = userService.getMessage();
	
	$http.get('login', {
        params: {
        	userID: userService.getUserID(),
            accesstoken: userService.getAccesstoken()
        }
     })
     .success(function (data,status) {
          if(data) {
        	  $location.path('/urlhome/'+userService.getAccesstoken());
          } else {
        	  $location.path('/');
          }
     })
     .error(function(data, status) {
			console.log( "failure message: ");
	 });
	
	//Login
	$scope.login = function(user){
		var dataObj = {
				username: user.username,
				password: user.password
		}
		
		var res = $http.post('/login', dataObj);
		
		res["Content-Type"] = "application/json; charset=utf-8";
		res.success(function(data, status, headers, config) {
			$scope.result = data;
			
			if($scope.result.result.result) {
				$location.path('/urlhome/'+$scope.result.accesstoken);
				userService.setMessage($scope.result.result.resultMessage);
				userService.setUserID($scope.result.userID);
				userService.setUsername($scope.result.username);
				userService.setAccesstoken($scope.result.accesstoken);
			}else{
				//reset login fields & set login error message
				user.username = '';
				user.password = '';
				$scope.resultMessage = $scope.result.result.resultMessage;
			}
		});
		
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + JSON.stringify({data: data}));
		});	
	}
	
	// Reset login fields on cancel
	$scope.reset = function () {
		$scope.user = '';
	}
	   
	
})

