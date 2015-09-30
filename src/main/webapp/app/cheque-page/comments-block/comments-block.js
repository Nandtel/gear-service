angular.module("mainModule")
    .controller('CommentsBlock', ['$scope', '$http', '$document',
        function($scope, $http, $document) {
            $scope.comment = undefined;
            $scope.add = 3;
            $scope.limit = 3;
            $scope.bottomButton = 'show more';

            /**
             * Method saveComment create new comment object and send it to server-side and then set status of this
             * form is Untouched and Pristine
             */
            $scope.saveComment = function() {
                $http.post('/cheques/' + $scope.cheque.id + '/' + $scope.title, $scope.comment)
                    .success(function() {
                        $scope.comment.text = undefined;
                        $scope.form.$setPristine();
                        $scope.form.$setUntouched();

                        $scope.getCheque();
                    });
            };

            /**
             * Method removeComment send request to server-side to delete comment from database
             * @param commentID - is id of comment, that necessary to delete
             */
            $scope.deleteComment = function(commentID) {
                $http.delete('/cheques/' + $scope.cheque.id + '/'+ $scope.title + '/' + commentID)
                    .success(function() {$scope.getCheque()});
            };

            /**
             * Method getCheque request from serve-side one cheque with detail information
             * It adds to cheque model cheque, when gets it
             */
            $scope.getCheque = function() {
                $http.get('/cheques/' + $scope.cheque.id)
                    .success(function (response) {$scope.cheque = response;});
            };

            $scope.addedLesserThanElem = function() {
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