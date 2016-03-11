angular.module("mainModule")
    .controller('CommentsBlock', ['$scope', '$http', '$document', '$rootScope', 'security', 'warning', 'cheque',
        function($scope, $http, $document, $rootScope, security, warning, cheque) {
            $scope.comment = undefined;
            $scope.add = 3;
            $scope.limit = 3;
            $scope.bottomButton = 'show more';

            /**
             * Method saveComment create new comment object and send it to server-side and then set status of this
             * form is Untouched and Pristine
             */
            $scope.saveComment = function() {
                $scope.comment.user = $rootScope.user.principal;
                $scope.comment.cheque = $scope.cheque;
                $http.post('/api/cheques/' + $scope.cheque.id + '/' + $scope.title, $scope.comment)
                    .success(function() {
                        $scope.comment.text = undefined;
                        $scope.commentsForm.$setPristine();
                        $scope.commentsForm.$setUntouched();
                        cheque.getChequeFromServer($scope.cheque.id);
                    });
            };

            /**
             * Method removeComment send request to server-side to delete comment from database
             * @param commentID - is id of comment, that necessary to delete
             */
            $scope.deleteComment = function(commentID, event) {
                warning.showConfirmDeleteComment(event).then(function() {

                    $http.delete('/api/cheques/' + $scope.cheque.id + '/'+ $scope.title + '/' + commentID)
                        .success(function() {
                            cheque.getChequeFromServer($scope.cheque.id);
                        });

                }, function() {});

            };

            $scope.addedLesserThanElem = function() {
                if(!!$scope.cheque[$scope.title])
                    return $scope.add < $scope.cheque[$scope.title].length;
            };

            var limitLesserThanElem = function() {
                return $scope.limit < $scope.cheque[$scope.title].length;
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

            $scope.$watch('commentsForm.$dirty', function (newValue, oldValue) {
                $scope.$parent.formDirty.comments = newValue;
            });

            $scope.security = security;
        }
    ])
    .directive('commentsBlock', [function() {
        return {
            restrict: 'E',
            controller: 'CommentsBlock',
            scope: {
                cheque: '=ngModel',
                title: '=title'
            },
            require: 'ngModel',
            templateUrl: 'app/cheque-page/comments-block/comments-block.html'
        }
    }]);