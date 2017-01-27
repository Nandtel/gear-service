/**
 * Filter filterByID filters array by id value.
 * @param items is array for filtering
 * @param id is value for finding in array
 * @returns array items where id equal with request id
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .filter("filterByID", function() {
        return function (items, id) {

            if(id == null || id == "")
                return items;

            var result = [];

            angular.forEach(items, function (item) {
                if (id == item.id)
                    result.push(item);
            });

            return result;
        }
    });