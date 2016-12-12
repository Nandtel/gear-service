angular.module('mainModule')
    .factory('cheque', ['$rootScope', '$http', '$state', '$q', 'warning', '$mdToast', 'gettextCatalog',
        function($rootScope, $http, $state, $q, warning, $mdToast, gettextCatalog) {

            $rootScope.cheque = {
                repairPeriod: 99, receiptDate: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
                components: [], notes: [], diagnostics: [], balance: {paidStatus: false},
                secretary: $rootScope.user.principal, engineer: $rootScope.user.principal};
            $rootScope.balance = {payments: []};
            $rootScope.photoList = [];
            $rootScope.filter = {
                customerName: null, engineer: null, model: null, phoneNumber: null,
                productName: null, representativeName: null, secretary: null, serialNumber: null};
            $rootScope.currentFilter = {};
            $rootScope.page = {};

            function getSortWithReplacementMinusToDesc(sort) {
                if(sort.indexOf('-') !== -1) {
                    sort = sort.substr(1);
                    sort += ',desc';
                }
                else
                    sort += ',asc';

                return sort;
            }

            return {
                createNewCheque: function() {
                    $rootScope.cheque = {
                        repairPeriod: 99, receiptDate: moment().format("YYYY-MM-DDTHH:mm:ssZZ"),
                        components: [], notes: [], diagnostics: [], balance: {paidStatus: false},
                        secretary: $rootScope.user.principal, engineer: $rootScope.user.principal};
                    $rootScope.balance = {payments: []};
                    $rootScope.photoList = [];
                },
                getChequeListFromServer: function() {
                    var tableFilter = {
                        page: $rootScope.tableFilter.page - 1,
                        size: $rootScope.tableFilter.size,
                        sort: getSortWithReplacementMinusToDesc($rootScope.tableFilter.sort)
                    };

                    $http.post('/api/cheques/list', $rootScope.currentFilter, {params: tableFilter})
                        .success(function(data) {
                            $rootScope.page = data;
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
                        .error(function() {
                            deferred.resolve(false);
                        });

                    return deferred.promise;
                },
                getDiagnosticsFromServer: function(chequeID) {
                    $http.get('/api/cheques/' + chequeID + '/diagnostics')
                        .then(function(response) {
                            $rootScope.cheque.diagnostics = response.data;
                        })
                },
                getNotesFromServer: function(chequeID) {
                    $http.get('/api/cheques/' + chequeID + '/notes')
                        .then(function(response) {
                            $rootScope.cheque.notes = response.data;
                        })
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
                    var deferred = $q.defer();
                    $http.post('/api/balance/cheque/' + chequeID, balance)
                        .success(function(balance) {
                            deferred.resolve(true);
                            $rootScope.balance = balance;

                            $mdToast.show(
                                $mdToast.simple()
                                    .content(gettextCatalog.getString('payments synchronized'))
                                    .position('top right')
                                    .hideDelay(700)
                            );
                        })
                        .error(function() {
                            deferred.resolve(false);
                        });

                    return deferred.promise;
                },
                getPhotoListFromServer: function(chequeID) {
                    $http.get('/api/photo/cheque/' + chequeID)
                        .success(function(data) {
                            $rootScope.photoList = data;
                        })
                },
                deletePhotoFromServer: function(photoID) {
                    $http.delete('api/photo/' + photoID);
                },
                addToChequeCurrentDateIfEmpty: function(name, field) {
                    if ($rootScope.cheque.hasOwnProperty(name) && $rootScope.cheque[field] === true)
                        $rootScope.cheque[name] = moment().format("YYYY-MM-DDTHH:mm:ssZZ");
                    else
                        $rootScope.cheque[name] = undefined;
                },
                cleanDateIfOff: function (name, field) {
                    if (!($rootScope.cheque.hasOwnProperty(name) && $rootScope.cheque[field] === true))
                        $rootScope.cheque[name] = undefined;
                }
            }

        }]);