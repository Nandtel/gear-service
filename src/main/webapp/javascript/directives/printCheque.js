app.directive('printCheque', function() {
    return {
        restrict: 'E',
        controller: ['$scope', function($scope) {

            var specializations = {
                office: {office: true, tech: false, client: false},
                tech: {office: false, tech: true, client: false},
                client: {office: false, tech: false, client: true}
            };

            $scope.spec = specializations[$scope.specialization];

            $scope.getKits = function() {
                var array = [];
                $scope.cheque.kits.forEach(function(item) {
                    array.push(item.text);
                });
                return array.join(', ');
            };

        }],
        scope: {
            cheque: '=ngModel',
            specialization: '=specialization'
        },
        require: ['ngModel', 'specialization'],
        templateUrl: '/print'
    }
});