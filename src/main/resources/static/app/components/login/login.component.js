(function () {
    "use strict";

    var app = angular.module('App');

    app.component('login.component', {
        templateUrl: 'app/components/login/login.component.html',
        controllerAs: 'ctrl',
        controller: function (AuthService, $state, toastr, STATES) {
            var ctrl = this;
            ctrl.credentials = {};
            ctrl.state = 'login';

            ctrl.$onInit = function () {
                ctrl.state = 'login';
                if (!AuthService.isAuthenticated()) {
                    ctrl.state = 'waiting';
                    AuthService.obtainProfile().then(function () {
                        ctrl.state = 'login';
                        $state.go(STATES.HOME);
                    }, function (e) {
                        ctrl.state = 'login';
                    });
                }
            };

            ctrl.login = function () {
                if (ctrl.credentials.login && ctrl.credentials.password) {
                    ctrl.state = 'waiting';
                    AuthService.login({username: ctrl.credentials.login, password: ctrl.credentials.password}).then(function () {
                        $state.go(STATES.HOME);
                    }, function () {
                        ctrl.state = 'login';
                        ctrl.credentials.password = "";
                        toastr.error("Usuario o contraseña no valido", "Error");
                    });
                }
            };
        }});
})();