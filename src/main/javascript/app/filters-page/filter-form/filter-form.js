angular.module("mainModule")
    .controller('FilterForm', ['$rootScope', '$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog', '$q', 'cheque', 'autocomplete',
        function ($rootScope, $scope, $http, $timeout, $mdDialog, gettextCatalog, $q, cheque, autocomplete) {
            $scope.filterForm = {};
            $scope.getAutocompleteData = autocomplete.getAutocompleteData;
            $scope.getExcelAnalytics = cheque.getExcelAnalytics;

            /**
             * Method resetFilter add to filter necessary data for right displaying
             * It adds to filter data from cleanFilter and makes filterForm pristine
             */
            $scope.resetFilter = function() {
                $rootScope.filter = angular.copy({});
                $scope.filterForm.$setPristine();
            };

            $scope.sendFilterPreferences = function() {
                $rootScope.tableFilter.page = 1;
                $rootScope.currentFilter = angular.copy($rootScope.filter);
                cheque.getChequeListFromServer();
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
