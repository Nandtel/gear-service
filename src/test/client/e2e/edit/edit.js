describe('edit.scala.html', function(){

    var addTestSampleTo = function(field) {
        switch(field.type) {
            case 'text': element(by.model(field.name)).sendKeys('T'); break;
            case 'chip': element(by.model(field.name)).sendKeys('Тест' + protractor.Key.ENTER); break;
            case 'comment':
                element(by.model(field.name)).sendKeys('Тест');
                element(by.id(field.clickToAdd)).click();
                browser.waitForAngular();
                break;
        }
    };

    var checkTestSampleIn = function(field) {
        switch (field.type) {
            case 'text':
                element(by.model(field.name)).getAttribute('value').then(function(text) {
                    expect(text.slice(-1)).toEqual('T'); //check that last two symbols is same as test value
                });
                break;
            case 'chip':
                //check that last chip contain test value
                expect(element.all(by.css('md-chip')).last().element(by.css('md-chip-template')).getText())
                    .toEqual('Тест');
                break;
            case 'comment':
                //check that comment text is same as test value
                expect(element.all(by.repeater(field.repeater).column(field.name)).first().getText()).toEqual('Тест');
                break;
        }
    };

    var cleanTestSampleFrom = function(field) {
        switch (field.type) {
            case 'text':
                element(by.model(field.name)).sendKeys(protractor.Key.BACK_SPACE); break;
            case 'chip':
                element.all(by.css('md-chip button.md-chip-remove')).last().click();
                break;
            case 'comment':
                element.all(by.css(field.clickToDel)).first().click(); break;
        }
    };

    beforeEach(function() {
        browser.get('/#/cheques/1');
        browser.waitForAngular();
    });

    var fields = [
        {name:'diagnostic.text',
            type: 'comment',
            clickToAdd: 'addDiagnostic',
            clickToDel: '[ng-click="removeDiagnostic(diagnostic.id)"]',
            repeater: 'diagnostic in cheque.diagnostics'},
        {name:'note.text',
            type: 'comment',
            clickToAdd: 'addNote',
            clickToDel: '[ng-click="removeNote(note.id)"]',
            repeater: 'note in cheque.notes'},
        {name:'cheque.nameOfCustomer', type: 'text'},
        {name:'cheque.nameOfProduct', type: 'text'},
        {name:'cheque.model', type: 'text'},
        {name:'cheque.serialNumber', type: 'text'},
        {name:'cheque.malfunction', type: 'text'},
        {name:'cheque.specialNotes', type: 'text'},
        {name:'cheque.purchaserName', type: 'text'},
        {name:'cheque.address', type: 'text'},
        {name:'cheque.masterName', type: 'text'},
        {name:'cheque.inspectorName', type: 'text'},
        {name:'$mdChipsCtrl.chipBuffer', type: 'chip'}
    ];

    fields.forEach(function(field) {
        it('should add to cheque field ' + field.name + ' testable values and then check that save successfully, ' +
            'after all clean added part from cheque field'
            , function() {
                addTestSampleTo(field);
                element(by.id('edit')).click();
                browser.waitForAngular();

                browser.get('/#/cheques/1');
                checkTestSampleIn(field);
                browser.waitForAngular();

                cleanTestSampleFrom(field);
                element(by.id('edit')).click();
                browser.waitForAngular();
        });
    });
});