/**
 * Controller EditController handles request from edit.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .controller("ChequePage", ['$scope', '$stateParams', '$http',
        function ($scope, $stateParams, $http) {
            $scope.cheque = {kits: [], payments: [], phone: "", email: ""};
            $scope.hasID = false;

            /**
             * Method getCheque request from serve-side one cheque with detail information
             * It adds to cheque model cheque, when gets it
             */
            $scope.getCheque = function(chequeID) {
                $http.get('/api/cheques/' + chequeID)
                    .success(function (response) {
                        $scope.cheque = response;
                        $scope.hasID = true;
                        console.log($scope.hasID);
                    });
            };

            $scope.$watch('cheque.id', function (newValue, oldValue) {
                $scope.hasID = !!newValue;
            });

            $scope.hasID = !!$scope.chequeID;

            if($scope.hasID)
                $scope.getCheque($scope.chequeID);

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
