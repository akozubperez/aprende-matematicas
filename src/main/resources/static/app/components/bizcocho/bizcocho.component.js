(function () {
    "use strict";

    var app = angular.module('App');

    app.component('bizcocho.component', {
        templateUrl: 'app/components/bizcocho/bizcocho.component.html',
        controllerAs: 'ctrl',
        controller: function () {
            var ctrl = this;
            ctrl.state = 'waiting';
            ctrl.step = 1;
            ctrl.numStep = 10;
            ctrl.stepEnabled = [];
            var ingredientes = ['Huevos', 'Harina', 'Yogur', 'Azúcar', 'Levadura', 'Mantequilla', 'Limón'];
            ctrl.precios = {};
            ctrl.responseError = {};

            ctrl.$onInit = function () {
                var total = 0;
                ctrl.responses = [];
                ingredientes.filter(function (e) {
                    return e !== 'Limón';
                }).forEach(function (e, i) {
                    ctrl.precios[e] = Math.round(Math.random() * 12 + 4);
                    total += ctrl.precios[e];
                    ctrl.responses[i + 2] = total;
                });
                ctrl.responses[2] = undefined;
                for (var i = 1; i <= ctrl.numStep; i++) {
                    ctrl.stepEnabled[i] = ctrl.responses[i] === undefined;
                }
                ctrl.state = 'ready';
            };

            ctrl.check = function (response) {
                if (ctrl.responses[ctrl.step] === response) {
                    ctrl.stepEnabled[ctrl.step] = true;
                    ctrl.responseError[ctrl.step] = false;
                } else {
                    ctrl.responseError[ctrl.step] = true;
                }
            };
            
            ctrl.next = function () {
                ctrl.step++;
            };

            ctrl.previous = function () {
                ctrl.step--;
            };
        }});
})();
