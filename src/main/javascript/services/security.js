/**
 * This file contains factory security to handle requests with permissions.
 *
 * @version 1.0
 * @author Dmitry
 * @since 08.02.2017
 */
angular.module('mainModule')
    .service('security', function($rootScope, $http, $location, $state) {

            return {

                init: function () {
                    var hasAnyRole = this.hasAnyRole;
                    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                        if (!!toState.data.access)
                            if (!hasAnyRole(toState.data.access)) {
                                event.preventDefault();
                                $state.go('cheque.filter');
                            }
                    });
                },

                hasRole: function (role) {
                    var boolean = false;
                    angular.forEach($rootScope.user.authorities, function (i) {
                        if (i.authority === role) {
                            boolean = true;
                        }
                    });
                    return boolean;
                },

                hasAnyRole: function (arrayRoles) {
                    var boolean = false;
                    angular.forEach($rootScope.user.authorities, function (i) {
                        angular.forEach(arrayRoles, function (j) {
                            if (i.authority === j) {
                                boolean = true;
                            }
                        })
                    });
                    return boolean;
                },

                isSameUser: function (user) {
                    return $rootScope.user.principal.username === user.username;
                }

            };
        });