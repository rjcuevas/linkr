mainApp.controller('headerController', function($scope, $location, $http,
		urlService, userService) {
	if(userService.getAccesstoken() == undefined){
		$location.path('/');
	}
	
	$scope.gotoUserProfile = function(){
		if(userService.getAccesstoken() != undefined){
			$location.path('/userProfile/'+userService.getAccesstoken());
		}
	}
	
	$scope.gotoIndex = function(){
		if(userService.getAccesstoken() != undefined){
			$location.path('/urlhome/'+userService.getAccesstoken());
		}
	}
	
	$scope.gotoLogout = function(){
		userService.clearUserService();
		urlService.clearUrlService();
		$location.path('/');
	}
});