describe('Test factories in mainModule', function() {
    var dialogTemplate;

    beforeEach(function () {
        module('mainModule');

        inject(function ($injector) {
            dialogTemplate = $injector.get('dialogTemplate');
        });
    });

    it('dialogTemplate should return string contain html for md-dialog', function() {
        expect(dialogTemplate('example', 'example')).toMatch('<md-dialog>');
    });

});