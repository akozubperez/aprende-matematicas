(function () {
    "use strict";

    var app = angular.module('App');

    app.component('tablas.component', {
        templateUrl: 'app/components/tablas/tablas.component.html',
        controllerAs: 'ctrl',
        controller: function () {
            var ctrl = this;
            ctrl.state = 'waiting';
            
            ctrl.$onInit = function () {
            
                ctrl.state = 'ready';
            };
        }});
})();
