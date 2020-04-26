(function () {
    "use strict";

    var app = angular.module('App');

    app.component('analogic.clock.component', {
        template: '',
        controllerAs: 'ctrl',
        bindings: {size: '@', color: '@', time: '<'},
        controller: function ($element) {
            var ctrl = this;

            ctrl.$onInit = function () {
                var canvas = angular.element("<canvas width=\""+ctrl.size+"\" height=\""+ctrl.size+"\"></canvas>");
                angular.element(canvas).appendTo($element[0]);
                ctrl.ctx = $element[0].firstChild.getContext("2d");
                drawClock();
            };

            ctrl.$onChange = function () {
                if (ctrl.ctx) {
                    drawClock();
                }
            };

            function drawClock() {
                var radius = ctrl.size / 2;
                ctrl.ctx.translate(radius, radius);
                radius = radius * 0.9;
                drawFace(radius);
                drawNumbers(radius);
                drawTime(radius);
            }
            
            function drawFace(radius) {
                var grad;
                ctrl.ctx.beginPath();
                ctrl.ctx.arc(0, 0, radius, 0, 2 * Math.PI);
                ctrl.ctx.fillStyle = 'white';
                ctrl.ctx.fill();
                grad = ctrl.ctx.createRadialGradient(0, 0, radius * 0.95, 0, 0, radius * 1.05);
                grad.addColorStop(0, ctrl.color);
                grad.addColorStop(0.5, ctrl.color);
                grad.addColorStop(1, ctrl.color);
                ctrl.ctx.strokeStyle = grad;
                ctrl.ctx.lineWidth = radius * 0.1;
                ctrl.ctx.stroke();
                ctrl.ctx.beginPath();
                ctrl.ctx.arc(0, 0, radius * 0.1, 0, 2 * Math.PI);
                ctrl.ctx.fillStyle = ctrl.color;
                ctrl.ctx.fill();
            }

            function drawNumbers(radius) {
                var ang;
                var num;
                ctrl.ctx.font = radius * 0.15 + "px arial";
                ctrl.ctx.textBaseline = "middle";
                ctrl.ctx.textAlign = "center";
                for (num = 1; num < 13; num++) {
                    ang = num * Math.PI / 6;
                    ctrl.ctx.rotate(ang);
                    ctrl.ctx.translate(0, -radius * 0.85);
                    ctrl.ctx.rotate(-ang);
                    ctrl.ctx.fillText(num.toString(), 0, 0);
                    ctrl.ctx.rotate(ang);
                    ctrl.ctx.translate(0, radius * 0.85);
                    ctrl.ctx.rotate(-ang);
                }
            }

            function drawTime(radius) {
                var hour = ctrl.time.getHours();
                var minute = ctrl.time.getMinutes();
                var second = ctrl.time.getSeconds();

                hour = hour % 12;
                hour = (hour * Math.PI / 6) +
                        (minute * Math.PI / (6 * 60)) +
                        (second * Math.PI / (360 * 60));
                drawHand(hour, radius * 0.5, radius * 0.07);

                minute = (minute * Math.PI / 30) + (second * Math.PI / (30 * 60));
                drawHand(minute, radius * 0.8, radius * 0.07);

                second = (second * Math.PI / 30);
                drawHand(second, radius * 0.9, radius * 0.02);
            }

            function drawHand(pos, length, width) {
                ctrl.ctx.beginPath();
                ctrl.ctx.lineWidth = width;
                ctrl.ctx.lineCap = "rectangle";
                ctrl.ctx.moveTo(0, 1);
                ctrl.ctx.rotate(pos);
                ctrl.ctx.lineTo(0, -length);
                ctrl.ctx.stroke();
                ctrl.ctx.rotate(-pos);
            }
        }
    });
})();
