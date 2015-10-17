angular.module("mainModule")
    .controller('AnalyticsPage', ['$scope', '$http',
        function ($scope, $http) {

        }
    ])
    .directive('analyticsPage', function() {
        return {
            restrict: 'E',
            controller: 'AnalyticsPage',
            templateUrl: 'app/analytics-page/analytics-page.html'
        }
    });