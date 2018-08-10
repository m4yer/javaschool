app.controller("stationListCtrl", function($scope, $http, DTOptionsBuilder, DTColumnBuilder) {

    $http({
        url: "/admin/station/list/get",
        method: "GET"
    }).then(function success(response) {
        $scope.stations = response.data;
        var data = $scope.stations;
        console.log('$scope.stations: ', $scope.stations);

        var sample = data[0], dtColumns = [];

        //create columns based on first row in dataset
        for (var key in sample) {
            dtColumns.push(DTColumnBuilder.newColumn(key).withTitle(key));
            console.log('column key: ', key);
        }
        $scope.dtColumns = dtColumns;

        //create options
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('data', data)
            .withOption('scrollY', '300px')
            .withOption('scrollX', '100%')
            .withOption('scrollCollapse', true);

        //initialize the dataTable
        $scope.columnsReady = true;
        $(".nav-link-stations").css('color', '#FF5A5F');
        pageLoaded();
    });

});