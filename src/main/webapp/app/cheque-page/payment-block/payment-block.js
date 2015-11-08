angular.module("mainModule")
    .controller('PaymentBlock', ['$scope', 'currencyRatesService', '$rootScope', 'security', 'warning', '$http',
        function($scope, currencyRatesService, $rootScope, security, warning, $http) {
            $scope.hasPrepayment = false;

            $scope.getPayments = function(chequeId) {
                $http.get('/api/payment/cheque/' + chequeId)
                    .success(function(data) {
                        $scope.payments = data;
                    });
            };

            $scope.addPayment = function() {

                if($scope.payments === undefined)
                    $scope.payments = [];

                currencyRatesService.refreshCurrencyRate();
                $scope.payments.push(
                    {cost: 0,
                        currentCurrency: 'rub',
                        currency: $rootScope.currencyRates,
                        user: $rootScope.user.principal});
            };

            $scope.delPayment = function(payment, event) {
                warning.showConfirmDeletePayment(event).then(function() {
                    $scope.payments.splice($scope.payments.indexOf(payment), 1);
                }, function() {});
            };

            $scope.sum = function(currency, withPrepayment) {
                var sum = 0;
                $scope.hasPrepayment = false;

                if($scope.payments != undefined)
                    $scope.payments.forEach(function(item) {
                        if(item.cost != undefined && item.currentCurrency != undefined) {
                            if(item.type != 'prepayment')
                                sum += item.cost * item.currency[item.currentCurrency] / item.currency[currency];
                            else {
                                $scope.hasPrepayment = true;
                                if(withPrepayment)
                                    sum -= item.cost * item.currency[item.currentCurrency] / item.currency[currency];
                            }
                        }
                    });

                return sum;
            };

            $scope.security = security;

            $scope.$watch('cheque.id', function (newValue, oldValue) {
                if(newValue !== undefined)
                    $scope.getPayments(newValue);
            });

        }
    ])
    .directive('paymentBlock', [function() {
        return {
            restrict: 'E',
            controller: 'PaymentBlock',
            scope: {
                cheque: '=ngModel'
            },
            require: 'ngModel',
            templateUrl: 'app/cheque-page/payment-block/payment-block.html'
        }
    }]);