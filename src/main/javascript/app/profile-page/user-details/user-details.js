angular.module("mainModule")
    .controller('UserDetails', function ($scope, $http, currencyRatesService) {

            $scope.saveUser = function() {
                $http.post('/api/user', $scope.user)
                    .success(function() {
                        $scope.$parent.getUsers();
                    });
            };

            $scope.addAuthority = function(authority) {
                return {'authority': authority};
            };

        }
    )
    .directive('userDetails', function() {
        return {
            restrict: 'E',
            controller: 'UserDetails',
            scope: {
                user: '=ngModel'
            },
            require: 'ngModel',
            templateUrl: 'app/profile-page/user-details/user-details.html'
        }
    });