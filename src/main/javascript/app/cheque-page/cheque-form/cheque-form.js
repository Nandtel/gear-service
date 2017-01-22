angular.module("mainModule")
    .controller('ChequeForm',
        function($scope, $http, gettextCatalog, $mdToast, $state, security, $rootScope, warning, $document,
                 cheque, autocomplete, $timeout) {

            $scope.disableChequeForm = !security.hasAnyRole(['ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_SECRETARY']);
            $scope.security = security;
            $scope.getAutocompleteData = autocomplete.getAutocompleteData;
            $scope.currentDateIfEmpty = cheque.addToChequeCurrentDateIfEmpty;
            $scope.cleanDateIfOff = cheque.cleanDateIfOff;
            $scope.users = [];

            $scope.getUsers = function() {
                autocomplete.getUsers()
                    .then(function(data) {
                        $scope.users = data;
                    });
            };

            $scope.getUsers();

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
                            cheque.getChequeBalanceFromServer($rootScope.cheque.id);
                        }
                    });
            };

            /**
             * Method deleteCheque delete current cheque and then send request to server-side
             * It show toast about delivery status
             */
            $scope.deleteCheque = function(event) {
                warning.showConfirmDeleteCheque(event).then(function() {
                    $scope.$parent.formDirty = {payment: false, cheque: false};
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

            $scope.syncWithReadyStatus = function () {
                $scope.cheque.readyStatus = $scope.cheque.withoutRepair;
                $scope.currentDateIfEmpty('readyDate', 'readyStatus');
            };

            $scope.syncWithWithoutRepairStatus = function () {
                var readyStatus = $scope.cheque.readyStatus;
                var withoutRepair = $scope.cheque.withoutRepair;

                if(readyStatus === false && withoutRepair === true)
                    $scope.cheque.withoutRepair = readyStatus;
            };

            $scope.printCheque = function() {
                $scope.printable = true;
                $timeout(function() {window.print();}, 0);
            };

            $timeout(function() {
                $scope.chequeForm.$setPristine();
                $scope.$watch('chequeForm.$dirty', function (newValue, oldValue) {
                    $scope.$parent.formDirty.cheque = newValue;
                });
            }, 1000);

        }
    )
    .directive('chequeForm', [function() {
        return {
            restrict: 'E',
            controller: 'ChequeForm',
            scope: {
                cheque: '=ngModel',
                hasID: '=hasId',
                printable: '=printable'
            },
            require: ['ngModel', 'hasId'],
            templateUrl: 'app/cheque-page/cheque-form/cheque-form.html'
        }
    }]);