angular.module("mainModule")
    .controller('PaymentBlock',
        function($rootScope, $scope, currencyRatesService, security, warning, $http, $mdToast, gettextCatalog, cheque, $state, $timeout) {
            $scope.hasPrepayment = false;
            $scope.balance = {payments: []};

            $scope.security = security;

            $scope.hasAtLeastOnePayment = function() {
                return $scope.balance.payments.length > 0;
            };

            $scope.synchronizeBalance = function() {
                cheque.syncChequeBalanceWithServer($scope.chequeID, $scope.balance)
                    .then(function(success) {
                        if(success) {
                            $scope.paymentForm.$setPristine();
                            $scope.paymentForm.$setUntouched();
                        }
                    });
            };

            $scope.addPayment = function() {
                if($scope.balance.payments === undefined)
                    $scope.balance.payments = [];

                currencyRatesService.refreshCurrencyRate();
                $scope.balance.payments.push(
                    {cost: 0, type: 'repair',
                        currency: 'rub',
                        exchangeRate: $rootScope.currencyRates,
                        user: $rootScope.user.principal});
            };

            $scope.delPayment = function(payment, event) {
                warning.showConfirmDeletePayment(event).then(function() {
                    $scope.balance.payments.splice($scope.balance.payments.indexOf(payment), 1);
                    $scope.paymentForm.$setDirty();
                }, function() {});
            };

            $scope.sum = function(currency, withPrepayment) {
                var sum = 0;
                $scope.hasPrepayment = false;

                if($scope.balance.payments != undefined)
                    $scope.balance.payments.forEach(function(item) {
                        if(item.cost != undefined && item.currency != undefined) {
                            if(item.type != 'prepayment')
                                sum += item.cost * item.exchangeRate[item.currency] / item.exchangeRate[currency];
                            else {
                                $scope.hasPrepayment = true;
                                if(withPrepayment)
                                    sum -= item.cost * item.exchangeRate[item.currency] / item.exchangeRate[currency];
                            }
                        }
                    });

                return sum;
            };

            $rootScope.$watch('balance', function (newValue, oldValue) {
                if(newValue !== undefined) {
                    $scope.balance = newValue;
                }

                $timeout(function() {
                    if($scope.paymentForm != null) {
                        $scope.paymentForm.$setPristine();
                        $scope.paymentForm.$setUntouched();
                    }
                }, 500);
            });

            $timeout(function() {
                $scope.$watch('paymentForm.$dirty', function (newValue, oldValue) {
                    $scope.$parent.formDirty.payment = newValue;
                });
            }, 1000);

        }
    )
    .directive('paymentBlock', [function() {
        return {
            restrict: 'E',
            controller: 'PaymentBlock',
            scope: {
                chequeID: '=chequeId'
            },
            require: 'chequeId',
            templateUrl: 'app/cheque-page/payment-block/payment-block.html'
        }
    }]);