(function () {
    "use strict";

    var app = angular.module('App');

    app.service('AuthGuard', function ($state, AuthService, STATES) {
        var self = this;

        self.canActivate = function () {
            var result = false;
            if (!AuthService.isAuthenticated()) {
                $state.go(STATES.LOGIN);
            } else {
                result = true;
            }
            return result;
        };
    });
})();
