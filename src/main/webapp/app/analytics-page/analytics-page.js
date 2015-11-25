angular.module("mainModule")
    .controller('AnalyticsPage', ['$scope', '$http', 'FileSaver', 'Blob',
        function ($scope, $http, FileSaver, Blob) {

            $scope.columns = ['engineers', 'brands'];
            $scope.rows = ['date', 'cheques'];
            $scope.funds = ['income', 'profit'];

            $scope.preferences = {};
            $scope.analyticsHeader = [];

            $scope.getAnalytics = function() {
                $http.post('/api/analytics', $scope.preferences, {responseType: 'blob'})
                    .success(function(data) {
                        var analytics = new Blob([data],
                            {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                        FileSaver.saveAs(analytics, 'analytics.xlsx');
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