angular.module("mainModule")
    .controller('TableData', ['$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog',
        function ($scope, $http, $timeout, $mdDialog, gettextCatalog)  {
        $scope.label = {
            text: gettextCatalog.getString('Rows per page:'),
            of: gettextCatalog.getString('of')
        };

        /**
         * Method getAllCheques request from server-side all cheques for display to the user
         * Timeout for smoothly display
         * It adds to cheque model all cheques, when gets them
         */
        $scope.getAllCheques = function() {
            $http.get('/api/cheques').success(function(response) {
                $scope.cheques = response;
            });
        };

        /**
         * Method necessary for md-data-table
         */
        $scope.onPageChange = function() {};

        /**
         * Method necessary for md-data-table
         */
        $scope.onOrderChange = function() {};

        //$scope.getAllCheques();
    }])
    .directive('tableData', function() {
        return {
            restrict: 'E',
            controller: 'TableData',
            scope: {
                filter: '=filter',
                cheques: '=cheques'
            },
            require: ['filter', 'cheques'],
            templateUrl: 'app/filters-page/table-data/table-data.html'
        }
    });