/**
 * This file contains factory auth to handle authentication methods.
 *
 * @version 1.0
 * @author Dmitry
 * @since 08.02.2017
 */
angular.module('mainModule')
	.factory('auth', function($rootScope, $http, $state, $q) {

			$rootScope.user = {};

			var auth = {

				authenticated : false,
				loginState: 'cheque.login',
				logoutPath: '/logout',
				desireState: 'cheque.filter',
				desireParams: {},

				init: function() {

					auth.authenticate({}, {})
							.then(function(authenticated) {
								if(!authenticated)
									$state.go(auth.loginState);
							});

					$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

						if(toState.name === auth.loginState || auth.authenticated)
							return;

						auth.desireState = toState.name;
						auth.desireParams = toParams;

						event.preventDefault();
					});
				},

				authenticate: function(credentials, recaptcha) {
					var deferred = $q.defer();
					var headers = credentials && credentials.username ?
					{Authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)} : {};
					headers.ReCaptcha = recaptcha;

					$http.get('/api/user', {headers: headers, params: {"remember-me": true}})
						.success(function(data) {
							$rootScope.user = data;
							auth.authenticated = !!data.name;
							$state.go(auth.desireState, auth.desireParams);
							deferred.resolve(auth.authenticated);
						})
						.error(function() {
							deferred.resolve(false);
							auth.authenticated = false;
						});

					return deferred.promise;
				},

				logout: function() {
					$http.post(auth.logoutPath, {});
					auth.authenticated = false;
					$rootScope.user = {};
					$state.go(auth.loginState);
				}
			};

		return auth;
	});