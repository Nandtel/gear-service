app.directive('datePicker', function() {
    return {
        restrict: 'AE',
        controller: ['$scope', function($scope) {

            $scope.$watch("date", function (newValue, oldValue) {
                if(newValue == undefined) {
                    $scope.dateInner = undefined;
                } else {
                    $scope.dateInner = moment(newValue).toDate();
                }
            });

            $scope.$watch("dateInner", function (newValue, oldValue) {
                if(newValue != undefined) {
                    $scope.date = moment(newValue).format("YYYY-MM-DDTHH:mm:ssZZ");
                }
            });
        }],
        scope: {
            date: '=ngModel'
        },
        require: 'ngModel',
        template: '<md-datepicker ng-model="dateInner" style="margin: 0 0.7rem;"></md-datepicker>'
    }
});