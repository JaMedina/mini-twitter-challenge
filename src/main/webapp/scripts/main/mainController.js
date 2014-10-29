miniTwitterApp.controller('MainController', ['$scope', '$location', '$cookieStore', 'Session', 'RequestProvider',
    function($scope, $location, $cookieStore, Session, RequestProvider) {
		$scope.login = function () {
			$scope.session = $cookieStore.get('session');
			if(!angular.isUndefined($scope.session)) {
				RequestProvider.execute('POST', 'rest/users/logout', {})
				.success(function (data, status, headers, config) {
			    	Session.invalidate();
			    	$cookieStore.remove('session');
			    	RequestProvider.execute('POST', 'public/login', {username: $scope.username})
					.success(function (data, status, headers, config) {
		            	Session.create(data.token, data.user.username, data.user.id);
		            	$cookieStore.put('session', Session);
		            	$location.path('timeline').replace();
		            }).error(function (data, status, headers, config) {
		                alert(data.message);
		            });
			    })
			}else{
				RequestProvider.execute('POST', 'public/login', {username: $scope.username})
				.success(function (data, status, headers, config) {
	            	Session.create(data.token, data.user.username, data.user.id);
	            	$cookieStore.put('session', Session);
	            	$location.path('timeline').replace();
	            }).error(function (data, status, headers, config) {
	                alert(data.message);
	            });
			}
			
			
	    }
		
		$scope.create = function () {
			RequestProvider.execute('POST', 'public/user', {name: $scope.newUser.name, username: $scope.newUser.username})
			.success(function (data, status, headers, config) {
            	alert('Your user was created. Now you can login.')
            	$scope.clear();
            }).error(function (data, status, headers, config) {
                alert(data.message);
            });
	    }
		
		$scope.clear = function () {
          $scope.newUser = {id: null, name: null, username: null};
		};
	}
]);



