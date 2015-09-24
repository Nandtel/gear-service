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
    .controller("Header", ['$scope', 'currencyRatesService',
        function ($scope, currencyRatesService) {

            currencyRatesService.getCurrencyRates()
                .then(function(rates) {
                    $scope.rates = rates;
                });

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