angular.module('mainModule')
    .factory('chequeService', ['$rootScope', '$http', '$state', '$q', 'warning', '$mdToast', 'gettextCatalog',
        function($rootScope, $http, $state, $q, warning, $mdToast, gettextCatalog) {

            $rootScope.cheque = {components: [], notes: [], payments: [], diagnostics: []};
            $rootScope.chequeList = [];
            $rootScope.balance = {};
            $rootScope.photoList = [];

            return {
                createNewCheque: function() {
                    $rootScope.cheque = {
                        repairPeriod: 99, receiptDate: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
                        components: [], notes: [], payments: [], diagnostics: [], balance: {paidStatus: false},
                        secretary: $rootScope.user.principal, engineer: $rootScope.user.principal};
                },

                getChequeListFromServer: function(filterPreferences) {
                    $http.post('/api/cheques/', filterPreferences)
                        .success(function(data) {
                            $rootScope.chequeList = data;
                        });
                },

                getChequeFromServer: function(chequeID) {
                    $http.get('/api/cheques/' + chequeID)
                        .success(function(data) {
                            $rootScope.cheque = data;
                        });
                },

                deleteChequeFromServer: function(chequeID) {
                    $http.delete('/api/cheques/' + chequeID)
                        .success(function() {
                            $state.go('cheque.filter');
                            $mdToast.show(
                                $mdToast.simple()
                                    .content(gettextCatalog.getString('Cheque') + ' №' + chequeID + ' ' + gettextCatalog.getString('deleted'))
                                    .position('top right')
                                    .hideDelay(7000)
                            );
                        });
                },

                syncChequeWithServer: function(cheque) {
                    var deferred = $q.defer();
                    $http.post('/api/cheques', cheque)
                        .success(function(data) {
                            $rootScope.cheque = data;
                            deferred.resolve(true);

                            $mdToast.show(
                                $mdToast.simple()
                                    .content(gettextCatalog.getString('Cheque') + ' ' + gettextCatalog.getString('synchronized'))
                                    .position('top right')
                                    .hideDelay(700)
                            );
                        })
                        .error(function(data) {
                            deferred.resolve(false);
                            if(data.exception === "org.springframework.orm.ObjectOptimisticLockingFailureException") {
                                warning.showAlertOptimisticLockingException();
                            }
                        });

                    return deferred.promise;
                },


                getChequeBalanceFromServer: function(chequeID) {
                    $http.get('/api/balance/cheque/' + chequeID)
                        .success(function(balance) {
                            $rootScope.balance = balance;
                        })
                        .error(function() {
                            $rootScope.balance = {};
                        });
                },

                syncChequeBalanceWithServer: function(chequeID, balance) {
                    $http.post('/api/balance/cheque/' + chequeID, balance)
                        .success(function(balance) {
                            $rootScope.balance = balance;

                            $mdToast.show(
                                $mdToast.simple()
                                    .content(gettextCatalog.getString('payments synchronized'))
                                    .position('top right')
                                    .hideDelay(700)
                            );
                        });
                },

                getPhotoListFromServer: function(chequeID) {
                    $http.get('/api/photo/cheque/' + chequeID)
                        .success(function(data) {
                            $rootScope.photoList = data;
                        })
                },
                deletePhotoFromServer: function(photoID) {
                    $http.delete('api/photo/' + photoID)
                        .success(function() {
                            chequeService.getPhotoListFromServer(photoID);
                        })
                },

                addToChequeCurrentDateIfEmpty: function(name) {
                    if ($rootScope.cheque.hasOwnProperty(name) && $rootScope.cheque[name] == undefined)
                        $rootScope.cheque[name] = moment().format("YYYY-MM-DDTHH:mm:ssZZ");
                    else
                        $rootScope.cheque[name] = undefined;
                }
            }

        }]);