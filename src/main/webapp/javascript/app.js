/**
 * This file contains mainModule with injecting dependencies, routing and configuration.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
var app =
    angular.module("mainModule", ['gettext', 'ui.utils', 'ui.router', 'angularMoment', 'ngMaterial', 'mdDateTime',
    'md.data.table', 'angular-loading-bar'])
    .config(['$stateProvider', function($stateProvider){
        $stateProvider
            .state('cheque', {
                abstract: true,
                url: "/cheques",
                views: {
                    "header": {
                        controller: 'HeaderController',
                        templateUrl: "/header"
                    },
                    "content": {
                        template: "<ui-view></ui-view>"
                    }
                }
            })
            .state('cheque.filter', {
                url: "^/filter",
                controller: 'MainController',
                templateUrl: "/filters",
                data: {'selectedTab': 0, 'disabledEdit': true}
            })
            .state('cheque.edit', {
                url: "/{chequeId:[0-9]{1,10}}",
                controller: "EditController",
                templateUrl: "/edit",
                data: {'selectedTab': 1, 'disabledEdit': false}
            })
            .state('cheque.create', {
                url: "^/create",
                controller: "CreateController",
                templateUrl: "/create",
                data: {'selectedTab': 2, 'disabledEdit': true}
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