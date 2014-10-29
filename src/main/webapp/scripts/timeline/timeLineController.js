miniTwitterApp.controller('TimeLineController', ['$scope', '$location', '$cookieStore', 'Session', 'RequestProvider', 
    function($scope, $location, $cookieStore, Session, RequestProvider) {
		$scope.session = $cookieStore.get('session');
		$scope.logout = function(){
			RequestProvider.execute('POST', 'rest/users/logout', {})
		    .success(function (data, status, headers, config) {
		    	Session.invalidate();
		    	$cookieStore.remove('session');
		    	$location.path('/').replace();
		    }).error(function (data, status, headers, config) {
		        alert(data.message);
		        if(status == 401){
	        		$location.path('/').replace();
	        	}
		    });
		}
				
		if(angular.isUndefined($scope.session)) {
			$location.path('/').replace();
		}else{
			Session.create($scope.session.token, $scope.session.username, $scope.session.id);
			$scope.search = '';
		}

		$scope.sendTweet = function(){
			RequestProvider.execute('POST', 'rest/tweets', {authorId: Session.id, message: $scope.tweet.message})
			.success(function (data, status, headers, config) {
            	$scope.refreshTweets();
				$scope.tweet.id = null;
				$scope.tweet.message = null;
            }).error(function (data, status) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	}
	        });
		}
		
		$scope.follow = function(followee){
			RequestProvider.execute('POST', 'rest/follow', {followeeUsername: followee.username})
			.success(function () {
	        	$scope.refreshFollowees();
	        	$scope.refreshTweets();
	        }).error(function (data, status) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	}
	        });
		}
		
		$scope.unFollow = function(followee){
			RequestProvider.execute('DELETE', 'rest/follow',{username: Session.username, followeeUsername: followee.username})
	        .success(function () {
	        	$scope.refreshFollowees();
	        	$scope.refreshTweets();
	        }).error(function (data, status) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	}
	        });
		}
		
		$scope.refreshFollowees = function(){
			RequestProvider.execute('GET', 'rest/followee', {username: Session.username})
			.success(function (data) {
				$scope.following = data.users;
	        }).error(function (data) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	}
	        });
		}
		$scope.refreshFollowers = function(){
			RequestProvider.execute('GET', 'rest/follow', {username: Session.username})
			.success(function (data) {
				$scope.followers  = data.users;
	        }).error(function (data, status) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	}
	        });
		}
		
		$scope.refreshTweets = function(){
			RequestProvider.execute('GET', 'rest/tweets', {filter: $scope.search})
			.success(function (data) {
				$scope.tweets  = data.tweets;
	        }).error(function (data, status) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	};
	        });
		}
		
		$scope.refreshUsers = function(){
			RequestProvider.execute('GET', 'rest/users', {})
			.success(function (data) {
				$scope.users = data.users;
	        }).error(function (data, status) {
	        	alert(data.message);
	        	if(status == 401){
	        		$location.path('/').replace();
	        	}
	        });
		}
		
		$scope.refreshTweets();
		$scope.refreshFollowers();
		$scope.refreshFollowees();
		$scope.refreshUsers();
    }
]);