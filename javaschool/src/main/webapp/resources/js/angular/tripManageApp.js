var app = angular.module("tripManageApp", ['datatables']);

var counter = 0;
var clickedManageButton;

var tripRow = [];

app.directive('clockpicker', ['$http', '$timeout', function($http, $timeout){

    return {
        link: function(scope, elm, attr){
            if (counter < 3) {
                elm.clockpicker({
                    placement: 'left',
                    align: 'right',
                    donetext: 'Ok',
                    autoclose: true
                });
            } else {
                elm.clockpicker({
                    placement: 'top',
                    align: 'left',
                    donetext: 'Ok',
                    autoclose: true
                });
            }
            console.log(elm);
            console.log(elm[0]['children'][1]);
            elm[0]['children'][1].onclick = function() {

                var scheduleId = attr.id.split('schedule').join('');
                var time_late = elm[0]['children'][0]['children'][0]['children'][0]['value'];
                console.log('$http.post:');
                console.log('scheduleId: ', scheduleId);
                console.log('time_late: ', time_late);

                $http({
                    url: "/admin/schedule/late",
                    method: "POST",
                    params: {
                        scheduleId: scheduleId,
                        time_late: time_late
                    }
                }).then(function () {
                    pageLoading();
                    modal.css('display', 'none');

                    $timeout(function () {
                        angular.element(document.getElementById(clickedManageButton)).trigger('click');
                    });


                });

            };
            console.log('counter: ', counter);
            counter++;
        }
    }
}]);

app.directive('tripRow', ['$http', function ($http) {

    return {
        link: function(scope, elm, attr){
            console.log('elm: ', elm);
            var arrivalCellValue = elm[0]['cells'][3]['innerHTML'];
            console.log('4rd column value: ', arrivalCellValue);
            tripRow.push(elm);
        }
    }

}]);