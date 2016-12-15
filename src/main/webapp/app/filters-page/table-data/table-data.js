angular.module("mainModule")
    .controller('TableData',
        function ($rootScope, $scope, $http, $timeout, $mdDialog, gettextCatalog, $state, cheque)  {

            $scope.label = {
                page: "Страница",
                rowsPerPage: gettextCatalog.getString('Rows per page:'),
                of: gettextCatalog.getString('of')
            };

            $scope.mdOnPaginate = cheque.getChequeListFromServer;
            $scope.mdOnReorder = cheque.getChequeListFromServer;

            $scope.open = function($event, chequeID) {
                if ($event.which === 2 || ($event.which === 1 && ($event.metaKey || $event.ctrlKey))) {
                    window.open($state.href('cheque.edit', {chequeId: chequeID}, {absolute: true}), '_blank');
                }
            };

    })
    .directive('tableData', function() {
        return {
            restrict: 'E',
            controller: 'TableData',
            templateUrl: 'app/filters-page/table-data/table-data.html'
        }
    });