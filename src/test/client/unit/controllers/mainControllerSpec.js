describe('MainController', function() {
    beforeEach(module('mainModule'));

    var $scope, $controller, $httpBackend, $state;

    beforeEach(inject(function(_$controller_, _$httpBackend_, _$state_) {
        $httpBackend = _$httpBackend_;
        $state = _$state_;

        $scope = {};
        $controller = _$controller_('MainController', { $scope: $scope });

        $httpBackend.whenGET('/header').respond(200);
        $httpBackend.whenGET('/filters').respond(200);

        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should initial parameter of filterForm as empty object', function() {
        expect($scope.filterForm).toEqual({});
    });

    it('initial parameter of cleanFilter is custom object', function() {
        expect($scope.cleanFilter).toEqual({
            order: '-id', limit: 15, page: 1, nameOfCustomer: "", nameOfProduct: "", model: "", serialNumber: "",
            purchaserName: "", inspectorName: "", masterName: ""
        });
    });

    it('should load items from server', inject(function($timeout) {
        $httpBackend.expectGET('/cheques').respond([{id: 1, label: 'Mock'}]);

        expect($scope.cheques).toBeUndefined();

        $timeout.flush();
        $httpBackend.flush();

        expect($scope.cheques).toEqual([{id: 1, label: 'Mock'}]);
    }));

    it('should reset filter and make filter-form pristine', function() {
        $scope.filter = {};
        expect($scope.filter).toEqual({});

        $scope.filterForm = {$setPristine: function(){}, $pristine: false};
        expect($scope.filterForm.$pristine).toBeFalsy();

        spyOn($scope.filterForm, '$setPristine');

        $scope.resetFilter();

        expect($scope.filter).toBeDefined();
        expect($scope.filterForm.$setPristine).toHaveBeenCalled();
    });

    it('should create mdDialog for datepicker and take response', inject(function($mdDialog, $q, $rootScope) {
        angular.extend($scope, {$new: function() {} });
        var deferred = $q.defer();

        spyOn($mdDialog, 'show').and.returnValue(deferred.promise);

        $scope.introducedFromPicker();
        $scope.introducedToPicker();

        deferred.resolve('test');
        $rootScope.$apply();

        expect($scope.filter.introducedFrom).toEqual('test');
        expect($scope.filter.introducedTo).toEqual('test');
        expect($mdDialog.show).toHaveBeenCalled();
    }));

});