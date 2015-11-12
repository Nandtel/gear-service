angular.module("mainModule")
    .controller('PaymentLine', ['$scope', '$http', 'security',
        function($scope, $http, security) {

            $scope.$watch('payment.type', function(newValue, oldValue) {
                $scope.hasPrepayment = newValue === 'prepayment';
            });

            $scope.$watch('paid', function(newValue, oldValue) {
                $scope.paymentLineDisabled =
                    newValue
                    || !$scope.security.hasAnyRole(['ROLE_ADMIN', 'ROLE_BOSS'])
                    && !$scope.security.isSameUser($scope.payment.user);
            });

            $scope.toPay = function(currency) {
                return $scope.payment.cost *
                    $scope.payment.exchangeRate[$scope.payment.currency] / $scope.payment.exchangeRate[currency];
            };

            $scope.loadUsers = function() {
                $http.get('/api/users')
                    .success(function(data) {
                        $scope.users = data;
                    });
            };

            $scope.hasPrepayment = false;
            $scope.types = [ 'repair' , 'zip', 'deliver', 'prepayment'];
            $scope.currencies = ['eur', 'uah', 'usd', 'rub'];
            $scope.security = security;

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