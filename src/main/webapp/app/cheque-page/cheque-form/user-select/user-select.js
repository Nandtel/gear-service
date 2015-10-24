angular.module("mainModule")
    .controller('UserSelect', ['$scope', '$http',
        function($scope, $http) {

            $scope.loadUsers = function() {
                $http.get('/api/users')
                    .success(function(data) {
                        $scope.users = data;
                    });
            }

        }
    ])
    .directive('userSelect', [function() {
        return {
            restrict: 'E',
            controller: 'UserSelect',
            scope: {
                user: '=ngModel',
                title: '=title'
            },
            require: ['ngModel', 'title'],
            templateUrl: 'app/cheque-page/cheque-form/user-select/user-select.html'
        }
    }]);