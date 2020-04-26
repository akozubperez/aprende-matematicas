(function () {
    "use strict";

    var app = angular.module('App');

    app.component('controllers.component', {
        templateUrl: 'app/components/controllers/controllers.component.html',
        controllerAs: 'ctrl',
        bindings: {step: '=', numSteps: '=', stepEnabled: '='},
        controller: function () {
            var ctrl = this;
            ctrl.step = 1;

            ctrl.next = function () {
                ctrl.step++;
            };

            ctrl.previous = function () {
                ctrl.step--;
            };
        }});
})();
