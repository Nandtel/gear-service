angular.module("mainModule")
    .controller('PaymentBlock', ['$scope',
        function($scope) {

            $scope.addPayment = function() {
                if($scope.cheque.payments === undefined)
                    $scope.cheque.payments = [];

                $scope.cheque.payments.push({description: undefined, cost: 0, currency: 'RUB'});
            };

            $scope.currencies = function() {
                return Object.keys($scope.rates);
            };

            $scope.delPayment = function(payment) {
                $scope.cheque.payments.splice($scope.cheque.payments.indexOf(payment), 1);
            };

            $scope.sumRUB = function() {
                var sum = 0;
                if($scope.cheque.payments != undefined)
                    $scope.cheque.payments.forEach(function(item) {
                        if(item.cost != undefined && item.currency != undefined)
                            sum += item.cost * $scope.rates[item.currency];
                    });
                return sum;
            };


            $scope.types = [ "Repair" , "ZIP", "Deliver"];
            $scope.masters = ['Kosoy', 'Valikozz'];

        }
    ])
    .directive('paymentBlock', [function() {
        return {
            restrict: 'E',
            controller: 'PaymentBlock',
            scope: {
                cheque: '=ngModel',
                rates: '=rates'
            },
            require: 'ngModel',
            templateUrl: 'app/cheque-page/payment-block/payment-block.html'
        }
    }]);