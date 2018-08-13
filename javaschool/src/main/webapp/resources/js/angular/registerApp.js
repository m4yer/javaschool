var app = angular.module("registerApp", ['ngMessages']);
app.directive('ngShow', function() { return function(scope, elem, attrs) { var doShow = scope.$eval(attrs.ngShow); elem[doShow ? 'removeClass' : 'addClass']('ng-hide'); pageLoaded() }; })
var compareTo = function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
};

app.directive("compareTo", compareTo);