mainApp.controller('shortenController', function($scope, $http, urlService, $location) {
	
	$scope.longlocalUrl = urlService.getLongUrl();
	$scope.googleVal = 'default';
	
	$scope.isDisabled1 = true; // since no regeneration will occur
	$scope.isDisabled2 = true;  // since no regeneration will occur
	$scope.isDisabled3 = false;
	
	
	//$scope.disableClass = 'disabledButton';
	$scope.regularClass = 'shortenButton';
	$scope.regularClass2 = 'shortenButton';
	$scope.regularClass3 = 'shortenButton';
	
	$scope.disableButton1 = function() {
		$scope.isDisabled1 = true;
		if($scope.regularClass === 'shortenButton'){
			$scope.disableClass = 'disabledButton';
		}else{
			$scope.regularClass = 'shortenButton';
		}
	}
	
	$scope.disableButton3 = function() {
		$scope.isDisabled3 = true;
		if($scope.regularClass3 === 'shortenButton'){
			$scope.disableClass3 = 'disabledButton';
		}else{
			$scope.regularClass3 = 'shortenButton';
		}
	}
	
	$scope.disableButton2 = function() {
		$scope.isDisabled2 = true;
	}
	
	$scope.disableDelete = function() {
		$scope.isDeleteDisable = true;
	}
	
	var dataObj = {
		urlID : urlService.getUrlId(),
		longUrl : urlService.getLongUrl()
	}
	
	$http.get('shortenurl', {
		params : {
			urlID :  urlService.getUrlId(),
			accesstoken :  $location.path().replace("/shortenurl/","")
		}
	}).success(function(data, status) {
		$scope.info_show = data;
	});

	$scope.shortenGoogleURL = function() {

		var dataObj = {
			longUrl : longlocalUrl
		}

		var res = $http.post('/shortenurl', dataObj);

		res["Content-Type"] = "application/json; charset=utf-8";

		res.success(function(data, status, headers, config) {
			$scope.result = data;
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data : data
			}));
		});
	}
	
	$scope.removeURl = function(){
		$scope.isDeleteDisable = true;
		
		var dataObj = {
				urlID : urlService.getUrlId()
		}
		var res = $http.post('/deleteUrlSurl', dataObj);
		
		res["Content-Type"] = "application/json; charset=utf-8";

		res.success(function(data, status, headers, config) {
			$scope.isDeleteDisable = false;
			$scope.result = data;
			$location.path('/urlhome/' + urlService.getAccesstoken());
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data : data
			}));
		});
	}
	
	$scope.regenLkn = function(surlid) {
		
		$scope.isDisabled3 = true;
		
		var dataObj = {
			shortUrlID : surlid
		}

		var res = $http.post('/regenerate', dataObj);

		res["Content-Type"] = "application/json; charset=utf-8";

		res.success(function(data, status, headers, config) {
			$scope.result = data;
			$scope.info_show.surl3 = $scope.result.shortenURL;
			$scope.isDisabled3 = false;
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data : data
			}));
		});
	}
	
	//make shortened URL page
	$scope.makeUrlPublic = function(urlAccess) {
		console.log("isPublic: " + urlAccess);
		
		var dataObj = {
			urlID : urlService.getUrlId(),
			isPublicURL : urlAccess
		}

		var res = $http.post('/publicMode', dataObj);

		res["Content-Type"] = "application/json; charset=utf-8";

		res.success(function(data, status, headers, config) {
			$scope.result = data;
		});

		res.error(function(data, status, headers, config) {
			console.log("failure message: " + JSON.stringify({
				data : data
			}));
		});
	}
	
	$http.get('getUrlDetails', {
		params : {
			urlID :  urlService.getUrlId(),
			accesstoken :  $location.path().replace("/shortenurl/","")
		}
	}).success(function(data, status) {
		$scope.longlocalUrl = data.urlAddress;
		$scope.isPublic = data.isPublicURL;
	});
	
})