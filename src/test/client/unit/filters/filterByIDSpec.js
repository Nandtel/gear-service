describe('Test filterByID in mainModule', function() {
    var $filter;

    beforeEach(function () {
        module('mainModule');

        inject(function (_$filter_) {
            $filter = _$filter_;
        });
    });

    it('filterByID should return values filtered by ID', function() {
        expect($filter('filterByID')([{'id': 1, 'name': 'Bob'}, {'id': 2, 'name': 'Kristin'}], 1))
            .toEqual([{'id': 1, 'name': 'Bob'}]);
    });

    it('filterByID should return all values if have no incoming parameters', function() {
        expect($filter('filterByID')([{'id': 1, 'name': 'Bob'}, {'id': 2, 'name': 'Kristin'}]))
            .toEqual([{'id': 1, 'name': 'Bob'}, {'id': 2, 'name': 'Kristin'}]);
    });

});