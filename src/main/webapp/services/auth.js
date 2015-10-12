angular.module('mainModule')
	.factory('auth', ['$rootScope', '$http', '$location', '$state',
		function($rootScope, $http, $location, $state) {

			$rootScope.user = {};

			var auth = {

				authenticated : false,
				loginState: 'cheque.login',
				logoutPath: '/logout',
				desireState: 'cheque.filter',
				desireParams: {},

				init: function() {

					auth.authenticate({});

					$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
						if(toState.name === auth.loginState || auth.authenticated)
							return;

						auth.desireState = toState.name;
						auth.desireParams = toParams;

						event.preventDefault();
						$state.go(auth.loginState);
					});
				},

				authenticate: function(credentials, callback) {
					var headers = credentials && credentials.username ?
					{authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)} : {};

					$http.get('user', {headers: headers})
						.success(function(data) {
							$rootScope.user = data;
							auth.authenticated = !!data.name;
							$state.go(auth.desireState, auth.desireParams);
							callback && callback(auth.authenticated);})
						.error(function() {
							auth.authenticated = false;
							callback && callback(false);
						});

				},

				logout: function() {
					$http.post(auth.logoutPath, {});
					auth.authenticated = false;
					$state.go(auth.loginState);
				}
			};

		return auth;
	}]);