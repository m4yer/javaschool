app.controller("routeListCtrl", function($scope, $http, DTOptionsBuilder, DTColumnBuilder) {

    $http({
        url: "/route/list/get",
        method: "GET"
    }).then(function success(response) {
        $scope.routes = response.data;
        console.log('$scope.routes: ', $scope.routes);
        var data = response.data, dtColumns = [];

        dtColumns.push(DTColumnBuilder.newColumn('route_id').withTitle('route_id'));
        dtColumns.push(DTColumnBuilder.newColumn('name').withTitle('name'));
        dtColumns.push(DTColumnBuilder.newColumn('name').withTitle('name'));
        dtColumns.push(DTColumnBuilder.newColumn('edit').withTitle('edit'));
        dtColumns.push(DTColumnBuilder.newColumn('delete').withTitle('delete'));
        dtColumns.push(DTColumnBuilder.newColumn('create_trip').withTitle('create_trip'));
        $scope.dtColumns = dtColumns;

        //create options
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('scrollY', '300px')
            .withOption('scrollX', '100%')
            .withOption('scrollCollapse', true);

        //initialize the dataTable
        $scope.columnsReady = true;
        $(".nav-link-routes").css('color', '#FF5A5F');
        pageLoaded();
    });

});