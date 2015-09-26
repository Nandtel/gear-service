angular.module("mainModule")
    .controller('PaymentLine', ['$scope',
        function($scope) {
            $scope.hasPrepayment = false;

            $scope.types = [ "repair" , "zip", "deliver", "prepayment"];
            $scope.masters = ['kosoy', 'valikozz'];
            $scope.currencies = ['eur', 'uah', 'usd', 'rub'];


            $scope.$watch('payment.type', function(newValue, oldValue) {
                $scope.hasPrepayment = newValue === 'prepayment';
            });

            //$scope.currencies = function() {
            //    return Object.keys($scope.payment.currencyRates);
            //};

            $scope.toPay = function(currency) {
                return $scope.payment.cost *
                    $scope.payment[$scope.payment.currency] / $scope.payment[currency];
            };

        }
    ])
    .directive('paymentLine', [function() {
        return {
            restrict: 'E',
            controller: 'PaymentLine',
            scope: {
                payment: '=payment',
                paid: '=paid'
            },
            require: ['ngModel', 'paid'],
            templateUrl: 'app/cheque-page/payment-block/payment-line/payment-line.html'
        }
    }]);