angular.module("mainModule")
    .controller('TableData', ['$rootScope', '$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog',
        function ($rootScope, $scope, $http, $timeout, $mdDialog, gettextCatalog)  {

            $scope.label = {
                text: gettextCatalog.getString('Rows per page:'),
                of: gettextCatalog.getString('of')
            };

            /**
             * Method necessary for md-data-table
             */
            $scope.onPageChange = function() {};

            /**
             * Method necessary for md-data-table
             */
            $scope.onOrderChange = function() {};

    }])
    .directive('tableData', function() {
        return {
            restrict: 'E',
            controller: 'TableData',
            templateUrl: 'app/filters-page/table-data/table-data.html'
        }
    });