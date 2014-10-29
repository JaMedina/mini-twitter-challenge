var miniTwitterApp = angular.module('miniTwitterApp', ['ngRoute','ngCookies']);

miniTwitterApp.config(['$routeProvider', '$httpProvider', 
                        function ($routeProvider, $httpProvider) {
	$httpProvider.defaults.headers.commons = { 'Content-Type' : 'application/json' };
    $routeProvider
        .when('/timeline', {
            templateUrl: 'views/timeline.html',
            controller: 'TimeLineController'
        })
        .otherwise({
            templateUrl: 'views/main.html',
            controller: 'MainController'
        });
}]);

miniTwitterApp.factory('Session', [
   function () {
       this.create = function (token, username, id) {
           this.token = token;
           this.username = username;
           this.id = id;
       };
       this.invalidate = function () {
           this.token = null;
           this.usernmae = null;
           this.id = null;
       };
       return this;
   }]);

miniTwitterApp.factory('RequestProvider', ['$http', '$cookieStore',
	function($http, $cookieStore){
		return{
			execute: function(method, url, params){
				var session = $cookieStore.get('session');
				if(!angular.isUndefined(session)){
					params.token = session.token;
					params.username = session.username;
				}
				var promise = $http({
					url: url,
					method: method,
					params: params
				});
				return promise
			}
		}
	}]);