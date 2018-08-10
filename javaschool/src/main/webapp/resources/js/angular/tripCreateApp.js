var app = angular.module("tripCreateApp", []);

var counter = 1;
var timePickersStopStations = [];

app.directive('clockpicker', ['$http', '$timeout', function($http, $timeout){

    return {
        link: function(scope, elm, attr){
            console.log('elm:', elm);
            console.log('elm[0]:', elm[0]);
            console.log('elm[0][\'childNodes\']', elm[0]['childNodes'][2]);
            console.log('elm[0].children[0]', elm[0]['children'][0]);
            var currentTimepickerValue = elm[0]['children'][0]['value'];
            if (counter < 5) {
                elm.clockpicker({
                    placement: 'left',
                    align: 'top',
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

            timePickersStopStations[counter] = currentTimepickerValue;
            // Timepicker itself
            elm[0]['children'][0].onchange = function(event){
                console.log('trigger for ' + event.target.id);
                var chosenId = event.target.id.split('timepicker').join('');
                timePickersStopStations[chosenId] = event.target.value;
                console.log('timePickersStopStations.lenght: ', timePickersStopStations.length);
                console.log('timePickersStopStations: ', timePickersStopStations);
            };

            console.log('timePickersStopStations.lenght: ', timePickersStopStations.length);
            console.log('timePickersStopStations: ', timePickersStopStations);

            counter++;
        }
    }
}]);