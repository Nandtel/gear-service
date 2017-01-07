angular.module("mainModule")
    .controller("LoginPage", function($scope, $rootScope, auth, $mdToast, vcRecaptchaService, autocomplete) {
            $scope.credentials = {};
            $scope.response = null;
            $scope.widgetId = null;
            $scope.model = {key: '6Le_FBETAAAAABTCTvoZWEEIqb1lxqNpNAtRlGWk'};

            $scope.authenticated = function() {
                return auth.authenticated;
            };

            $scope.login = function() {
                auth.authenticate($scope.credentials, $scope.response)
                    .then(function(authenticated) {
                        if (authenticated) {
                            $mdToast.show(
                                $mdToast.simple()
                                    .content("Login succeeded")
                                    .position('top right')
                                    .hideDelay(700)
                            );
                        } else {
                            $mdToast.show(
                                $mdToast.simple()
                                    .content("Login failed")
                                    .position('top right')
                                    .hideDelay(700)
                            );
                            vcRecaptchaService.reload($scope.widgetId);
                        }
                })
            };
            $scope.logout = auth.logout;

            $scope.hasAccess = function(role) {
                var boolean = false;
                angular.forEach($rootScope.user.authorities, function(i) {
                    if(i.authority === role) {
                        boolean = true;
                    }
                });
                return boolean && $scope.authenticated();
            };

            $scope.setResponse = function (response) {
                $scope.response = response;
            };
            $scope.setWidgetId = function (widgetId) {
                $scope.widgetId = widgetId;
            };
            $scope.cbExpiration = function() {
                $scope.response = null;
            };

        })
    .directive('loginPage', function() {
        return {
            restrict: 'E',
            controller: 'LoginPage',
            templateUrl: 'app/login-page/login-page.html'
        }
    });
