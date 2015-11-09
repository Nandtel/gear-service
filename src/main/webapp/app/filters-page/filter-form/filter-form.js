angular.module("mainModule")
    .controller('FilterForm', ['$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog', '$q',
        function ($scope, $http, $timeout, $mdDialog, gettextCatalog, $q) {
            $scope.filterForm = {};
            $scope.cleanFilter = {
                order: '-id', limit: 15, page: 1, nameOfCustomer: "", nameOfProduct: "", model: "", serialNumber: "",
                purchaserName: "", inspectorName: "", masterName: "", introducedFrom: undefined,
                introducedTo: undefined
            };
            /**
             * Method resetFilter add to filter necessary data for right displaying
             * It adds to filter data from cleanFilter and makes filterForm pristine
             */
            $scope.resetFilter = function() {
                $scope.filter = angular.copy($scope.cleanFilter);
                $scope.filterForm.$setPristine();
            };

            $scope.filter = angular.copy($scope.cleanFilter);
        }
    ])
    .directive('filterForm', function() {
        return {
            restrict: 'E',
            controller: 'FilterForm',
            scope: {
                filter: '=filter',
                cheques: '=cheques'
            },
            require: ['filter', 'cheques'],
            templateUrl: 'app/filters-page/filter-form/filter-form.html'
        }
    });
