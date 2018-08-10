app.controller("indexManageCtrl", function($scope, $http) {

    $http({
        url: "/station/get/list/title",
        method: "GET"
    }).then(function success(response) {
        var list = response.data;
        new Awesomplete(document.querySelector(".autocomplete-from input"),{
            list: list,
            minChars: 1,
            maxItems: 4
        });
        new Awesomplete(document.querySelector(".autocomplete-to input"),{
            list: list,
            minChars: 1,
            maxItems: 4
        });

        pageLoaded();
    });

});