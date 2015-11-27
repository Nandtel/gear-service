angular.module("mainModule")
    .controller('FilterForm', ['$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog', '$q', 'chequeService',
        function ($scope, $http, $timeout, $mdDialog, gettextCatalog, $q, chequeService) {
            $scope.filterForm = {};
            $scope.tableFilter = {order: '-id', limit: 15, page: 1};
            $scope.items = [];

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

            $scope.serverItems = function(name) {
                $http.get('/api/autocomplete/' + name)
                    .success(function(data) {
                        $scope.items = data;
                    });
            };

            $scope.serverItemsUser = function(name) {
                $http.get('/api/autocomplete/user/' + name)
                    .success(function(data) {
                        $scope.items = data;
                    });
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
