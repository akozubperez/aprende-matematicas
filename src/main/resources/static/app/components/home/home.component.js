(function () {
    "use strict";

    var app = angular.module('App');

    app.component('home.component', {
        templateUrl: 'app/components/home/home.component.html',
        controllerAs: 'ctrl',
        controller: function () {
            var ctrl = this;
            ctrl.state = 'waiting';

            ctrl.$onInit = function () {
                ctrl.state = 'ready';
            };
        }});
})();
