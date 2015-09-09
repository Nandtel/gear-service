describe('filters.scala.html', function() {

    var testingFilterBy = function(name, type) {
        switch(type) {
            case 'date':
                it('should filter by date and check thar items in column equal to filter value', function() {
                    var date = '2000';
                    var comparison = function(year, date, name) {
                        if(name === 'IntroducedFrom')
                                expect(year).not.toBeLessThan(date);
                        else if(name === 'IntroducedTo')
                                expect(year).not.toBeGreaterThan(date);
                    };

                    element(by.binding("'" + name + "'")).click();

                    element(by.model('calendar._year')).sendKeys(
                        protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE);
                    //need to send three last numbers - this is constructive feature of this field
                    element(by.model('calendar._year')).sendKeys(date.slice(1));

                    element(by.buttonText('Ок')).click();
                    browser.waitForAngular();

                    element.all(by.repeater('cheque in filteredCheques').column('cheque.introduced')).getText()
                        .then(function(column) {
                            expect(column.length).toBeGreaterThan(0);
                            column.forEach(function(item) {
                                comparison(item.split('.')[2], date, name);
                            })
                        });
                });
                break;

            case 'input':
                it('should filter by ' + name, function() {
                    element(by.repeater('cheque in filteredCheques').row(0).column('cheque.id'))
                        .getText()
                        .then(function(chequeId) {
                            element(by.model('filter.id')).sendKeys(chequeId);
                            expect(element(by.repeater('cheque in filteredCheques').row(0).column('cheque.id')).getText())
                                .toEqual(chequeId);
                        });
                });
                break;

            case 'autocomplete':
                it('should use autocomplete to fill filter field named ' + name + ' and check that every items in column ' +
                    'equal to filter value', function() {
                    element(by.repeater('cheque in filteredCheques').row(0).column('cheque.' + name))
                        .getText()
                        .then(function (sample) {
                            element(by.xpath('//md-autocomplete[@md-search-text="filter.' + name + '"]'))
                                .element(by.model('$mdAutocompleteCtrl.scope.searchText'))
                                .sendKeys(sample.substring(0, 10));

                            browser.waitForAngular();
                            element(by.repeater('(index, item) in $mdAutocompleteCtrl.matches').row(0)).click();

                            element.all(by.repeater('cheque in filteredCheques').column('cheque.' + name))
                                .getText()
                                .then(function (list) {
                                    list.forEach(function (item) {
                                        expect(item).toEqual(sample);
                                    })
                                });
                        });
                });
                break;

            case 'select':
                it('should check that every items in column is not empty in accordance to select option ' + name,
                    function() {
                        element(by.model('filter.' + name)).click();
                        element.all(by.css('md-option')).first().click();

                        element.all(by.repeater('cheque in filteredCheques').column('cheque.' + name)).getText()
                            .then(function(list) {
                                list.forEach(function(item) {
                                    expect(item).not.toEqual('');
                                })
                            });
                });

                it('should check that every items in column is empty in accordance to select option ' + name,
                    function() {
                        element(by.model('filter.' + name)).click();
                        //click to second option to get cheques without current status(guaranty, ready, issued)
                        element.all(by.css('md-option')).get(1).click();

                        element.all(by.repeater('cheque in filteredCheques').column('cheque.' + name)).getText()
                            .then(function(list) {
                                list.forEach(function(item) {
                                    expect(item).toEqual('');
                                })
                            });
                });
                break;
        }
    };

    beforeEach(function() {
        browser.get('/#/filter');
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
        {name: 'guarantee', type: 'select'},
        {name: 'ready', type: 'select'},
        {name: 'issued', type: 'select'}
    ];

    filters.forEach(function(item) {
        testingFilterBy(item.name, item.type);
    });

    it('should fill filter-fields and then clean all filter-fields by clicking button', function() {
        filters.forEach(function(item) {
            if(item.type === 'autocomplete')
                element(by.repeater('cheque in filteredCheques').row(0).column('cheque.' + item.name))
                    .getText()
                    .then(function (sample) {
                        element(by.xpath('//md-autocomplete[@md-search-text="filter.' + item.name + '"]'))
                            .element(by.model('$mdAutocompleteCtrl.scope.searchText'))
                            .sendKeys(sample);
                    });
        });

        expect(element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).count())
            .toEqual(1);

        element(by.css('body')).sendKeys(protractor.Key.ESCAPE); //disable autocomplete
        element(by.css('[ng-click="resetFilter()"]')).click();

        expect(element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).count())
            .toBeGreaterThan(1);
    });

});