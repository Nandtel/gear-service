describe('create.scala.html', function() {

    var fillChequeFields = function() {
        var fields = [
            {field: 'cheque.nameOfCustomer', sample: 'Череванченко Екатерина Павловна'},
            {field:'cheque.repairPeriod', sample: ''},
            {field:'cheque.nameOfProduct', sample: 'ГТ производителя'},
            {field:'cheque.model', sample: 'ASUS T100TA-DK003H'},
            {field:'cheque.serialNumber', sample: '89798 8783 324235'},
            {field:'cheque.malfunction', sample: 'Не работает'},
            {field:'cheque.specialNotes', sample: 'Есть проблемы'},
            {field:'cheque.purchaserName', sample: 'Колос Инна Николаевна'},
            {field:'cheque.address', sample: 'Тестируемый адрес'},
            {field:'cheque.phone', sample: '+38(999)999 ‒  99 ‒  99'},
            {field:'cheque.email', sample: 'mail@mail.ma'},
            {field:'cheque.masterName', sample: 'Калиниченко Игорь Анатольевич'},
            {field:'cheque.inspectorName', sample: ''},
            {field:'$mdChipsCtrl.chipBuffer', sample: 'ГТ производителя' + protractor.Key.ENTER},
            {field:'diagnostic.text', sample: 'Проведена диагностика'},
            {field:'note.text', sample: 'Принят в ремонт'}
        ];

        fields.forEach(function(item) {
           element(by.model(item.field)).sendKeys(item.sample);
        });
    };

    it('should check create-button: when cheque-field has no data in, button should be disabled', function() {
        expect(element(by.id('create')).getAttribute('disabled')).toEqual('true');
    });

    it('should check create-button: when cheque-field has data in, button should be active', function() {
        fillChequeFields();
        expect(element(by.id('create')).getAttribute('disabled')).toBeNull();
    });

    it('should create cheque and then check that count of pages in filter page increment by one, then check that fields' +
        'of new check is equals to input, then delete new check by button in edit form', function() {
        browser.get('/#/filter');
        element.all(by.css('md-data-table-pagination div span')).get(0).getText().then(function(text) {
            var now = new Date();
            var countCheques = parseInt(text.charAt(9)); // 9 is position in string of total count of cheques

            browser.get('/#/create');
            fillChequeFields();
            element(by.id('create')).click();
            browser.waitForAngular();

            browser.get('#/filter');
            element.all(by.css('table tbody tr')).first().all(by.css('td')).getText()
                .then(function(answer) {
                    answer.splice(0, 1); // delete first column, because Id is volatile
                    expect(answer).toEqual([ 'Череванченко Екатерина Павловна',
                        (now.getDate() < 10 ? '0' : '') +  now.getDate() + '.' + (now.getMonth() + 1 < 10 ? '0' : '')
                        + (now.getMonth() + 1) + '.' + now.getFullYear(), 'ГТ производителя',
                        'ASUS T100TA-DK003H', '89798 8783 324235', 'Колос Инна Николаевна', 'Администратор',
                        'Калиниченко Игорь Анатольевич', '', '', '' ]);
                });
            element.all(by.css('md-data-table-pagination div span')).get(0).getText().then(function(text) {
                expect(parseInt(text.charAt(9))).toEqual(countCheques + 1);
            });
        });

        //delete cheque
        element.all(by.css('table tbody tr')).first().click();
        element(by.css('md-toolbar button.delete-cheque')).click();
    });



});