/**
 * This file contains factory warning to handle warning notifications.
 *
 * @version 1.0
 * @author Dmitry
 * @since 08.02.2017
 */
angular.module('mainModule')
    .factory('warning', function($rootScope, $http, $state, $mdDialog, gettextCatalog) {

            var warning = {

                showConfirmUnsavedChanges: function(event) {

                    var confirmUnsavedChanges = $mdDialog.confirm()
                        .title(gettextCatalog.getString('would you like to continue without saving changes?'))
                        .textContent(gettextCatalog.getString("if you do not save your changes, then all the information will be lost"))
                        .ariaLabel('unsaved changes warning')
                        .targetEvent(undefined)
                        .ok(gettextCatalog.getString('continue'))
                        .cancel(gettextCatalog.getString('cancel'));

                    return $mdDialog.show(confirmUnsavedChanges);

                },

                showConfirmDeletePayment: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title(gettextCatalog.getString('would you like to delete payment?'))
                        .textContent(gettextCatalog.getString('deleted payment can not be recovered'))
                        .ariaLabel('delete payment warning')
                        .targetEvent(event)
                        .ok(gettextCatalog.getString('delete'))
                        .cancel(gettextCatalog.getString('cancel'));

                    return $mdDialog.show(confirmDelete);

                },

                showConfirmDeleteComment: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title(gettextCatalog.getString('would you like to delete comment?'))
                        .textContent(gettextCatalog.getString('deleted comment can not be recovered'))
                        .ariaLabel('delete comment warning')
                        .targetEvent(event)
                        .ok(gettextCatalog.getString('delete'))
                        .cancel(gettextCatalog.getString('cancel'));

                    return $mdDialog.show(confirmDelete);

                },

                showConfirmDeleteCheque: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title(gettextCatalog.getString('would you like to delete cheque?'))
                        .textContent(gettextCatalog.getString('deleted cheque can not be recovered'))
                        .ariaLabel('delete cheque warning')
                        .targetEvent(event)
                        .ok(gettextCatalog.getString('delete'))
                        .cancel(gettextCatalog.getString('cancel'));

                    return $mdDialog.show(confirmDelete);

                },

                showConfirmDeletePhoto: function(event) {

                    var confirmDelete = $mdDialog.confirm()
                        .title(gettextCatalog.getString('would you like to delete photo?'))
                        .textContent(gettextCatalog.getString('deleted photo can not be recovered'))
                        .ariaLabel('delete photo warning')
                        .targetEvent(event)
                        .ok(gettextCatalog.getString('delete'))
                        .cancel(gettextCatalog.getString('cancel'));

                    return $mdDialog.show(confirmDelete);

                },

                showAlertOptimisticLockingException: function() {

                    var alertOptLockExc = $mdDialog.alert()
                        .clickOutsideToClose(true)
                        .title(gettextCatalog.getString('cheque has been changed'))
                        .textContent(gettextCatalog.getString('cheque has been changed by another user, please refresh the cheque and enter your changes again.'))
                        .ariaLabel('optimistic locking exception')
                        .ok(gettextCatalog.getString('ok'));

                    return $mdDialog.show(alertOptLockExc);

                },

                showServerConnectionLostException: function() {

                    var alertSerConnLost = $mdDialog.alert()
                        .clickOutsideToClose(false)
                        .title(gettextCatalog.getString('server connection lost title'))
                        .textContent(gettextCatalog.getString('server connection lost content'))
                        .ariaLabel('server connection lost')
                        .ok(gettextCatalog.getString('ok'));

                    return $mdDialog.show(alertSerConnLost);

                },

                showServerSessionExpired: function() {

                    var alertSerConnLost = $mdDialog.alert()
                        .clickOutsideToClose(false)
                        .title(gettextCatalog.getString('server session expired title'))
                        .textContent(gettextCatalog.getString('server session expired content'))
                        .ariaLabel('server session expired')
                        .ok(gettextCatalog.getString('ok'));

                    return $mdDialog.show(alertSerConnLost);

                }
            };

            return warning;
        });