describe('EditController', function() {
    beforeEach(module('mainModule'));

    var $scope, $controller, $httpBackend, $stateParams, $state;

    beforeEach(inject(function(_$controller_, _$httpBackend_, _$stateParams_, _$state_) {
        $httpBackend = _$httpBackend_;
        $stateParams = _$stateParams_;
        $state = _$state_;

        $scope = {};
        $stateParams.chequeId = 1;
        $controller = _$controller_('EditController', { $scope: $scope });

        $httpBackend.whenGET('/cheques/1').respond({"id":1, "name":"Bob"});
        $httpBackend.whenGET('/header').respond(200);
        $httpBackend.whenGET('/filters').respond(200);

        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should create mdDialog for datepicker and take response', inject(function($mdDialog, $q, $rootScope) {
        angular.extend($scope, {$new: function() {} });
        var deferred = $q.defer();

        spyOn($mdDialog, 'show').and.returnValue(deferred.promise);

        $scope.introducedPicker();
        $scope.guaranteePicker();

        deferred.resolve('test');
        $rootScope.$apply();

        expect($scope.cheque.introduced).toEqual('test');
        expect($scope.cheque.guarantee).toEqual('test');
        expect($mdDialog.show).toHaveBeenCalled();
    }));

    it('should be parameter of cheque is custom object', function() {
        expect($scope.cheque).toEqual({"id":1, "name":"Bob"});
    });

    it('should return object with key "kit"', function() {
        expect($scope.addKit('example')).toEqual({'kit': 'example'});
    });

    it('should send to server post request with modified cheque', function() {
        $httpBackend.expectPOST('/cheques/1', {"id":1, "name":'Bob'}).respond(200);

        $stateParams.chequeId = 1;
        $scope.cheque = {"id":1, "name":'Bob'};

        $scope.modifyCheque();
        $httpBackend.flush();
    });

    it('should send to server delete request for cheque', function() {
        $httpBackend.expectDELETE('/cheques/1').respond(200);

        $stateParams.chequeId = 1;

        $scope.deleteCheque();
        $httpBackend.flush();
    });

    it('should send to server diagnostic comment with text', function() {
        $httpBackend.expectPOST('/cheques/1/diagnostic', {"text":'text'}).respond(200);

        $stateParams.chequeId = 1;
        $scope.diagnostic = {};
        $scope.diagnostic.text = 'text';
        $scope.diagnostics = {$setPristine: function(){}, $setUntouched: function() {}};

        spyOn($scope.diagnostics, '$setPristine');
        spyOn($scope.diagnostics, '$setUntouched');
        expect($scope.diagnostic.text).toEqual('text');

        $scope.saveDiagnostic();
        $httpBackend.flush();

        expect($scope.diagnostics.$setPristine).toHaveBeenCalled();
        expect($scope.diagnostics.$setUntouched).toHaveBeenCalled();
        expect($scope.diagnostic.text).toBeUndefined();
    });

    it('should send to server delete request for diagnostic comment', function() {
        $httpBackend.expectDELETE('/cheques/diagnostic/1').respond(200);

        $stateParams.chequeId = 1;

        $scope.removeDiagnostic(1);
        $httpBackend.flush();
    });

    it('should send to server note comment with text', function() {
        $httpBackend.expectPOST('/cheques/1/note', {"text":'text'}).respond(200);

        $stateParams.chequeId = 1;
        $scope.note = {};
        $scope.note.text = 'text';
        $scope.notes = {$setPristine: function(){}, $setUntouched: function() {}};

        spyOn($scope.notes, '$setPristine');
        spyOn($scope.notes, '$setUntouched');
        expect($scope.note.text).toEqual('text');

        $scope.saveNote();
        $httpBackend.flush();

        expect($scope.notes.$setPristine).toHaveBeenCalled();
        expect($scope.notes.$setUntouched).toHaveBeenCalled();
        expect($scope.note.text).toBeUndefined();
    });

    it('should send to server delete request for note comment', function() {
        $httpBackend.expectDELETE('/cheques/note/1').respond(200);

        $stateParams.chequeId = 1;

        $scope.removeNote(1);
        $httpBackend.flush();
    });

});