/**
 * This file contains factory currencyRatesService to handle operations with currency rates.
 *
 * @version 1.0
 * @author Dmitry
 * @since 08.02.2017
 */
angular.module('mainModule')
    .service('currencyRatesService', function(CacheFactory, $http, $q, $rootScope) {
            var currencyRatesCache;

            if(!CacheFactory.get('currencyRatesCache')) {

                CacheFactory.createCache('currencyRatesCache', {
                    deleteOnExpire: 'aggressive',
                    maxAge: 60 * 60 * 1000 * 8, // 8 hours
                    recycleFreq: 60000,
                    storageMode: 'localStorage',
                    onExpire: function (key, value) {
                        $http.get(key).success(function (data) {
                            currencyRatesCache.put(key, data);
                        });
                    }
                });
            }

            currencyRatesCache = CacheFactory.get('currencyRatesCache');

            return {
                getCurrencyRate: function() {
                    $http.get('/api/currency-rate', {cache: currencyRatesCache})
                        .success(function(data) {
                            $rootScope.currencyRates = data;
                        });
                },
                refreshCurrencyRate: function() {
                    currencyRatesCache.remove('/api/currency-rate');
                    this.getCurrencyRate();
                },
                getListOfCurrencyRates: function() {
                    var deferred = $q.defer();
                    $http.get('/api/currency-rate-list')
                        .success(function(data) {
                            deferred.resolve(data);
                    });
                    return deferred.promise;
                },
                setListOfCurrencyRates: function(rates) {
                    $http.post('/api/currency-rate-list', rates);
                }
            }
        }
    );