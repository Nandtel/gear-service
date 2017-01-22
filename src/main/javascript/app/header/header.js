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
    .controller("Header", function ($scope, currencyRatesService, $mdToast, $rootScope, auth, gettextCatalog, security) {

            currencyRatesService.getCurrencyRate();

            $scope.refreshCurrencyRates = function() {
                currencyRatesService.refreshCurrencyRate();

                $mdToast.show(
                    $mdToast.simple()
                        .content('Currency rates refreshed')
                        .position('top right')
                        .hideDelay(700));
            };

            $scope.authenticated = function() {
                return auth.authenticated;
            };

            $scope.profileName = function() {
                if(!!$rootScope.user.principal && !!$rootScope.user.principal.fullname)
                    return $rootScope.user.principal.fullname;

                return gettextCatalog.getString('profile');
            };

            $scope.security = security;

            $scope.logout = auth.logout;
        }
    )
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