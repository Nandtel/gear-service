angular.module("mainModule")
    .controller('PaymentLine', ['$scope', '$http', 'security',
        function($scope, $http, security) {

            $scope.$watch('payment.type', function(newValue, oldValue) {
                $scope.hasPrepayment = newValue === 'prepayment';
            });

            $scope.toPay = function(currency) {
                return $scope.payment.cost *
                    $scope.payment.currency[$scope.payment.currentCurrency] / $scope.payment.currency[currency];
            };

            $scope.loadUsers = function() {
                $http.get('/api/users')
                    .success(function(data) {
                        $scope.users = data;
                    });
            };

            $scope.hasPrepayment = false;
            $scope.types = [ "repair" , "zip", "deliver", "prepayment"];
            $scope.currencies = ['eur', 'uah', 'usd', 'rub'];
            $scope.security = security;
            $scope.paymentLineDisabled =
                $scope.paid
                || !$scope.security.hasAnyRole(['ROLE_ADMIN', 'ROLE_BOSS'])
                && !$scope.security.isSameUser($scope.payment.user);

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
            templateUrl: 'app/cheque-page/cheque-form/payment-block/payment-line/payment-line.html'
        }
    }]);