describe('DialogController', function() {
    beforeEach(module('mainModule'));

    var $scope, $controller, $mdDialog;

    beforeEach(inject(function(_$controller_, _$mdDialog_) {
        $mdDialog = _$mdDialog_;
        $scope = {};
        $controller = _$controller_('DialogController', { $scope: $scope });
    }));

    it('should have two methods', function() {

        spyOn($mdDialog, 'hide');
        spyOn($mdDialog, 'cancel');

        $scope.hide();
        $scope.cancel();

        expect($mdDialog.hide).toHaveBeenCalled();
        expect($mdDialog.cancel).toHaveBeenCalled();
    });
});