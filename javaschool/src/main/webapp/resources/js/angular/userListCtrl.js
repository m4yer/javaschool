app.controller("userListCtrl", function($scope, $http, DTOptionsBuilder) {

    $http({
        url: "/admin/user/list/get",
        method: "GET"
    }).then(function success(response) {
        $scope.users = response.data;
        console.log('$scope.users: ', $scope.users);

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('autoWidth', true)
            .withOption('scrollX', '100%')
            .withOption('scrollY', '390px')
            .withOption('scrollCollapse', true);

        //initialize the dataTable
        $scope.columnsReady = true;
        $(".nav-link-users").css('color', '#FF5A5F');
        pageLoaded();
    });

    $scope.showUserInfo = function (event) {
        pageLoading();
        var chosenUserId = event.target.id.split('user-').join('');
        for (var i = 0; i < $scope.users.length; i++) {
            if ($scope.users[i]['id'] == chosenUserId) {
                $scope.userDetails = $scope.users[i];
                break;
            }
        }
        openModalForm();
        pageLoaded();
    }

});