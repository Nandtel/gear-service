angular.module("mainModule")
    .controller("LoginPage", ['$scope', '$rootScope', 'auth', '$mdToast',
        function($scope, $rootScope, auth, $mdToast) {
            $scope.credentials = {};

            $scope.authenticated = function() {
                return auth.authenticated;
            };

            $scope.login = function() {
                auth.authenticate($scope.credentials, function(authenticated) {
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
            }
        }])
    .directive('loginPage', function() {
        return {
            restrict: 'E',
            controller: 'LoginPage',
            templateUrl: 'app/login-page/login-page.html'
        }
    });
