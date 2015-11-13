angular.module("mainModule")
    .controller('PaymentBlock', ['$scope', 'currencyRatesService', '$rootScope', 'security', 'warning', '$http', '$mdToast', 'gettextCatalog',
        function($scope, currencyRatesService, $rootScope, security, warning, $http, $mdToast, gettextCatalog) {
            var chequeID;
            $scope.hasPrepayment = false;

                $scope.getPayments = function(chequeId) {
                $http.get('/api/payment/cheque/' + chequeId)
                    .success(function(data) {
                        $scope.payments = data;
                    });
            };

            $scope.synchronizePayments = function() {
                $http.post('/api/payment/cheque/' + chequeID, $scope.payments)
                    .success(function(payments) {
                        $scope.payments = payments;

                        $mdToast.show(
                            $mdToast.simple()
                                .content(gettextCatalog.getString('payments synchronized'))
                                .position('top right')
                                .hideDelay(700)
                        );
                    });
            };

            $scope.addPayment = function() {

                if($scope.payments === undefined)
                    $scope.payments = [];

                currencyRatesService.refreshCurrencyRate();
                $scope.payments.push(
                    {cost: 0,
                        currency: 'rub',
                        exchangeRate: $rootScope.currencyRates,
                        user: $rootScope.user.principal});
            };

            $scope.delPayment = function(paymentID, event) {
                warning.showConfirmDeletePayment(event).then(function() {
                    $http.delete('/api/payment/' + paymentID + '/cheque')
                        .success(function() {
                            $scope.getPayments(chequeID)
                        });
                }, function() {});
            };

            $scope.sum = function(currency, withPrepayment) {
                var sum = 0;
                $scope.hasPrepayment = false;

                if($scope.payments != undefined)
                    $scope.payments.forEach(function(item) {
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

            $scope.security = security;

            $scope.$watch('cheque.id', function (newValue, oldValue) {
                if(newValue !== undefined) {
                    chequeID = newValue;
                    $scope.getPayments(chequeID);
                }
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