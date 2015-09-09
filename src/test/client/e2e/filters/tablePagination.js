describe('table pagination in filter.scala.html', function() {

    beforeEach(function() {
        browser.get('/#/filter');
        browser.waitForAngular();
    });

    it('should establish select to 20 cheques per page', function() {
        element(by.css('md-data-table-pagination md-select-value')).click();
        element(by.xpath('//md-option[@value="20"]')).click();

        expect(element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).count()).toEqual(20);
    });

    it('should leaf through pages with next and previous buttons', function() {
        element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).getText()
            .then(function(firstIds) {
                element(by.css('[ng-click="next()"]')).click().click();
                browser.waitForAngular();
                element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).getText()
                    .then(function(secondIds) {
                        expect(secondIds).not.toEqual(firstIds);
                    });

                element(by.css('[ng-click="previous()"]')).click().click();
                browser.waitForAngular();
                element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).getText()
                    .then(function(secondIds) {
                        expect(secondIds).toEqual(firstIds);
                    });
            });
    });

    it('should leaf through pages with last and first buttons', function() {
        element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).getText()
            .then(function(firstIds) {
                element(by.css('[ng-click="last()"]')).click().click();
                browser.waitForAngular();
                element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).getText()
                    .then(function(secondIds) {
                        expect(secondIds).not.toEqual(firstIds);
                    });

                element(by.css('[ng-click="first()"]')).click().click();
                browser.waitForAngular();
                element.all(by.repeater('cheque in filteredCheques').column('cheque.id')).getText()
                    .then(function(secondIds) {
                        expect(secondIds).toEqual(firstIds);
                    });
            });
    });
});