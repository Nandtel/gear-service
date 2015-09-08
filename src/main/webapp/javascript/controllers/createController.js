/**
 * Controller CreateController handles request from create.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
app.controller("CreateController", ['$scope', '$http', '$state', '$filter', '$mdDialog', '$mdToast', 'gettextCatalog', 'dialogTemplate',
    function ($scope, $http, $state, $filter, $mdDialog, $mdToast, gettextCatalog, dialogTemplate) {
        $scope.cleanNewCheck = {repairPeriod: 99, introduced: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
            inspectorName: 'Администратор', kits: [], notes: [], diagnostics: []};

        /**
         * Method resetNewCheck reset cheque with default cleanNewCheque data
         */
        $scope.resetNewCheck = function() {
            $scope.cheque = angular.copy($scope.cleanNewCheck);
        };

        /**
         * Method sendCheque create new cheque and push to it notes and diagnostic comments and then send to server-side
         * It show toast about delivery status
         */
        $scope.sendCheque = function() {

            if($scope.note != null)
                $scope.cheque.notes.push($scope.note);

            if($scope.diagnostic != null)
                $scope.cheque.diagnostics.push($scope.diagnostic);

            $http.post('/cheques', $scope.cheque)
                .success(function() {
                    $state.go('cheque.filter');
                    $mdToast.show(
                        $mdToast.simple()
                            .content(gettextCatalog.getString('Cheque') + ' ' + gettextCatalog.getString('added'))
                            .position('top right')
                            .hideDelay(700)
                    );
                });
        };

        /**
         * Method addKit create new chip with text and return it
         * @param text of kit chip
         * @returns kit object with kit-text
         */
        $scope.addKit = function(text) {
            return {'text': text};
        };

        /**
         * Method introducedPicker create service md-dialog from angular-material for fill cheque.introduced with date
         * @param event, that gets from click on date-field
         * It adds to cheque object field with name introduced and date from md-dialog-date-time-picker
         */
        $scope.introducedPicker = function(event) {
            $mdDialog.show({
                template: dialogTemplate('cheque.introduced', 'full'),
                targetEvent: event,
                controller: 'DialogController',
                scope: $scope.$new()
            })
                .then(function(answer) {
                    $scope.cheque.introduced = moment(answer).format("YYYY-MM-DDTHH:mm:ssZZ");
                });
        };

        /**
         * Method guaranteePicker create service md-dialog from angular-material for fill cheque.guarantee with date
         * @param event, that gets from click on date-field
         * It adds to cheque object field with name guarantee and  date from md-dialog-date-time-picker
         */
        $scope.guaranteePicker = function(event) {
            $mdDialog.show({
                template: dialogTemplate('cheque.guarantee', 'date'),
                targetEvent: event,
                controller: 'DialogController',
                scope: $scope.$new()
            })
                .then(function(answer) {
                    $scope.cheque.guarantee = moment(answer).format("YYYY-MM-DDTHH:mm:ssZZ");
                });
        };

        $scope.resetNewCheck();
    }
]);