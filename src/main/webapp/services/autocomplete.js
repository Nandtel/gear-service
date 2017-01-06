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
                    var deferred = $q.defer();

                    $http.get('/api/autocomplete/' + itemName + '/' + searchText)
                        .success(function (data) {
                            deferred.resolve(data);
                        });

                    return deferred.promise;
                }
            }

        });