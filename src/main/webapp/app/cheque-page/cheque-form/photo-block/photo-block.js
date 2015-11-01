angular.module("mainModule")
    .controller('PhotoBlock', ['$scope', 'Upload', '$http', '$state', '$location', '$rootScope',
        function($scope, Upload, $http, $state, $location, $rootScope) {

            $scope.getAllPhotoFromCheque = function() {
                $http.get('/api/photo/cheque/' + $scope.cheque.id)
                    .success(function(data) {
                        $scope.photos = data;
                    })
            };

            $scope.deletePhoto = function(photoID) {
                $http.delete('api/photo/' + photoID)
                    .success(function() {
                        $scope.getAllPhotoFromCheque();
                    })
            };

            $scope.openPhoto = function(ID) {
                $state.go('cheque.photo', {photoID: ID});
            };

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
                                url: '/api/upload-image',
                                data: {
                                    file: file,
                                    chequeID: $scope.cheque.id,
                                    username: $rootScope.user.principal.username
                                }
                            })
                            .success(function () {
                                $scope.getAllPhotoFromCheque();
                            });
                        }
                    }
                }
            };

            $scope.$watch('cheque.id', function(newValue, oldValue) {
                if(newValue !== undefined)
                    $scope.getAllPhotoFromCheque();
            });

        }
    ])
    .directive('photoBlock', [function() {
        return {
            restrict: 'E',
            controller: 'PhotoBlock',
            scope: {
                cheque: '=ngModel'
            },
            require: 'ngModel',
            templateUrl: 'app/cheque-page/cheque-form/photo-block/photo-block.html'
        }
    }]);