/**
 * This file contains mainModule with injecting dependencies, routing and configuration.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
var app =
    angular.module("mainModule", ['gettext', 'ui.utils', 'ui.router', 'angularMoment', 'ngMaterial', 'mdDateTime',
    'md.data.table', 'angular-loading-bar', 'AngularPrint', 'templates'])
    .config(['$stateProvider', function($stateProvider){
        $stateProvider
            .state('cheque', {
                abstract: true,
                url: "/cheques",
                views: {
                    "header": {
                        controller:
                            ['$rootScope', '$scope', '$state', function($rootScope, $scope, $state) {
                                $scope.selectedTab = $state.current.data.selectedTab;

                                $rootScope.$on('$stateChangeSuccess',
                                    function(event, toState, toParams, fromState, fromParams) {
                                        $scope.selectedTab = toState.data.selectedTab;
                                    });
                        }],
                        template: '<header selected-tab="selectedTab"></header>'
                    },
                    "content": {
                        template: "<ui-view></ui-view>"
                    }
                }
            })
            .state('cheque.filter', {
                url: "^/filter",
                template: "<filters-page></filters-page>",
                data: {'selectedTab': 0}
            })
            .state('cheque.edit', {
                url: "^/cheque/{chequeId:[0-9]{1,10}}",
                controller:
                    ['$scope', '$stateParams', function($scope, $stateParams) {
                        $scope.chequeID = $stateParams.chequeId;
                    }],
                template: '<cheque-page cheque-id="chequeID"></cheque-page>',
                data: {'selectedTab': 1}
            })
            .state('cheque.create', {
                url: "^/cheque",
                template: '<cheque-page></cheque-page>',
                data: {'selectedTab': 1}
            })
    }])
    .config(['$urlRouterProvider', function($urlRouterProvider){
        $urlRouterProvider.when('', '/filter');
        $urlRouterProvider.otherwise('/filter');
    }])
    .config(['$mdThemingProvider', function($mdThemingProvider){
        $mdThemingProvider
            .theme('header', 'default')
            .accentPalette('indigo');
    }])
    .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
        cfpLoadingBarProvider.includeSpinner = false;
        cfpLoadingBarProvider.latencyThreshold = 100;
    }])
    .run(['gettextCatalog', 'amMoment',
        function(gettextCatalog, amMoment){
            gettextCatalog.setCurrentLanguage('ru');
            amMoment.changeLocale('ru');
    }]);