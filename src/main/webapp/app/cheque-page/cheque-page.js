/**
 * Controller EditController handles request from edit.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .controller("ChequePage", ['$scope', '$stateParams', '$http', 'Upload',
        function ($scope, $stateParams, $http, Upload) {
            $scope.cheque = {kits: [], payments: [], phone: "", email: ""};
            $scope.hasID = false;

            /**
             * Method getCheque request from serve-side one cheque with detail information
             * It adds to cheque model cheque, when gets it
             */
            $scope.getCheque = function (chequeID) {
                $http.get('/api/cheques/' + chequeID)
                    .success(function (response) {
                        $scope.cheque = response;
                        $scope.hasID = true;
                    });
            };

            $scope.$watch('cheque.id', function (newValue, oldValue) {
                $scope.hasID = !!newValue;
            });

            $scope.hasID = !!$scope.chequeID;

            if ($scope.hasID)
                $scope.getCheque($scope.chequeID);






            $scope.$watch('files', function () {
                $scope.upload($scope.files);
            });
            $scope.$watch('file', function () {
                if ($scope.file != null) {
                    $scope.files = [$scope.file];
                }
            });
            $scope.log = '';

            $scope.upload = function (files) {
                if (files && files.length) {
                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        if (!file.$error) {
                            Upload.upload({
                                url: '/upload-image',
                                data: {
                                    file: file
                                }
                            }).progress(function (evt) {
                                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                                $scope.log = 'progress: ' + progressPercentage + '% ' +
                                    evt.config.data.file.name + '\n' + $scope.log;
                            }).success(function (data, status, headers, config) {
                                $timeout(function() {
                                    $scope.log = 'file: ' + config.data.file.name + ', Response: ' + JSON.stringify(data) + '\n' + $scope.log;
                                });
                            });
                        }
                    }
                }
            };
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
