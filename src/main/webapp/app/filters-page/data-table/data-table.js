angular.module("mainModule")
    .controller('DataTable', ['$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog',
        function ($scope, $http, $timeout, $mdDialog, gettextCatalog) {
            $scope.filterForm = {};
            $scope.cleanFilter = {
                order: '-id', limit: 15, page: 1, nameOfCustomer: "", nameOfProduct: "", model: "", serialNumber: "",
                purchaserName: "", inspectorName: "", masterName: "", introducedFrom: undefined,
                introducedTo: undefined
            };
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
                $http.get('/cheques').success(function(response) {
                    $scope.cheques = response;
                });
            };

            /**
             * Method resetFilter add to filter necessary data for right displaying
             * It adds to filter data from cleanFilter and makes filterForm pristine
             */
            $scope.resetFilter = function() {
                $scope.filter = angular.copy($scope.cleanFilter);
                $scope.filterForm.$setPristine();
            };

            /**
             * Method necessary for md-data-table
             */
            $scope.onPageChange = function() {};

            /**
             * Method necessary for md-data-table
             */
            $scope.onOrderChange = function() {};

            $scope.filter = angular.copy($scope.cleanFilter);
            $scope.getAllCheques();
        }
    ])
    .directive('dataTable', function() {
        return {
            restrict: 'E',
            controller: 'DataTable',
            scope: {
                filter: '=filter',
                cheques: '=cheques'
            },
            require: ['filter', 'cheques'],
            templateUrl: 'app/filters-page/data-table/data-table.html'
        }
    });
