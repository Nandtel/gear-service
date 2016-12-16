angular.module("mainModule")
    .controller('TimePicker', function($scope) {

        $scope.$watch("time", function (newValue, oldValue) {
            if(newValue == undefined) {
                $scope.hours = undefined;
                $scope.minutes = undefined;
            } else {
                $scope.hours = moment(newValue).format("HH");
                $scope.minutes = moment(newValue).format("mm");
            }
        });

        $scope.$watch("hours", function (newValue, oldValue) {
            if(newValue != undefined) {
                $scope.time = moment($scope.time).hours(newValue).format("YYYY-MM-DDTHH:mm:ssZZ");
            }
        });

        $scope.$watch("minutes", function (newValue, oldValue) {
            if(newValue != undefined) {
                $scope.time = moment($scope.time).minutes(newValue).format("YYYY-MM-DDTHH:mm:ssZZ");
            }
        });

    })
    .directive('timePicker', function() {
        return {
            restrict: 'E',
            controller: 'TimePicker',
            scope: {
                time: '=ngModel'
            },
            require: 'ngModel',
            templateUrl: 'directives/time-picker/time-picker.html'
        }
    });