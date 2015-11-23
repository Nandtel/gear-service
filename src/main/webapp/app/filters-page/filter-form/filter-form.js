angular.module("mainModule")
    .controller('FilterForm', ['$scope', '$http', '$timeout', '$mdDialog', 'gettextCatalog', '$q',
        function ($scope, $http, $timeout, $mdDialog, gettextCatalog, $q) {
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
                $http.post('/api/cheques/pre/', $scope.filter)
                    .success(function(data) {
                        $scope.test = data;
                        console.log($scope.test);
                        //$scope.cheques = data;
                    });
            };
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
