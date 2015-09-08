/**
 * Controller EditController handles request from edit.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
app.controller("EditController", ['$scope', '$stateParams', '$http', '$state', '$filter', '$compile', '$mdDialog', '$mdToast', 'gettextCatalog', 'dialogTemplate',
    function ($scope, $stateParams, $http, $state, $filter, $compile, $mdDialog, $mdToast, gettextCatalog, dialogTemplate) {
        $scope.cheque = {kits: []};

        /**
         * Method factory makeDialog with values of model and displayMode create function for date-time-pickers methods
         * @param model is parameter name for cheque object
         * @param displayMode is parameter for oriental of date-time-picker
         * @returns function for date-time-pickers methods
         */
        function makeDialog(model, displayMode) {
            return function(event) {
                $mdDialog.show({
                    template: dialogTemplate(model, displayMode),
                    targetEvent: event,
                    controller: 'DialogController',
                    scope: $scope.$new()
                })
                    .then(function(answer) {
                        $scope.cheque[model] = moment(answer).format("YYYY-MM-DDTHH:mm:ssZZ");
                    });
            };
        }

        /**
         * Method introducedPicker create service md-dialog from angular-material for fill cheque.introduced with date
         * @param event, that gets from click on date-field
         * It adds to cheque object field with name introduced and date from md-dialog-date-time-picker
         */
        $scope.introducedPicker = makeDialog('introduced', 'full');

        /**
         * Method guaranteePicker create service md-dialog from angular-material for fill cheque.guarantee with date
         * @param event, that gets from click on date-field
         * It adds to cheque object field with name guarantee and date from md-dialog-date-time-picker
         */
        $scope.guaranteePicker = makeDialog('guarantee', 'date');

        /**
         * Method readyPicker create service md-dialog from angular-material for fill cheque.ready with date
         * @param event, that gets from click on date-field
         * It adds to cheque object field with name ready and date from md-dialog-date-time-picker
         */
        $scope.readyPicker = makeDialog('ready', 'date');

        /**
         * Method issuedPicker create service md-dialog from angular-material for fill cheque.issued with date
         * @param event, that gets from click on date-field
         * It adds to cheque object field with name issued and date from md-dialog-date-time-picker
         */
        $scope.issuedPicker = makeDialog('issued', 'date');

        /**
         * Method getCheque request from serve-side one cheque with detail information
         * It adds to cheque model cheque, when gets it
         */
        $scope.getCheque = function() {
            $http.get('/cheques/' + $stateParams.chequeId).success(function (response) {
                $scope.cheque = response;
            });
        };

        /**
         * Method modifyCheque modify current cheque and then send request to server-side
         * It show toast about delivery status
         */
        $scope.modifyCheque = function() {
            $http.post('/cheques/' + $stateParams.chequeId, $scope.cheque)
                .success(function() {
                    $state.go('cheque.filter');
                    $mdToast.show(
                        $mdToast.simple()
                            .content(gettextCatalog.getString('Cheque') + ' №' + $scope.cheque.id + ' ' + gettextCatalog.getString('modified'))
                            .position('top right')
                            .hideDelay(700)
                    );
                });
        };

        /**
         * Method deleteCheque delete current cheque and then send request to server-side
         * It show toast about delivery status
         */
        $scope.deleteCheque = function() {
            $http.delete('/cheques/' + $stateParams.chequeId)
                .success(function() {
                    $state.go('cheque.filter');
                    $mdToast.show(
                        $mdToast.simple()
                            .content(gettextCatalog.getString('Cheque') + ' №' + $scope.cheque.id + ' ' + gettextCatalog.getString('deleted'))
                            .position('top right')
                            .hideDelay(700)
                    );
                });
        };

        /**
         * Method saveDiagnostic create new diagnostic object and send it to server-side and then set status of this
         * form is Untouched and Pristine
         */
        $scope.saveDiagnostic = function() {
            $http.post('/cheques/' + $stateParams.chequeId + '/diagnostic', $scope.diagnostic)
                .success(function() {
                    $scope.diagnostic.text = undefined;
                    $scope.diagnostics.$setPristine();
                    $scope.diagnostics.$setUntouched();

                    $scope.getCheque();
                });
        };

        /**
         * Method removeDiagnostic send request to server-side to delete diagnostic from database
         * @param diagnosticId - is id of diagnostic, that necessary to delete
         */
        $scope.removeDiagnostic = function(diagnosticId) {
            $http.delete('/cheques/' + $stateParams.chequeId + '/diagnostic/' + diagnosticId)
                .success(function() {$scope.getCheque();});
        };

        /**
         * Method saveNote create new note object and send it to server-side and then set status of this
         * form is Untouched and Pristine
         */
        $scope.saveNote = function() {
            $http.post('/cheques/' + $stateParams.chequeId + '/note', $scope.note)
                .success(function() {
                    $scope.note.text = undefined;
                    $scope.notes.$setPristine();
                    $scope.notes.$setUntouched();

                    $scope.getCheque();
                });
        };

        /**
         * Method removeNote send request to server-side to delete note from database
         * @param noteId - is id of note, that necessary to delete
         */
        $scope.removeNote = function(noteId) {
            $http.delete('/cheques/' + $stateParams.chequeId + '/note/' + noteId)
                .success(function() {$scope.getCheque();});
        };

        /**
         * Method addKit create new chip with text and return it
         * @param text of kit chip
         * @returns kit object with kit-text
         */
        $scope.addKit = function(text) {
            return {'text': text};
        };

        $scope.getCheque();
    }
]);