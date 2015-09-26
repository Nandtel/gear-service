angular.module('mainModule')
    .service('currencyRatesService', ['CacheFactory', '$http', '$q',
        function(CacheFactory, $http, $q) {
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
                getCurrencyRates: function() {
                    var deferred = $q.defer();
                    $http.get('/currency-rates', {cache: currencyRatesCache})
                        .success(function(data) {
                            deferred.resolve(data);
                        });
                    return deferred.promise;
                },
                refreshCurrencyRates: function() {
                    var deferred = $q.defer();
                    currencyRatesCache.remove('/currency-rates');
                    $http.get('/currency-rates', {cache: currencyRatesCache})
                        .success(function(data) {
                            deferred.resolve(data);
                        });
                    return deferred.promise;
                }
            }
        }
    ]);