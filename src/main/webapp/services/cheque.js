angular.module('mainModule')
    .factory('cheque', ['$rootScope', '$http', '$state', '$q',
        function($rootScope, $http, $state, $q) {

            var cheque;

            function getChequeFromServer(filterPreferences) {
                $http.post('/api/cheques/pre/', filterPreferences)
                    .success(function(data) {
                        cheque = data;
                    });
            }

            function sendChequeToServer() {

            }

            return {
                get: getChequeFromServer,
                send: sendChequeToServer
            }

        }]);