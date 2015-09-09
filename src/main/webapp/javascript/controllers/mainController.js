/**
 * Controller MainController handles request from index.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
app.controller("MainController", ['$scope', '$http', '$timeout', '$mdDialog', 'dialogTemplate', 'gettextCatalog',
    function ($scope, $http, $timeout, $mdDialog, dialogTemplate, gettextCatalog) {
        $scope.filterForm = {};
        $scope.cleanFilter = {
            order: '-id', limit: 15, page: 1, nameOfCustomer: "", nameOfProduct: "", model: "", serialNumber: "",
            purchaserName: "", inspectorName: "", masterName: ""
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
            $timeout(function() {
                $http.get('/cheques').success(function(response) {
                    $scope.cheques = response;
                });
            }, 1000);
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
         * Method introducedFromPicker create service md-dialog from angular-material for fill introducedFrom with date
         * @param event, that gets from click on date-field
         * It adds to filters object field with name introducedFrom and date from md-dialog-date-time-picker
         */
        $scope.introducedFromPicker = function(event) {
            $mdDialog.show({
                template: dialogTemplate('filter.introducedFrom', 'full'),
                targetEvent: event,
                controller: 'DialogController',
                scope: $scope.$new()
            })
                .then(function(answer) {
                    $scope.filter.introducedFrom = moment(answer).format("YYYY-MM-DDTHH:mm:ssZZ");
                });
        };

        /**
         * Method introducedToPicker create service md-dialog from angular-material for fill introducedTo with date
         * @param event, that gets from click on date-field
         * It adds to filters object field with name introducedTo date from md-dialog-date-time-picker
         */
        $scope.introducedToPicker = function(event) {
            $mdDialog.show({
                template: dialogTemplate('filter.introducedTo', 'full'),
                targetEvent: event,
                controller: 'DialogController',
                scope: $scope.$new()
            })
                .then(function(answer) {
                    $scope.filter.introducedTo = moment(answer).format("YYYY-MM-DDTHH:mm:ssZZ");
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

        $scope.filter = angular.copy($scope.cleanFilter);
        $scope.getAllCheques();
    }
]);