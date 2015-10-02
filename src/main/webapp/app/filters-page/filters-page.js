/**
 * Controller MainController handles request from index.html template.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
angular.module("mainModule")
    .directive('filtersPage', function() {
        return {
            restrict: 'E',
            templateUrl: 'app/filters-page/filters-page.html'
        }
    });