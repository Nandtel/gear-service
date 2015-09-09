describe('table title ordering in filter.scala.html', function() {

    function sortAlgorithmFor(type) {
        if(type === 'number')
            return function(a, b) {return parseInt(a) - parseInt(b)};
        else if(type === 'date')
            return function(a, b) {
                if(a === '') return 1;
                if(b === '') return -1;
                var arrA = a.split('.');
                var dateA = new Date(arrA[2], arrA[1], arrA[0]);
                var arrB = b.split('.');
                var dateB = new Date(arrB[2], arrB[1], arrB[0]);
                return dateA.valueOf() - dateB.valueOf();
            };
        else if(type === 'string')
            return null;
    }

    var checkOrderBy = function(column, type, order) {
        browser.waitForAngular();
        element.all(by.repeater('cheque in filteredCheques').column(column)).getText()
            .then(function(rows){
                expect(rows.length).toBeGreaterThan(2);

                var sorted = rows.slice().sort(sortAlgorithmFor(type));
                if(order === 'descending')
                    sorted.reverse();

                expect(sorted).toEqual(rows);
            });
    };

    beforeAll(function() {
        browser.get('/#/filter');
        browser.waitForAngular();
    });

    var columns = [
        {column: 'cheque.id', type: 'number'},
        {column: 'cheque.nameOfCustomer', type: 'string'},
        {column: 'cheque.introduced', type: 'date'},
        {column: 'cheque.nameOfProduct', type: 'string'},
        {column: 'cheque.model', type: 'string'},
        {column: 'cheque.serialNumber', type: 'string'},
        {column: 'cheque.purchaserName', type: 'string'},
        {column: 'cheque.inspectorName', type: 'string'},
        {column: 'cheque.masterName', type: 'string'},
        {column: 'cheque.guarantee', type: 'date'},
        {column: 'cheque.ready', type: 'date'},
        {column: 'cheque.issued', type: 'date'}
    ];

    columns.forEach(function(item, i) {
        it('should be sorted ' + item.column + ' by ascending', function() {
            element.all(by.css('tr th')).get(i).click();
            checkOrderBy(item.column, item.type, 'ascending');
        });

        it('should be sorted ' + item.column + ' by descending', function() {
            element.all(by.css('tr th')).get(i).click();
            checkOrderBy(item.column, item.type, 'descending');
        });
    });

});