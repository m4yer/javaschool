app.controller("tripFindCtrl", function($scope, $http) {

    $('.clockpicker').clockpicker();
    $("#datepicker_since").datepicker();
    $("#datepicker_until").datepicker();
    // $("#datepicker_since").on("change", function (value) {
    //
    // });

    // Requesting all stations
    $http({
        url: "/station/get/all",
        method: "GET"
    }).then(function success(response) {
        $scope.stations = response.data;
    });

    $scope.getTrips = function() {
        var since = $("#datepicker_since").val().split('/').join('-') + ' ' + $("#clockpicker_since").val();
        var until = $("#datepicker_until").val().split('/').join('-') + ' ' + $("#clockpicker_until").val();
        $http({
            url: "/user/trip/find/",
            method: "POST",
            params: {
                stationFrom: $scope.stationFrom,
                stationTo: $scope.stationTo,
                dateStart: since,
                dateEnd: until
            }
        }).then(function success(response) {
            console.log(response.data);
            $scope.trips = response.data;
        });
    };

    $scope.changeSomeProps = function () {
        $scope.trips[0].id = 50000;
        console.log('An updated trips: ' + $scope.trips);
    };

});