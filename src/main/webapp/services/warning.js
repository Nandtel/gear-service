angular.module('mainModule')
    .factory('warning', ['$rootScope', '$http', '$state', '$mdDialog',
        function($rootScope, $http, $state, $mdDialog) {

            var warning = {

                showConfirmUnsavedChanges: function(event) {

                    var confirmUnsavedChanges = $mdDialog.confirm()
                        .title('Would you like to continue without saving changes?')
                        .content('You can specify some description text in here.')
                        .ariaLabel('unsaved changes warning')
                        .targetEvent(undefined)
                        .ok('Continue')
                        .cancel('Stay');

                    return $mdDialog.show(confirmUnsavedChanges);

                },

                showConfirmDeletePayment: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title('Would you like to delete payment?')
                        .content('You can specify some description text in here.')
                        .ariaLabel('delete payment warning')
                        .targetEvent(event)
                        .ok('Delete')
                        .cancel('No');

                    return $mdDialog.show(confirmDelete);

                },

                showConfirmDeleteComment: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title('Would you like to delete comment?')
                        .content('You can specify some description text in here.')
                        .ariaLabel('delete comment warning')
                        .targetEvent(event)
                        .ok('Delete')
                        .cancel('No');

                    return $mdDialog.show(confirmDelete);

                },

                showConfirmDeleteCheque: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title('Would you like to delete cheque?')
                        .content('You can specify some description text in here.')
                        .ariaLabel('delete cheque warning')
                        .targetEvent(event)
                        .ok('Delete')
                        .cancel('No');

                    return $mdDialog.show(confirmDelete);

                },

                showConfirmDeletePhoto: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title('Would you like to delete photo?')
                        .content('You can specify some description text in here.')
                        .ariaLabel('delete photo warning')
                        .targetEvent(event)
                        .ok('Delete')
                        .cancel('No');

                    return $mdDialog.show(confirmDelete);

                }


            };

            return warning;
        }]);