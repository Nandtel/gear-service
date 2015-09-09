describe('datetimePicker', function() {
    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    var testDatetimePicker = function(name, type, link) {
        var datetimeTitle = capitalizeFirstLetter(name.split('.')[1]);

        it('should change year in ' + name + ' by path: ' + link + ' datetimePicker and then check that year ' +
            'has been changed in button text',
            function() {
            var year = '2000';


            browser.get('/#' + link);
            browser.waitForAngular();

            element(by.binding(datetimeTitle)).click();

            element(by.model('calendar._year')).sendKeys(
                protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE);

            //need to send three last numbers - this is constructive feature of this field
            element(by.model('calendar._year')).sendKeys(year.slice(1));

            element(by.buttonText('Ок')).click();
            browser.waitForAngular();

            element(by.binding(name)).getText().then(function(date) {
                expect(date.split(' ')[2]).toEqual(year);
            });

        });

        it('should change month in ' + name + ' by path: ' + link + ' datetimePicker and then check that month ' +
            'has been changed in button text', function() {
            browser.get('/#' + link);
            browser.waitForAngular();

            element(by.binding(datetimeTitle)).click();

            element(by.model('calendar._month')).element(by.css('[value="number:8"]')).click(); //pick september

            element(by.buttonText('Ок')).click();
            browser.waitForAngular();

            element(by.binding(name)).getText().then(function(date) {
                expect(date.split(' ')[1]).toEqual('сен'); //september
            });

        });

        it('should change month in ' + name + ' by path: ' + link + ' datetimePicker by using arrow-button ' +
            '"Previous Month" and then check that month has been changed in button text', function() {
            browser.get('/#' + link);
            browser.waitForAngular();

            element(by.binding(datetimeTitle)).click();

            element(by.model('calendar._month')).element(by.css('[value="number:9"]')).click(); //pick october
            element(by.css('[aria-label="Previous Month"]')).click();

            element(by.buttonText('Ок')).click();
            browser.waitForAngular();

            element(by.binding(name)).getText().then(function(date) {
                expect(date.split(' ')[1]).toEqual('сен'); //september
            });
        });

        it('should change month in ' + name + ' by path: ' + link + ' datetimePicker by using arrow-button "Next Month" ' +
            'and then check that month has been changed in button text', function() {
            browser.get('/#' + link);
            browser.waitForAngular();

            element(by.binding(datetimeTitle)).click();

            element(by.model('calendar._month')).element(by.css('[value="number:7"]')).click(); //pick august
            element(by.css('[aria-label="Next Month"]')).click();

            element(by.buttonText('Ок')).click();
            browser.waitForAngular();

            element(by.binding(name)).getText().then(function(date) {
                expect(date.split(' ')[1]).toEqual('сен'); //september
            });
        });

        it('should change date in ' + name + ' by path: ' + link + ' datetimePicker and then check that date ' +
            'has been changed in button text', function() {
            browser.get('/#' + link);
            browser.waitForAngular();

            element(by.binding(datetimeTitle)).click();

            element.all(by.css('button.day-cell')).get(14).click(); //set date to 15

            element(by.buttonText('Ок')).click();
            browser.waitForAngular();

            element(by.binding(name)).getText().then(function(date) {
                expect(date.split(' ')[0]).toEqual('15');
            });
        });

        it('should click to close-button to exit without saving changes in ' + name + ' by path: ' + link + ' datepicker',
            function() {
                browser.get('/#' + link);
                browser.waitForAngular();

                element(by.binding(name)).getText().then(function(currentDate) {
                    element(by.binding(datetimeTitle)).click();
                    element.all(by.css('button.day-cell')).get(14).click(); //set date to 15

                    element(by.buttonText('Закрыть')).click();

                    expect(element(by.binding(name)).getText()).toEqual(currentDate);
                });
        });

        it('should click to now-button to change date to current in ' + name + ' by path: ' + link + ' datepicker',
            function() {
            browser.get('/#' + link);
            browser.waitForAngular();

            element(by.binding(datetimeTitle)).click();
            element.all(by.css('button.day-cell')).get(14).click(); //set date to 15
            element(by.buttonText('Ок')).click();

            element(by.binding(datetimeTitle)).click();
            element(by.buttonText('Сейчас')).click();
            element(by.buttonText('Ок')).click();

            expect(element(by.binding(name)).getText()).toMatch(new Date().getFullYear().toString());
        });

        it('should change date for introduced button in ' + name + ' by path: ' + link + ' datepicker', function() {
            element(by.binding(datetimeTitle)).click();
            element.all(by.css('button.day-cell')).get(14).click(); //set date to 15

            element(by.buttonText('Ок')).click();

            expect(element(by.binding(name)).getText())
                .toMatch('15');
        });

        if(type === 'datetime') {
            it('should change time in ' + name + ' by path: ' + link + ' datetimePicker and then check that time ' +
                'has been changed in button text', function() {
                browser.get('/#' + link);
                browser.waitForAngular();

                element(by.binding(datetimeTitle)).click();

                element(by.model('clock._hours')).sendKeys(protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + 20);
                element(by.model('clock._minutes')).sendKeys(protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + 11);

                element(by.buttonText('Ок')).click();
                browser.waitForAngular();

                element(by.binding(name)).getText().then(function(date) {
                    expect(date.split(' ')[4]).toEqual('20:11');
                });
            });

            it('should change time in ' + name + ' by path: ' + link + ' datetimePicker by using increment buttons ' +
                'and then check that time has been changed in button text', function() {
                browser.get('/#' + link);
                browser.waitForAngular();

                element(by.binding(datetimeTitle)).click();

                element(by.model('clock._hours')).sendKeys(protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + 19);
                element(by.model('clock._minutes')).sendKeys(protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + 0);

                element(by.css('[aria-label="Increment Hours"]')).click();
                browser.waitForAngular();
                element(by.css('[aria-label="Increment Minutes"]')).click();
                browser.waitForAngular();

                element(by.buttonText('Ок')).click();
                browser.waitForAngular();

                element(by.binding(name)).getText().then(function(date) {
                    expect(date.split(' ')[4]).toEqual('20:01');
                });
            });

            it('should change time in ' + name + ' by path: ' + link + ' datetimePicker by using decrement buttons ' +
                'and then check that time has been changed in button text', function() {
                browser.get('/#' + link);
                browser.waitForAngular();

                element(by.binding(datetimeTitle)).click();

                element(by.model('clock._hours')).sendKeys(protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + 21);
                element(by.model('clock._minutes')).sendKeys(protractor.Key.BACK_SPACE + protractor.Key.BACK_SPACE + 1);

                element(by.css('[aria-label="Decrement Hours"]')).click();
                browser.waitForAngular();
                element(by.css('[aria-label="Decrement Minutes"]')).click();
                browser.waitForAngular();

                element(by.buttonText('Ок')).click();
                browser.waitForAngular();

                element(by.binding(name)).getText().then(function(date) {
                    expect(date.split(' ')[4]).toEqual('20:00');
                });
            });
        }
    };

    var datetimepickers = [
        {name: 'cheque.introduced', type: 'datetime', link: '/create'},
        {name: 'cheque.guarantee', type: 'date', link: '/create'},
        {name: 'filter.introducedFrom', type: 'datetime', link: '/filter'},
        {name: 'filter.introducedTo', type: 'datetime', link: '/filter'},
        {name: 'cheque.introduced', type: 'datetime', link: '/cheques/1'},
        {name: 'cheque.guarantee', type: 'date', link: '/cheques/1'},
        {name: 'cheque.ready', type: 'date', link: '/cheques/1'},
        {name: 'cheque.issued', type: 'date', link: '/cheques/1'}
    ];

    datetimepickers.forEach(function(item) {
        testDatetimePicker(item.name, item.type, item.link);
    });
});