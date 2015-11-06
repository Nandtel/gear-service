angular.module("mainModule")
    .controller('AnalyticsPage', ['$scope', '$http',
        function ($scope, $http) {

            $scope.columns = ['engineers', 'brands'];
            $scope.rows = ['date', 'cheques'];

            $scope.preferences = {};

            $scope.getAnalytics = function() {
                $http.post('/api/analytics', $scope.preferences)
                    .success(function(data) {
                        $scope.analytics = data;
                    });
            };
        }
    ])
    .directive('analyticsPage', function() {
        return {
            restrict: 'E',
            controller: 'AnalyticsPage',
            templateUrl: 'app/analytics-page/analytics-page.html'
        }
    });