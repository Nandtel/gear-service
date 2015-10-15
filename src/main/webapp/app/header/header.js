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
    .controller("Header", ['$scope', 'currencyRatesService', '$mdToast', '$rootScope', 'auth', 'gettextCatalog',
        function ($scope, currencyRatesService, $mdToast, $rootScope, auth, gettextCatalog) {

            currencyRatesService.getCurrencyRate();

            $scope.refreshCurrencyRates = function() {
                currencyRatesService.refreshCurrencyRate();

                $mdToast.show(
                    $mdToast.simple()
                        .content('Currency rates refreshed')
                        .position('top right')
                        .hideDelay(700));
            };

            $rootScope.$on('$routeChangeStart', function() {
                console.log('Change the route');
            });

            $scope.authenticated = function() {
                return auth.authenticated;
            };

            $scope.profileName = function() {
                if(!!$rootScope.user.principal && !!$rootScope.user.principal.fullName)
                    return $rootScope.user.principal.fullName;

                return gettextCatalog.getString('profile');
            };

            $scope.logout = auth.logout;
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