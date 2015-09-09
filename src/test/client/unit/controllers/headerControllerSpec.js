describe('HeaderController', function() {
    beforeEach(module('mainModule'));

    var $scope, $controller, $httpBackend, $rootScope, $state;

    beforeEach(inject(function(_$controller_, _$httpBackend_, _$rootScope_, _$state_) {
        $httpBackend = _$httpBackend_;
        $rootScope = _$rootScope_;
        $state = _$state_;
        $scope = $rootScope.$new();
        $controller =  _$controller_('HeaderController', { $scope: $scope });
    }));

    it('should contain empty object', function() {
        expect($scope.data).toEqual({});
    });

    it('should change data for different tabs', inject(function($location) {
        $httpBackend.whenGET('/header').respond(200);
        $httpBackend.whenGET('/filters').respond(200);
        $httpBackend.whenGET('/create').respond(200);

        $location.replace().url('/create');
        $rootScope.$digest();

        $httpBackend.flush();

        expect($scope.data).toEqual({'selectedIndex': 2, 'disabledEdit': true});
    }));

});