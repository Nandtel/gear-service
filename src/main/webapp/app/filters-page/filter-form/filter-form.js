angular.module("mainModule")
    .controller('FilterForm', ['$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog', '$q', 'chequeService',
        function ($scope, $http, $timeout, $mdDialog, gettextCatalog, $q, chequeService) {
            $scope.filterForm = {};
            $scope.tableFilter = {order: '-id', limit: 15, page: 1};

            /**
             * Method resetFilter add to filter necessary data for right displaying
             * It adds to filter data from cleanFilter and makes filterForm pristine
             */
            $scope.resetFilter = function() {
                $scope.filter = angular.copy({});
                $scope.filterForm.$setPristine();
            };

            $scope.sendFilterPreferences = function() {
                chequeService.getChequeListFromServer($scope.filter);
            };
        }
    ])
    .directive('filterForm', function() {
        return {
            restrict: 'E',
            controller: 'FilterForm',
            templateUrl: 'app/filters-page/filter-form/filter-form.html'
        }
    });
