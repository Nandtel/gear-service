angular.module('mainModule')
    .factory('chequeService', ['$rootScope', '$http', '$state', '$q', 'warning', '$mdToast', 'gettextCatalog',
        function($rootScope, $http, $state, $q, warning, $mdToast, gettextCatalog) {

            $rootScope.cheque = {components: [], notes: [], payments: [], diagnostics: []};
            $rootScope.chequeList = [];
            $rootScope.balance = {};
            $rootScope.photoList = [];

            function getChequeListFromServer(filterPreferences) {
                $http.post('/api/cheques/pre/', filterPreferences)
                    .success(function(data) {
                        $rootScope.chequeList = data;
                    });
            }

            function createNewCheque() {
                $rootScope.cheque = {
                    repairPeriod: 99, receiptDate: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
                    components: [], notes: [], payments: [], diagnostics: [], balance: {paidStatus: false},
                    secretary: $rootScope.user.principal};
            }

            function getChequeFromServer(chequeID) {
                $http.get('/api/cheques/' + chequeID)
                    .success(function(data) {
                        $rootScope.cheque = data;
                    });
            }

            function syncChequeWithServer(cheque) {
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
            }

            function deleteChequeFromServer(chequeID) {
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
            }

            function getChequeBalanceFromServer(chequeID) {
                $http.get('/api/balance/cheque/' + chequeID)
                    .success(function(balance) {
                        $rootScope.balance = balance;
                    })
                    .error(function() {
                        $rootScope.balance = {};
                    });
            }

            function syncChequeBalanceWithServer(chequeID, balance) {
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
            }

            function getPhotoListFromServer(chequeID) {
                $http.get('/api/photo/cheque/' + chequeID)
                    .success(function(data) {
                        $rootScope.photoList = data;
                    })
            }

            function deletePhotoFromServer(photoID) {
                $http.delete('api/photo/' + photoID)
                    .success(function() {
                        chequeService.getPhotoListFromServer(photoID);
                    })
            }

            function addToChequeCurrentDateIfEmpty(name) {
                if ($rootScope.cheque.hasOwnProperty(name))
                    $rootScope.cheque[name] = moment().format("YYYY-MM-DDTHH:mm:ssZZ");
            }

            return {
                createNewCheque: createNewCheque,
                getChequeListFromServer: getChequeListFromServer,
                getChequeFromServer: getChequeFromServer,
                deleteChequeFromServer: deleteChequeFromServer,
                syncChequeWithServer: syncChequeWithServer,
                getChequeBalanceFromServer: getChequeBalanceFromServer,
                syncChequeBalanceWithServer: syncChequeBalanceWithServer,
                getPhotoListFromServer: getPhotoListFromServer,
                deletePhotoFromServer: deletePhotoFromServer,
                addToChequeCurrentDateIfEmpty: addToChequeCurrentDateIfEmpty
            }

        }]);