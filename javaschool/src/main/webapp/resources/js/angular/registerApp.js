var app = angular.module("registerApp", ['ngMessages']);
app.directive('ngShow', function() { return function(scope, elem, attrs) { var doShow = scope.$eval(attrs.ngShow); elem[doShow ? 'removeClass' : 'addClass']('ng-hide'); pageLoaded() }; })
app.controller("registerCtrl", function($scope, $http) {

    $scope.checkPasswordMatching = function checkPasswordsMatching() {
        $scope.retypeMatch = false;
        var passInput = $('#password');
        var passInputValue = $('#password').val();
        var rePassInput = $('#rePassword');
        var rePassInputValue = $('#rePassword').val();

        var messageValid = $('#retypePasswordValid');
        var doesntMatch = $('#retypePasswordNotMatch');
        var checkFirstInput = $('#retypePasswordCheckFirstInput');
        if (passInput.is(':valid') && passInputValue.length >= 6) {
            checkFirstInput.css('display', 'none');
            if (rePassInputValue == passInputValue) {
                messageValid.css('display', 'block');
                doesntMatch.css('display', 'none');
                $scope.retypeMatch = true;
            } else {
                messageValid.css('display', 'none');
                doesntMatch.css('display', 'block');
                $scope.retypeMatch = false;
            }
        } else {
            messageValid.css('display', 'none');
            doesntMatch.css('display', 'none');
            checkFirstInput.css('display', 'block');
            $scope.retypeMatch = false;
        }
    }

});

