app.controller("tripManageCtrl", function($scope, $http, DTOptionsBuilder, DTColumnBuilder) {

    $http({
        url: "/admin/trip/list/get",
        method: "GET"
    }).then(function success(response) {
        $scope.trips = response.data;

        //create options
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('autoWidth', true)
            .withOption('scrollX', '100%')
            .withOption('scrollY', '390px')
            .withOption('scrollCollapse', true);

        for (var i = 0; i < $scope.trips.length; i++) {
            closure(i);
        }

        //initialize the dataTable
        $scope.columnsReady = true;

        pageLoaded();
    });

    function closure(i) {
        $http({
            url: "/trip/arrival-time",
            method: "GET",
            params: {
                tripId: $scope.trips[i].id,
                stationTo: 'LAST'
            }
        }).then(function success(response) {
            console.log(response.data);
            console.log($scope.trips[i]);
            $scope.trips[i].arrival_time = response.data;
        });
    }

    $scope.getLate = function(index) {
        var lateness = 0;
        console.log('$scope.schedules: ', $scope.schedules);
        for (var i = 0; i < index; i++) {
            var stringTimeLateForCurrentIndex = $scope.schedules[i]['time_late'];
            var timeParts = stringTimeLateForCurrentIndex.split(':');
            console.log('timeparts: ', timeParts);
            var timeLateForCurrentIndex = (timeParts[0] * 24 * 60) + timeParts[1] * 60;
            console.log('timeLateForCurrentIndex', timeLateForCurrentIndex);
            lateness = lateness + timeLateForCurrentIndex;
        }
        console.log('lateness: ', lateness);
        return lateness;
    };

    $scope.manageLateness = function (event) {

        clickedManageButton = event.target.id;

        counter = 0;
        $scope.tmColumnsReady = false;

        $("#brand-form-modal .dataTables_wrapper").remove();

        pageLoading();
        console.log('Finding schedule for tripId: ', event.target.id);
        $http({
            url: "/route/schedule/get/",
            method: "GET",
            params: {
                tripId: event.target.id
            }
        }).then(function success(response) {
            $scope.schedules = response.data;

            $scope.tmOptions = DTOptionsBuilder.newOptions()
                .withOption('autoWidth', true)
                .withOption('ordering', false)
                .withOption('searching', false);

            $scope.tmColumnsReady = true;

            console.log('manage button clicked!');
            openModalForm();
            pageLoaded();
        });
    };

});
