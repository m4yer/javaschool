app.controller("trainManageCtrl", function($scope, $http, DTOptionsBuilder, DTColumnBuilder) {

    $http({
        url: "/admin/train/list/get",
        method: "GET"
    }).then(function success(response) {
        $scope.trains = response.data;
        var data = $scope.trains, dtColumns = [];

        dtColumns.push(DTColumnBuilder.newColumn('train_id').withTitle('train_id'));
        dtColumns.push(DTColumnBuilder.newColumn('train_name').withTitle('train_name'));
        dtColumns.push(DTColumnBuilder.newColumn('seats_amount').withTitle('seats_amount'));
        dtColumns.push(DTColumnBuilder.newColumn('carriage_amount').withTitle('carriage_amount'));
        dtColumns.push(DTColumnBuilder.newColumn('speed').withTitle('speed'));
        $scope.dtColumns = dtColumns;

        //create options
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('data', data)
            .withOption('scrollY', '300px')
            .withOption('scrollX', '100%')
            .withOption('scrollCollapse', true);

        //initialize the dataTable
        $scope.columnsReady = true;
        $(".nav-link-trains").css('color', '#FF5A5F');
        pageLoaded();
    });

});