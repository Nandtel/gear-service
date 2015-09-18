/**
 * Controller CreateController handles request from create.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
app.controller("CreateController", ['$scope', '$http', '$state', '$filter', '$mdToast', 'gettextCatalog',
    function ($scope, $http, $state, $filter, $mdToast, gettextCatalog) {
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

            $scope.cheque.guarantee = moment($scope.cheque.guarantee).format("YYYY-MM-DDTHH:mm:ssZZ");

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

        $scope.resetNewCheck();
    }
]);