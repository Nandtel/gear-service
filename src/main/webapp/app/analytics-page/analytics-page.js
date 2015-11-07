angular.module("mainModule")
    .controller('AnalyticsPage', ['$scope', '$http',
        function ($scope, $http) {

            $scope.columns = ['engineers', 'brands'];
            $scope.rows = ['date', 'cheques'];
            $scope.funds = ['income', 'profit'];

            $scope.preferences = {};
            $scope.analyticsHeader = [];
            var analyticsHeaderSet = {};

            $scope.getAnalytics = function() {
                $http.post('/api/analytics', $scope.preferences)
                    .success(function(data) {
                        $scope.analytics = data;
                        angular.forEach(data, function(item) {
                            angular.forEach(Object.keys(item), function(item) {
                                analyticsHeaderSet[item] = "";
                            });
                        });
                        $scope.analyticsHeader = Object.keys(analyticsHeaderSet);
                        analyticsHeaderSet = {};
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