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
                    console.log(files);
                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        if (!file.$error) {
                            Upload.upload({
                                url: '/upload-image',
                                data: {
                                    file: file
                                },
                                ignoreLoadingBar: true
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
            templateUrl: 'app/cheque-page/photo-block/photo-block.html'
        }
    }]);