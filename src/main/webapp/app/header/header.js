/**
 * Controller HeaderController handles request from header.html
 * It handles current positions of tab and change it, when it is necessary
 * It handles active state of edit tab: active, when user view all information of one cheque and inactive in other cases
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .controller("Header", ['$scope', 'currencyRatesService', '$mdToast',
        function ($scope, currencyRatesService, $mdToast) {

            $scope.currencyDate = function() {
                return moment($scope.rates.id).format('ll');
            };

            currencyRatesService.getCurrencyRates()
                .then(function(rates) {
                    $scope.rates = rates;
                });

            $scope.refreshCurrencyRates = function() {
                console.log('1241241');
                currencyRatesService.refreshCurrencyRates()
                    .then(function(rates) {
                        $scope.rates = rates;

                        $mdToast.show(
                            $mdToast.simple()
                                .content('Currency rates refreshed')
                                .position('top right')
                                .hideDelay(700)
                        );

                    });
            }
        }
    ])
    .directive('header', function() {
        return {
            restrict: 'E',
            controller: 'Header',
            scope: {
                selectedTab: '=selectedTab'
            },
            require: 'selectedTab',
            templateUrl: 'app/header/header.html'
        }
    });