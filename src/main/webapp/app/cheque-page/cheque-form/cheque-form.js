angular.module("mainModule")
    .controller('ChequeForm', ['$scope', '$http', 'gettextCatalog', '$mdToast', '$state', 'security', '$rootScope', 'warning', '$document', 'chequeService',
        function($scope, $http, gettextCatalog, $mdToast, $state, security, $rootScope, warning, $document, chequeService) {

            $scope.disableChequeForm = !security.hasAnyRole(['ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_SECRETARY']);
            $scope.security = security;

            /**
             * Method modifyCheque modify current cheque and then send request to server-side
             * It show toast about delivery status
             */
            $scope.syncCheque = function() {
                chequeService.syncChequeWithServer($scope.cheque)
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
                    chequeService.deleteChequeFromServer($scope.cheque.id);
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

            $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

                if(fromState.name === 'cheque.edit' && !!$scope.chequeForm && $scope.chequeForm.$dirty === true) {
                    event.preventDefault();

                    warning.showConfirmUnsavedChanges().then(function() {
                        $scope.chequeForm.$setPristine();
                        $state.go(toState);
                    }, function() {});
                }

            });

            $scope.items = [];
            $scope.serverItems = function(name) {
                $http.get('/api/autocomplete/' + name)
                    .success(function(data) {
                        $scope.items = data;
                    });
            };

            $scope.loadUsers = function() {
                $http.get('/api/users')
                    .success(function(data) {
                        $scope.users = data;
                    });
            };

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