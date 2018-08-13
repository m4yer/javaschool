app.controller("stationScheduleCtrl", function($scope, $http, DTOptionsBuilder) {

    $scope.searchDate;

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
        $http({
            url: "/schedule/get/",
            method: "GET",
            params: {
                stationName: $scope.stationName
            }
        }).then(function success(response) {
            console.log('$scope.schedules: ', response.data);
            $scope.schedules = response.data;
        });
    };

    $scope.showRoute = function (event) {
        var chosenTripId = event.target.id.split('trip-').join('');

        $http({
            url: "/admin/schedule/get/",
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