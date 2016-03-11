angular.module('mainModule')
    .factory('autocomplete', ['$rootScope', '$http',
        function($rootScope, $http) {

            $rootScope.autocomplete = {
                customers: [],
                products: [],
                models: [],
                serials: [],
                representatives: [],
                emails: [],
                components: [],
                secretaries: [],
                engineers: [],
                users: [],
                phoneNumbers: []
            };

            return {
                getDataFromServer: function(itemName) {
                    $http.get('/api/autocomplete/' + itemName)
                        .success(function(data) {
                            $rootScope.autocomplete[itemName] = data;
                        });
                }
            }

        }]);