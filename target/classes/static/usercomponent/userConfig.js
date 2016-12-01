/* Configuration of RouteProvider*/
mainApp.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'usercomponent/login.html',
		controller : 'loginController'
	}).when('/createuser', {
		templateUrl : 'usercomponent/createuser.html',
		controller : 'userController'
	}).when('/login', {
		templateUrl : 'usercomponent/login.html',
		controller : 'loginController'
	}).when('/urlhome/:accesstoken', {
		templateUrl : 'urlComponent/urlhome.html',
		controller : 'urlHomeController'
	}).when('/shortenurl/:accesstoken', {
		templateUrl : 'urlComponent/shortenurl.html',
		controller : 'shortenController'
	}).when('/userProfile/:accesstoken', {
		templateUrl : 'urlComponent/userProfile.html',
		controller : 'enquiryController'
	})
	;
});
