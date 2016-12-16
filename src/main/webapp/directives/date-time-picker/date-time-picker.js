angular.module("mainModule")
    .controller('DateTimePicker', function($scope) {

        $scope.$watch("datetime", function (newValue, oldValue) {
            if(newValue == undefined) {
                $scope.datetimeinner = undefined;
                $scope.date = undefined;
                $scope.time = undefined;
            } else {
                $scope.date = moment(newValue).toDate();
                $scope.time = moment(newValue).toDate();
            }
        });

        $scope.$watch("date", function (newValue, oldValue) {
            if(newValue != undefined) {
                var hours = moment($scope.datetime).hours();
                var minutes = moment($scope.datetime).minutes();
                $scope.datetime = moment(newValue).hours(hours).minutes(minutes).format("YYYY-MM-DDTHH:mm:ssZZ");
            }
        });

        $scope.$watch("time", function (newValue, oldValue) {
            if(newValue != undefined) {
                var hours = moment(newValue).hours();
                var minutes = moment(newValue).minutes();
                $scope.datetime = moment($scope.datetime).hours(hours).minutes(minutes).format("YYYY-MM-DDTHH:mm:ssZZ");
            }
        });

    })
    .directive('dateTimePicker', function() {
        return {
            restrict: 'E',
            controller: 'DateTimePicker',
            scope: {
                datetime: '=ngModel',
                disableChequeForm: '=ngDisabled'
            },
            require: 'ngModel',
            templateUrl: 'directives/date-time-picker/date-time-picker.html'
        }
    });