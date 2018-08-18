app.controller("indexManageCtrl", function($scope, $http) {

    $http({
        url: "/station/get/list/title",
        method: "GET"
    }).then(function success(response) {
        var list = response.data;
        var awesompleteFrom = new Awesomplete(document.querySelector(".autocomplete-from input"),{
            list: list,
            minChars: 0,
            maxItems: 4
        });
        $('#awesomplete-from').on('focus', function() {
            awesompleteFrom.evaluate();
        });
        var awesompleteTo = new Awesomplete(document.querySelector(".autocomplete-to input"),{
            list: list,
            minChars: 0,
            maxItems: 4
        });
        $('#awesomplete-to').on('focus', function() {
            awesompleteTo.evaluate();
        });
        pageLoaded();
    });

});