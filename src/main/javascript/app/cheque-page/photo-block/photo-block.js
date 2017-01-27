angular.module("mainModule")
    .controller('PhotoBlock',
        function($scope, Upload, $http, $state, $location, $rootScope, warning, cheque, $window, security) {
            $scope.security = security;

            $scope.deletePhoto = function(photoID, event) {
                warning.showConfirmDeletePhoto(event).then(function() {
                    $http.delete('api/photo/' + $scope.chequeID + '/'+ photoID)
                        .success(function() {
                            cheque.getPhotoListFromServer($scope.chequeID);
                        })
                }, function() {});
            };

            $scope.openPhoto = function(photoID) {
                $window.open('/api/photo/' + photoID, '_blank');
            };

            $scope.$watch('files', function () {
                $scope.upload($scope.files);
            });

            $scope.$watch('file', function () {
                if ($scope.file != null) {
                    $scope.files = [$scope.file];
                }
            });

            $scope.upload = function (files) {
                if (files && files.length) {
                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        if (!file.$error) {
                            Upload.upload({
                                url: '/api/upload-image',
                                data: {
                                    file: file,
                                    chequeID: $scope.chequeID,
                                    username: $rootScope.user.principal.username
                                }
                            })
                            .success(function () {
                                cheque.getPhotoListFromServer($scope.chequeID);
                            });
                        }
                    }
                }
            };
        }
    )
    .directive('photoBlock', [function() {
        return {
            restrict: 'E',
            controller: 'PhotoBlock',
            scope: {
                chequeID: '=chequeId'
            },
            require: 'chequeId',
            templateUrl: 'app/cheque-page/photo-block/photo-block.html'
        }
    }]);