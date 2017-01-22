angular.module("mainModule")
    .controller('CurrencyRateDay', function ($scope, $http) {

            $scope.sendRate = function() {
                $http.post('/api/currency-rate', $scope.rate)
                    .success(function() {
                        $scope.day.$setPristine();
                    })
            };

        }
    )
    .directive('currencyRateDay', function() {
        return {
            restrict: 'E',
            controller: 'CurrencyRateDay',
            scope: {
                rate: '=ngModel'
            },
            require: 'ngModel',
            templateUrl: 'app/profile-page/currency-rate-day/currency-rate-day.html'
        }
    });