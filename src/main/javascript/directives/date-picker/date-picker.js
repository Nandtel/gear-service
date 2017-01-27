angular.module("mainModule")
    .controller('DatePicker', function($scope) {

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
    })
    .directive('datePicker', function() {
        return {
            restrict: 'E',
            controller: 'DatePicker',
            scope: {
                date: '=ngModel',
                disabled: '=ngDisabled',
                placeholder: '=mdPlaceholder'
            },
            require: ['ngModel', 'ngDisabled'],
            templateUrl: 'directives/date-picker/date-picker.html'
        }
    });