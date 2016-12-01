mainApp.controller('userController', function($scope, $http, $location, userService){
	$scope.emailpattern = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
	$scope.createuser = function(user){
		var dataObj = {first_name: user.first_name,
				       last_name: user.last_name,
				       email: user.email,
					   username: user.username,
					   password: user.password}
		var res = $http.post('/createUser', dataObj);
		res["Content-Type"] = "application/json; charset=utf-8";
		res.success(function(data, status, headers, config) {
			$scope.result = data;
			if($scope.result.result.result) {
				$location.path('/');
				userService.setMessage($scope.result.result.resultMessage);
			}
		});
		res.error(function(data, status, headers, config) {
			console.log( "failure message: " + JSON.stringify({data: data}));
		});	
	};
	// Reset login fields on cancel
	$scope.reset = function () {
		$scope.user = '';
		$location.path('/');
	};
})