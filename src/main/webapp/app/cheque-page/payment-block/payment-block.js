angular.module("mainModule")
    .controller('PaymentBlock', ['$rootScope', '$scope', 'currencyRatesService', 'security', 'warning', '$http', '$mdToast', 'gettextCatalog', 'chequeService',
        function($rootScope, $scope, currencyRatesService, security, warning, $http, $mdToast, gettextCatalog, chequeService) {
            var chequeID;
            $scope.hasPrepayment = false;
            $scope.balance = {payments: []};

            $scope.security = security;

            $scope.synchronizeBalance = function() {
                chequeService.syncChequeBalanceWithServer(chequeID, $scope.balance);
            };

            $scope.addPayment = function() {
                if($scope.balance.payments === undefined)
                    $scope.balance.payments = [];

                currencyRatesService.refreshCurrencyRate();
                $scope.balance.payments.push(
                    {cost: 0,
                        currency: 'rub',
                        exchangeRate: $rootScope.currencyRates,
                        user: $rootScope.user.principal});
            };

            $scope.delPayment = function(payment, event) {
                warning.showConfirmDeletePayment(event).then(function() {
                    $scope.balance.payments.splice($scope.balance.payments.indexOf(payment), 1);
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

            $scope.$watch('chequeID', function (newValue, oldValue) {
                if(newValue !== undefined) {
                    chequeID = newValue;
                    chequeService.getChequeBalanceFromServer(chequeID);
                }
            });

            $rootScope.$watch('balance', function (newValue, oldValue) {
                if(newValue !== undefined) {
                    $scope.balance = newValue;
                }
            });

        }
    ])
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