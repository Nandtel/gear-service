/**
 * Controller DialogController handles output of dialogTemplateFactory
 * It handles action-buttons in dialog
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
app.controller("DialogController", ['$scope', '$mdDialog',
    function ($scope, $mdDialog) {
        /**
         * Method hide exit from dialog-date-time-picker with changes
         * @param value is date from dialog-date-time-picker
         */
        $scope.hide = function(value) {
            $mdDialog.hide(value);
        };

        /**
         * Method cancel exit from dialog-date-time-picker without changes
         */
        $scope.cancel = function() {
            $mdDialog.cancel();
        };
    }
]);