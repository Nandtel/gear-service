describe('CreateController', function() {
    beforeEach(module('mainModule'));

    var $scope, $controller, $httpBackend, $state;

    beforeEach(inject(function(_$controller_, _$httpBackend_, _$state_) {
        $httpBackend = _$httpBackend_;
        $state = _$state_;
        $scope = {};
        $controller = _$controller_('CreateController', { $scope: $scope });

        $httpBackend.whenGET('/header').respond(200);
        $httpBackend.whenGET('/filters').respond(200);

        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('initial parameter of cleanNewCheck is custom object', function() {
        expect($scope.cleanNewCheck).toBeDefined();
    });

    it('function resetNewCheck should copy to object custom template', function() {
        $scope.cheque = {};
        $scope.resetNewCheck();
        expect($scope.cheque).toEqual($scope.cleanNewCheck);
    });

    it('function addKit should return object with key "kit"', function() {
        expect($scope.addKit('example')).toEqual({'kit': 'example'});
    });

    it('should add note and diagnostic to cheque and send items to server', function() {
        $scope.cheque = {"id":1, "name":'Bob', "notes":[], diagnostics:[]};
        $scope.note = {"text":'text'};
        $scope.diagnostic = {"text":'text'};

        $httpBackend.expectPOST('/cheques', {"id":1, "name":'Bob', "notes":[{"text":'text'}], diagnostics:[{"text":'text'}]}).respond(200);

        $scope.sendCheque();
        $httpBackend.flush();
    });

    it('should create mdDialog for datepicker and take response', inject(function($mdDialog, $q, $rootScope) {
        angular.extend($scope, {$new: function() {} });
        var deferred = $q.defer();

        spyOn($mdDialog, 'show').and.returnValue(deferred.promise);

        $scope.introducedPicker();
        $scope.guaranteePicker();

        deferred.resolve('test');
        $rootScope.$apply();

        expect($scope.cheque.introducedFrom).toEqual('test');
        expect($scope.cheque.guarantee).toEqual('test');
        expect($mdDialog.show).toHaveBeenCalled();
    }));
});