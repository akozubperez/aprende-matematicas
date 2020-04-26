(function () {
    "use strict";

    var app = angular.module('App');

    app.component('title.component', {
        templateUrl: 'app/components/title/title.component.html',
        controllerAs: 'ctrl',
        bindings: {text: '@'},
        controller: function () {}});
})();
