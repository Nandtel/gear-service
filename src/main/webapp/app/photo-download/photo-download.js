angular.module("mainModule")
    .controller("PhotoDownload", ['$scope', '$http',
        function($scope, $http) {

            $http.get('/photo/' + $scope.photoID)
                .success(function(data) {
                    $scope.image = data;
                });

        }])
    .directive('photoDownload', function() {
        return {
            restrict: 'E',
            controller: 'PhotoDownload',
            scope: {
                photoID: '=photoId'
            },
            require: 'photoId',
            templateUrl: 'app/photo-download/photo-download.html'
        }
    });