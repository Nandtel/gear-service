angular.module("mainModule")
    .controller('CommentsBlock',
        function($scope, $http, $document, $rootScope, security, warning, cheque, $timeout) {
            $scope.comment = undefined;
            $scope.add = 3;
            $scope.limit = 3;
            $scope.bottomButton = 'show more';
            $scope.security = security;

            /**
             * Method saveComment create new comment object and send it to server-side and then set status of this
             * form is Untouched and Pristine
             */
            $scope.saveComment = function() {
                $scope.comment.user = $rootScope.user.principal;
                $scope.comment.cheque = $scope.cheque;
                $http.post('/api/cheques/' + $scope.cheque.id + '/' + $scope.type, $scope.comment)
                    .success(function() {
                        $scope.comment.text = undefined;
                        $scope.commentsForm.$setPristine();
                        $scope.commentsForm.$setUntouched();

                        if($scope.type === 'diagnostics')
                            cheque.getDiagnosticsFromServer($scope.cheque.id);

                        if($scope.type === 'notes')
                            cheque.getNotesFromServer($scope.cheque.id);

                    });
            };

            /**
             * Method removeComment send request to server-side to delete comment from database
             * @param commentID - is id of comment, that necessary to delete
             */
            $scope.deleteComment = function(commentID, event) {
                warning.showConfirmDeleteComment(event).then(function() {

                    $http.delete('/api/cheques/' + $scope.cheque.id + '/'+ $scope.type + '/' + commentID)
                        .success(function() {
                            if($scope.type === 'diagnostics')
                                cheque.getDiagnosticsFromServer($scope.cheque.id);

                            if($scope.type === 'notes')
                                cheque.getNotesFromServer($scope.cheque.id);
                        });

                }, function() {});

            };

            $scope.addedLesserThanElem = function() {
                if(!!$scope.cheque[$scope.type])
                    return $scope.add < $scope.cheque[$scope.type].length;
            };

            var limitLesserThanElem = function() {
                return $scope.limit < $scope.cheque[$scope.type].length;
            };

            $scope.limitComments = function() {

                if(limitLesserThanElem())
                    $scope.limit += $scope.add;
                else
                    $scope.limit = $scope.add;

                if(!limitLesserThanElem())
                    $scope.bottomButton = 'roll up';
                else
                    $scope.bottomButton = 'show more';
            };

            $timeout(function() {
                $scope.commentsForm.$setPristine();
                $scope.$watch('commentsForm.$dirty', function (newValue, oldValue) {
                    $scope.$parent.formDirty.comments = newValue;
                });
            }, 1000);
        }
    )
    .directive('commentsBlock', [function() {
        return {
            restrict: 'E',
            controller: 'CommentsBlock',
            scope: {
                cheque: '=ngModel',
                type: '=type'
            },
            require: 'ngModel',
            templateUrl: 'app/cheque-page/comments-block/comments-block.html'
        }
    }]);