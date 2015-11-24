/**
 * Controller EditController handles request from edit.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .controller("ChequePage", ['$rootScope', '$scope', '$stateParams', '$http', 'chequeService',
        function ($rootScope, $scope, $stateParams, $http, chequeService) {

            $scope.hasID = !!$scope.chequeID;

            if ($scope.hasID)
                chequeService.getChequeFromServer($scope.chequeID);
            else
                chequeService.createNewCheque();

            //$scope.$watch('cheque.id', function (newValue, oldValue) {
            //    $scope.hasID = !!newValue;
            //});

            $rootScope.$watch('cheque', function (newValue, oldValue) {
                $scope.cheque = newValue;
                $scope.chequeID = newValue.id;
                $scope.hasID = !!newValue.id;
            });

        }
    ])
    .directive('chequePage', function() {
        return {
            restrict: 'E',
            controller: 'ChequePage',
            scope: {
                chequeID: '=chequeId'
            },
            templateUrl: 'app/cheque-page/cheque-page.html'
        }
    });