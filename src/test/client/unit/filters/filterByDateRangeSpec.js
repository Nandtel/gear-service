describe('Test filterByDateRange in mainModule', function() {
    var $filter;

    beforeEach(function () {
        module('mainModule');

        inject(function (_$filter_) {
            $filter = _$filter_;
        });
    });

    it('filterByDateRange should return values filtered by date range', function() {
        expect($filter('filterByDateRange')([{'introduced': new Date(2000, 0, 1), 'name': 'Bob'}, {'introduced': new Date(3000, 0, 1), 'name': 'Kristin'}],
            new Date(1980, 0, 1), new Date(2010, 0, 1)))
            .toEqual([{'introduced': new Date(2000, 0, 1), 'name': 'Bob'}]);
    });

    it('filterByDateRange should return values filtered by custom date range if have no incoming parameters', function() {
        expect($filter('filterByDateRange')([{'introduced': new Date(2000, 0, 1), 'name': 'Bob'}, {'introduced': new Date(3000, 0, 1), 'name': 'Kristin'}]))
            .toEqual([{'introduced': new Date(2000, 0, 1), 'name': 'Bob'}, {'introduced': new Date(3000, 0, 1), 'name': 'Kristin'}]);
    });
});