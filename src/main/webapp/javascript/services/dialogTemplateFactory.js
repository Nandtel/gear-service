/**
 * Factory service dialogTemplate for create string template for md-dialog
 * @returns function, that create string template with model and displayMode values
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
app.factory('dialogTemplate', [function() {
    return function(model, displayMode) {
        return ' <md-dialog> \
                    <md-content> \
                        <time-date-picker ng-model="' + model + '" display-mode="' + displayMode + '" display-twentyfour="true"\
                        on-save="hide($value)" on-cancel="cancel()"></time-date-picker> \
                    </md-content> \
                 </md-dialog> \
                 ';
    }
}]);