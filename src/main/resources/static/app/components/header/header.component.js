(function () {
    "use strict";

    var app = angular.module('App');

    app.component('header.component', {
        templateUrl: 'app/components/header/header.component.html',
        controllerAs: 'ctrl',
        controller: function (AuthService, $state, STATES) {
            var ctrl = this;
            ctrl.state = 'waiting';
            ctrl.user = {};

            ctrl.$onInit = function () {
                ctrl.user = AuthService.getUser();
                ctrl.authenticated = AuthService.isAuthenticated();
                ctrl.state = 'ready';
            };

            ctrl.logout = function () {
                AuthService.logout();
                $state.go(STATES.LOGIN);
            };
        }});
})();
