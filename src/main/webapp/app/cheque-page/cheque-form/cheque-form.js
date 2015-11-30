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

            $scope.$watch('chequeForm.$dirty', function (newValue, oldValue) {
                $scope.$parent.formDirty.cheque = newValue;
            });

            $scope.items = [];
            $scope.serverItems = function(name) {
                $http.get('/api/autocomplete/' + name)
                    .success(function(data) {
                        $scope.items = data;
                    });
            };

            $scope.serverItemsComponent = function(name) {
                $http.get('/api/au/component/' + name)
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

            $scope.currentDateIfEmpty = function(name) {
                chequeService.addToChequeCurrentDateIfEmpty(name);
            }

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