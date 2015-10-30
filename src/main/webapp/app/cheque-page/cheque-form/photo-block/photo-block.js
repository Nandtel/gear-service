angular.module("mainModule")
    .controller('PhotoBlock', ['$scope', 'Upload', '$http', '$state', '$location',
        function($scope, Upload, $http, $state, $location) {

            $scope.open = function(name) {
                window.location.assign("https://" + $location.host() + ":"+ $location.port() + '/photo/' + name);
            };

            $scope.getAllPhoto = function() {
                $http.get('/photos')
                    .success(function(data) {
                        $scope.photos = data;
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
                                url: '/upload-image',
                                file: file
                            })
                            .success(function () {
                                $scope.getAllPhoto();
                            });
                        }
                    }
                }
            };

            $scope.getAllPhoto();
        }
    ])
    .directive('photoBlock', [function() {
        return {
            restrict: 'E',
            controller: 'PhotoBlock',
            scope: {
                cheque: '=ngModel',
                hasID: '=hasId'
            },
            require: ['ngModel', 'hasId'],
            templateUrl: 'app/cheque-page/cheque-form/photo-block/photo-block.html'
        }
    }]);