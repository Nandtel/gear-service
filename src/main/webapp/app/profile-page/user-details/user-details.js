angular.module("mainModule")
    .controller('UserDetails', ['$scope', '$http', 'currencyRatesService',
        function ($scope, $http, currencyRatesService) {

            $scope.saveUser = function() {
                $http.post('/api/user', $scope.user)
                    .success(function() {
                        $scope.$parent.getUsers();
                    });
            };

            $scope.deleteUser = function() {
                $http.delete('/api/user/' + $scope.user.username)
                    .success(function() {
                        $scope.$parent.getUsers();
                    });
            };

        }
    ])
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