/**
 * Controller EditController handles request from edit.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .controller("ChequePage", ['$rootScope', '$scope', '$stateParams', '$http', 'cheque', 'warning', '$state',
        function ($rootScope, $scope, $stateParams, $http, cheque, warning, $state) {
            $scope.formDirty = {payment: false, cheque: false};
            $scope.hasID = !!$scope.chequeID;

            if ($scope.hasID)
                cheque.getChequeFromServer($scope.chequeID);
            else
                cheque.createNewCheque();

            $scope.$watch('cheque.id', function (newValue, oldValue) {
                $scope.hasID = !!newValue;
            });

            $rootScope.$watch('cheque', function (newValue, oldValue) {
                $scope.cheque = newValue;
                $scope.chequeID = newValue.id;
                $scope.hasID = !!newValue.id;
            });

            $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {

                if((fromState.name === 'cheque.edit' || fromState.name === 'cheque.create')
                    && ($scope.formDirty.cheque || $scope.formDirty.payment)) {
                    event.preventDefault();

                    warning.showConfirmUnsavedChanges().then(function() {
                        $scope.formDirty = {payment: false, cheque: false};
                        $state.go(toState);
                    }, function() {});
                }
            });

        }
    ])
    .directive('chequePage', function() {
        return {
            restrict: 'E',
            controller: 'ChequePage',
            scope: {
                chequeID: '=?chequeId'
            },
            templateUrl: 'app/cheque-page/cheque-page.html'
        }
    });