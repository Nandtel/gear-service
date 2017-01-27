angular.module("mainModule")
    .controller('ProfilePage', function ($scope, $http, currencyRatesService) {
            var sampleUser =
            {username: 'Name', fullname: 'FullName', enabled: true, authorities: [{authority:'ROLE_ENGINEER'}]};

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

            $scope.addUser = function() {
                $scope.users.push(sampleUser);
            };

            $scope.getUsers();
        }
    )
    .directive('profilePage', function() {
        return {
            restrict: 'E',
            controller: 'ProfilePage',
            templateUrl: 'app/profile-page/profile-page.html'
        }
    });