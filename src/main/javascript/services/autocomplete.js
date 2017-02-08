/**
 * This file contains factory autocomplete to handle autocomplete methods.
 *
 * @version 1.0
 * @author Dmitry
 * @since 08.02.2017
 */
angular.module('mainModule')
    .factory('autocomplete', function($rootScope, $http, $q) {

            return {
                getUsers: function() {
                    var deferred = $q.defer();

                    $http.get('/api/users')
                        .success(function(data) {
                            deferred.resolve(data);
                        });

                    return deferred.promise;
                },
                getAutocompleteData: function (itemName, searchText) {
                    var isFormDirty = typeof arguments[2] !== 'undefined'  ? arguments[2] : true;

                    if(!isFormDirty) return [];

                    var deferred = $q.defer();

                    $http.get('/api/autocomplete/' + itemName + '/' + searchText)
                        .success(function (data) {
                            deferred.resolve(data);
                        });

                    return deferred.promise;
                }
            }

        });