app.directive('timePicker', function() {
  return {
    restrict: 'E',
    controller: ['$scope', function($scope) {

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
    }],
    scope: {
      time: '=ngModel'
    },
    require: 'ngModel',
    template: '<md-content layout="row" layout-align="center center" style="margin-left: 10px; font-size: 15px">' +
    ' <md-input-container style="width: 1.5rem; padding: 8px 0 0 0;">' +
    '   <input type="text" ng-model="hours" maxlength="2" max="24" ng-model-options="{updateOn: \'blur\'}">' +
    ' </md-input-container>' +
    ' <span style="padding: 8px 3px 0 3px;">:</span>' +
    ' <md-input-container style="width: 1.5rem; padding: 8px 0 0 0;">' +
    '   <input type="text" ng-model="minutes" maxlength="2" max="60" ng-model-options="{updateOn: \'blur\'}">' +
    ' </md-input-container>' +
    '</md-content>'
  }
});