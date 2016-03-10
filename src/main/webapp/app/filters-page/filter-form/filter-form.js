angular.module("mainModule")
    .controller('FilterForm', ['$rootScope', '$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog', '$q', 'cheque', 'autocomplete',
        function ($rootScope, $scope, $http, $timeout, $mdDialog, gettextCatalog, $q, cheque, autocomplete) {
            $scope.filterForm = {};
            $scope.getAutocompleteData = autocomplete.getDataFromServer;

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
                cheque.getChequeListFromServer($rootScope.filter);
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
