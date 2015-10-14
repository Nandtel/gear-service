angular.module("mainModule")
    .controller('ChequeForm', ['$scope', '$http', 'gettextCatalog', '$mdToast', '$state',
        function($scope, $http, gettextCatalog, $mdToast, $state) {

            /**
             * Method getCheque request from serve-side one cheque with detail information
             * It adds to cheque model cheque, when gets it
             */
            $scope.getCheque = function() {
                $http.get('/api/cheques/' + $scope.cheque.id).success(function(response) {
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

                        $scope.form.$setPristine();
                        $scope.form.$setUntouched();

                        $mdToast.show(
                            $mdToast.simple()
                                .content(gettextCatalog.getString('Cheque') + ' ' + gettextCatalog.getString('added'))
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
                $http.delete('/api/cheques/' + $scope.cheque.id)
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
             * Method addKit create new chip with text and return it
             * @param text of kit chip
             * @returns kit object with kit-text
             */
            $scope.addKit = function(text) {
                return {'text': text};
            };

            $scope.printCheque = function() {
                window.print();
            };

            if(!$scope.hasID) {

                $scope.cleanNewCheck = {repairPeriod: 99, introducedDate: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
                    inspectorName: 'Администратор', kits: [], notes: [], payments: [], diagnostics: []};

                /**
                 * Method resetNewCheck reset cheque with default cleanNewCheque data
                 */
                $scope.resetNewCheck = function() {
                    $scope.cheque = angular.copy($scope.cleanNewCheck);
                };

                $scope.resetNewCheck();
            }

            $scope.$watch('form.$dirty', function(newValue, oldValue) {
                $scope.hasUnsavedChange = newValue;
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