app.controller("stationScheduleCtrl", function($scope, $http, DTOptionsBuilder) {

    $("#datepicker").datepicker({
        startDate: 'now',
        endDate: '+1m'
    }).on('changeDate', function(e) {
        $scope.dateClicked = true;
        if ($scope.stationWasTyped) $('#searchScheduleButton').prop('disabled', false);
    });

    $("#stationNameInput").keydown(function () {
        $scope.stationWasTyped = true;
        if ($scope.dateClicked) $('#searchScheduleButton').prop('disabled', false);
    });

    // Requesting all stations
    $http({
        url: "/station/get/list/title",
        method: "GET"
    }).then(function success(response) {
        $scope.stations = response.data;
        var list = $scope.stations;
        new Awesomplete(document.querySelector(".autocomplete-station input"),{
            list: list,
            minChars: 1,
            maxItems: 8
        });
    });

    $scope.getSchedule = function () {
        pageLoading();
        $scope.userWasFindingSchedule = true;
        $http({
            url: "/schedule/get-by-date/",
            method: "GET",
            params: {
                stationName: $scope.stationName,
                desiredDate: $('#datepicker').val()
            }
        }).then(function success(response) {
            console.log('$scope.schedules: ', response.data);
            $scope.schedules = response.data;
            pageLoaded();
        }, function (reason) {
            $scope.schedules = [];
            pageLoaded();
        });
    };

    $scope.showRoute = function (event) {
        var chosenTripId = event.target.id.split('trip-').join('');

        $http({
            url: "/route/schedule/get/",
            method: "GET",
            params: {
                tripId: chosenTripId
            }
        }).then(function success(response) {
            console.log('$scope.routeSchedule: ', response.data);
            $scope.routeSchedule = response.data;
            //create options
            $scope.rtOptions = DTOptionsBuilder.newOptions()
                .withOption('scrollY', '300px')
                .withOption('scrollX', '100%')
                .withOption('scrollCollapse', true);

            //initialize the dataTable
            $scope.rtColumnsReady = true;
            openModalForm();
        });

    }

});