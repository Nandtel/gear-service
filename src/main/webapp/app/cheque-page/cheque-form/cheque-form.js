angular.module("mainModule")
    .controller('ChequeForm', ['$scope', '$http', 'gettextCatalog', '$mdToast', '$state', 'security', '$rootScope',
        'warning', '$document', 'cheque', 'autocomplete',
        function($scope, $http, gettextCatalog, $mdToast, $state, security, $rootScope, warning, $document,
                 cheque, autocomplete) {

            $scope.disableChequeForm = !security.hasAnyRole(['ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_SECRETARY']);
            $scope.security = security;
            $scope.getAutocompleteData = autocomplete.getDataFromServer;
            $scope.currentDateIfEmpty = cheque.addToChequeCurrentDateIfEmpty;

            /**
             * Method modifyCheque modify current cheque and then send request to server-side
             * It show toast about delivery status
             */
            $scope.syncCheque = function() {
                cheque.syncChequeWithServer($scope.cheque)
                    .then(function(success) {
                        if(success) {
                            $scope.chequeForm.$setPristine();
                            $scope.chequeForm.$setUntouched();
                        }
                    });
            };

            /**
             * Method deleteCheque delete current cheque and then send request to server-side
             * It show toast about delivery status
             */
            $scope.deleteCheque = function(event) {
                warning.showConfirmDeleteCheque(event).then(function() {
                    cheque.deleteChequeFromServer($scope.cheque.id);
                }, function() {});
            };

            /**
             * Method addKit create new chip with text and return it
             * @param text of kit chip
             * @returns kit object with kit-text
             */
            $scope.addKit = function(name) {
                return {'name': name};
            };

            $scope.printCheque = function() {
                $scope.syncCheque();
                window.print();
            };

            $scope.$watch('chequeForm.$dirty', function (newValue, oldValue) {
                $scope.$parent.formDirty.cheque = newValue;
            });
        }
    ])
    .directive('chequeForm', [function() {
        return {
            restrict: 'E',
            controller: 'ChequeForm',
            scope: {
                cheque: '=ngModel',
                hasID: '=hasId'
            },
            require: ['ngModel', 'hasId'],
            templateUrl: 'app/cheque-page/cheque-form/cheque-form.html'
        }
    }]);