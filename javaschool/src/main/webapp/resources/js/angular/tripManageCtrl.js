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
            .withOption('order', [[2, 'asc']])
            .withOption('scrollCollapse', true);

        for (var i = 0; i < $scope.trips.length; i++) {
            closure(i);
        }
        if ($scope.trips.length == 0) {
            $scope.columnsReady = true;
            pageLoaded();
        }
    });

    function closure(i) {
        pageLoading();
        $http({
            url: "/trip/arrival-time",
            method: "GET",
            params: {
                tripId: $scope.trips[i].id,
                stationTo: 'LAST'
            }
        }).then(function success(response) {
            $scope.trips[i].arrival_time = response.data;
            if (i == $scope.trips.length - 1) {
                $scope.columnsReady = true;
                pageLoaded();
            }
        });
    }

    $scope.getLate = function(index) {
        var lateness = 0;
        for (var i = 0; i < index; i++) {
            var lateForCurrentIndex = $scope.schedules[i]['time_late'];
            var hours = lateForCurrentIndex['hour'];
            var minutes = lateForCurrentIndex['minute'];
            var timeLateForCurrentIndex = (hours * 24 * 60) + minutes * 60;
            lateness = lateness + timeLateForCurrentIndex;
        }
        return lateness;
    };

    $scope.manageLateness = function (event) {
        pageLoading();
        clickedManageButton = event.target.id;

        counter = 0;
        $scope.tmColumnsReady = false;

        $("#brand-form-modal .dataTables_wrapper").remove();

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

            openModalForm();
            pageLoaded();
        });
    };

});
