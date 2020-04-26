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
            ctrl.numSteps = 10;
            ctrl.stepEnabled = [];
            var ingredientes = ['Huevos', 'Harina', 'Mantequilla', 'Yogur', 'Azúcar', 'Levadura', 'Limón'];
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
                ctrl.responses[ctrl.responses.length] = 100 - ctrl.responses[ctrl.responses.length - 1];
                ctrl.responses[2] = undefined;

                var hora = 4 + Math.round(Math.random() * 4);
                var minutos = 15 + Math.round(Math.random() * 3) * 15;
                ctrl.time = new Date(1970, 1, 1, hora, minutos);
                ctrl.responses[ctrl.responses.length] = hora * 60 + minutos + 45;
                
                for (var i = 1; i <= ctrl.numSteps; i++) {
                    ctrl.stepEnabled[i] = ctrl.responses[i] === undefined;
                }
                ctrl.state = 'ready';
            };

            ctrl.check = function (response) {
                if (ctrl.step === 9){
                    response = response.getHours() * 60 + response.getMinutes();
                } 
                if (ctrl.responses[ctrl.step] === response) {
                    ctrl.stepEnabled[ctrl.step] = true;
                    ctrl.responseError[ctrl.step] = false;
                } else {
                    ctrl.responseError[ctrl.step] = true;
                }
            };
        }});
})();
