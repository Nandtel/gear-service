angular.module("mainModule")
    .controller("Dashboard", ['$scope', '$stateParams', '$http',
        function ($scope, $stateParams, $http) {

            $http.get('/api/attention')
                .success(function(data) {
                    $scope.attention = data;
                });

            $http.get('/api/delay')
                .success(function(data) {
                    $scope.delay = data;
                });

        }
    ])
    .directive('dashboard', function() {
        return {
            restrict: 'E',
            controller: 'Dashboard',
            templateUrl: 'app/dashboard/dashboard.html'
        }
    });