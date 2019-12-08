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
            var ingredientes = ['Huevos', 'Mantequilla', 'Azúcar', 'Yogur', 'Harina', 'Limón', 'Levadura'];
            ctrl.precios = {};
 
            ctrl.$onInit = function () {
                ingredientes.forEach(function (i){
                    ctrl.precios[i] = Math.round(Math.random()*10+4);
                });
                ctrl.state = 'ready';
            };

            ctrl.next = function () {
                ctrl.step++;
            };

            ctrl.previous = function () {
                ctrl.step--;
            };
        }});
})();
