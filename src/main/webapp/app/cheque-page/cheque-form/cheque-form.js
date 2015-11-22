angular.module("mainModule")
    .controller('ChequeForm', ['$scope', '$http', 'gettextCatalog', '$mdToast', '$state', 'security', '$rootScope', 'warning', '$document',
        function($scope, $http, gettextCatalog, $mdToast, $state, security, $rootScope, warning, $document) {

            /**
             * Method getCheque request from serve-side one cheque with detail information
             * It adds to cheque model cheque, when gets it
             */
            $scope.getCheque = function() {
                $http.get('/api/cheques/' + $scope.cheque.id)
                    .success(function(response) {
                        $scope.cheque = response;
                    });
            };

            /**
             * Method modifyCheque modify current cheque and then send request to server-side
             * It show toast about delivery status
             */
            $scope.sendCheque = function() {
                $http.post('/api/cheques', $scope.cheque)
                    .success(function(response) {
                        $scope.cheque = response;

                        $scope.chequeForm.$setPristine();
                        $scope.chequeForm.$setUntouched();

                        $mdToast.show(
                            $mdToast.simple()
                                .content(gettextCatalog.getString('Cheque') + ' ' + gettextCatalog.getString('synchronized'))
                                .position('top right')
                                .hideDelay(700)
                        );
                    })
                    .error(function(data) {
                        if(data.exception === "org.springframework.orm.ObjectOptimisticLockingFailureException") {
                            warning.showAlertOptimisticLockingException();
                        }
                    });
            };

            /**
             * Method deleteCheque delete current cheque and then send request to server-side
             * It show toast about delivery status
             */
            $scope.deleteCheque = function(event) {
                warning.showConfirmDeleteCheque(event).then(function() {

                    $http.delete('/api/cheques/' + $scope.cheque.id)
                        .success(function() {
                            $state.go('cheque.filter');
                            $mdToast.show(
                                $mdToast.simple()
                                    .content(gettextCatalog.getString('Cheque') + ' №' + $scope.cheque.id + ' ' + gettextCatalog.getString('deleted'))
                                    .position('top right')
                                    .hideDelay(7000)
                            );
                        });

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
                $scope.sendCheque();
                window.print();
            };

            if(!$scope.hasID) {

                $scope.cleanNewCheck = {repairPeriod: 99, receiptDate: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
                    secretary: 'Администратор', components: [], notes: [], payments: [], diagnostics: []};

                /**
                 * Method resetNewCheck reset cheque with default cleanNewCheque data
                 */
                $scope.resetNewCheck = function() {
                    $scope.cheque = angular.copy($scope.cleanNewCheck);
                };

                $scope.resetNewCheck();
            }

            $scope.loadUsers = function() {
                $http.get('/api/users')
                    .success(function(data) {
                        $scope.users = data;
                    });
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


            $scope.disableChequeForm = !security.hasAnyRole(['ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_SECRETARY']);

            $scope.security = security;
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