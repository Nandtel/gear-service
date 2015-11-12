angular.module("mainModule")
    .directive('printCheque', function() {
        return {
            restrict: 'E',
            controller: ['$scope', function($scope) {

                var specializations = {
                    office: {office: true, tech: false, client: false},
                    tech: {office: false, tech: true, client: false},
                    client: {office: false, tech: false, client: true}
                };

                $scope.spec = specializations[$scope.specialization];

                $scope.getComponents = function() {
                    var array = [];
                    $scope.cheque.components.forEach(function(item) {
                        array.push(item.name);
                    });
                    return array.join(', ');
                };

            }],
            scope: {
                cheque: '=ngModel',
                specialization: '=specialization'
            },
            require: ['ngModel', 'specialization'],
            templateUrl: 'app/cheque-page/print-cheque/print-cheque.html'
        }
    });