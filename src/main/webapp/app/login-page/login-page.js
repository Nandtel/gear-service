angular.module("mainModule")
    .controller("LoginPage", ['$scope', '$rootScope', 'auth',
        function($scope, $rootScope, auth) {
            $scope.credentials = {};

            $scope.authenticated = function() {
                return auth.authenticated;
            };

            $scope.login = function() {
                auth.authenticate($scope.credentials, function(authenticated) {
                    if (authenticated) {
                        console.log("Login succeeded");
                        $scope.error = false;
                    } else {
                        console.log("Login failed");
                        $scope.error = true;
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
