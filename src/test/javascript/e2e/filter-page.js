describe('checking functionality of filter page', function() {

    beforeEach(function() {
        browser.get('/filter');
        browser.executeScript("arguments[0].setAttribute('style', 'zoom:100%')", element(by.css('body')));
        browser.waitForAngular();
    });

    var filters = [
        {name: 'IntroducedFrom', type: 'date'},
        {name: 'IntroducedTo', type: 'date'},
        {name: 'id', type: 'input'},
        {name: 'nameOfCustomer', type: 'autocomplete'},
        {name: 'nameOfProduct', type: 'autocomplete'},
        {name: 'model', type: 'autocomplete'},
        {name: 'serialNumber', type: 'autocomplete'},
        {name: 'purchaserName', type: 'autocomplete'},
        {name: 'inspectorName', type: 'autocomplete'},
        {name: 'masterName', type: 'autocomplete'},
        {name: 'warrantyStatus', type: 'select', column: 'warrantyDate'},
        {name: 'readyStatus', type: 'select', column: 'readyDate'},
        {name: 'returnedToClientStatus', type: 'select', column: 'returnedToClientDate'},
        {name: 'paidStatus', type: 'select'},
        {name: 'withoutRepair', type: 'select'}
    ];

    var testingFilterBy = function(name, type) {
        switch(type) {
            case 'select':
                var column = arguments[2];

                var currentCheckingFunc;
                var optionTrue;
                var optionFalse;

                var checkingFunc = {
                    withColumn: function (callback) {
                        element.all(by.repeater('cheque in $root.page.content')
                            .column('cheque.' + column)).getText()
                            .then(function(list) {
                                list.forEach(function(item) {
                                    callback(item);
                                })
                            });
                    },
                    withoutColumn: function (callback) {
                        var items = element.all(by.repeater('cheque in $root.page.content'));
                        callback(items);
                    }
                };

                if(column !== undefined) {
                    currentCheckingFunc = checkingFunc.withColumn;
                    optionTrue = function (item) {expect(item).not.toEqual('');};
                    optionFalse = function (item) {expect(item).toEqual('');};
                }
                else {
                    currentCheckingFunc = checkingFunc.withoutColumn;
                    optionTrue = function (items) {expect(items.count()).not.toEqual(0);};
                    optionFalse = function (items) {expect(items.count()).toEqual(0);};
                }

                it('should check that every items in column is not empty in accordance to select option ' + name,
                    function() {
                        element(by.model('$root.filter.' + name)).click();
                        browser.waitForAngular();
                        element(by.css('div.md-select-menu-container.md-active md-option[value=' + true + ']')).click();
                        element(by.id('find')).click();
                        currentCheckingFunc(optionTrue);
                    });

                it('should check that every items in column is empty in accordance to select option ' + name,
                    function() {
                        element(by.model('$root.filter.' + name)).click();
                        browser.waitForAngular();
                        element(by.css('div.md-select-menu-container.md-active md-option[value=' + false + ']')).click();
                        element(by.id('find')).click();
                        currentCheckingFunc(optionFalse);
                    });

                break;
        }
    };

    it('should check that clicking of find button shows list of cheques', function() {
        element.all(by.id('find')).first().click();
        element.all(by.repeater('cheque in $root.page.content').column('cheque.id')).count()
            .then(function(size) {
                expect(size).toEqual(15);
            });
    });

    filters.forEach(function(item) {
        testingFilterBy(item.name, item.type, item.column);
    });

});