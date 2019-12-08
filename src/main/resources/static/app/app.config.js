(function () {
    "use strict";

    var app = angular.module('App');

    app.factory('httpRequestInterceptor', function ($q, $injector, STATES) {
        return {
            'responseError': function (rejection) {
                var $state = $injector.get('$state');
                if ($state.current.name !== 'login' && rejection.status === 403) {
                    var AuthService = $injector.get('AuthService');
                    AuthService.onError($state.current.name);
                    $state.go(STATES.LOGIN);
                }
                return $q.reject(rejection);
            }
        };
    });
})();