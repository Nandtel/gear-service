angular.module("mainModule")
    .controller('ProfilePage', ['$scope', '$http', 'currencyRatesService',
        function ($scope, $http, currencyRatesService) {

            currencyRatesService.getListOfCurrencyRates()
                .then(function(data) {
                    $scope.rates = data;
                });

            $scope.setRates = function() {
                currencyRatesService.setListOfCurrencyRates($scope.rates);
            };

            $scope.getUsers = function() {
                $http.get('/api/users')
                    .success(function(data) {
                        $scope.users = data;
                    });
            };

            $scope.getUsers();

        }
    ])
    .directive('profilePage', function() {
        return {
            restrict: 'E',
            controller: 'ProfilePage',
            templateUrl: 'app/profile-page/profile-page.html'
        }
    });