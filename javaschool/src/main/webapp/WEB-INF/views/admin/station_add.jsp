<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form.css" />"/>
<title>RW | New station</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="stationAddApp">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="brand-form-wrapper mx-auto">
                    <div class="form-header">
                        <span class="header-caption">NEW STATION</span>
                    </div>
                    <div class="form-content text-left">

                        <form:form modelAttribute="station" action="/admin/station/add" method="post" name="stationAddForm">

                            <!-- Title check -->
                            <div ngShow ng-show="stationAddForm.name.$error" ng-messages="stationAddForm.name.$error">
                                <div class="input-warning" ng-message="required">Field is required</div>
                                <div class="input-warning" ng-message="minlength">At least 4 symbols</div>
                            </div>
                            <div ngShow ng-show="stationAddForm.name.$error" ng-messages="stationAddForm.name">
                                <div class="input-success" ng-message="$valid">&#10004;</div>
                            </div>
                            <label for="name">Title</label>
                            <form:input path="name" type="text" placeholder="Title" name="name" id="name" autocomplete="false" ng-model="name" ng-minlength="4" required="required"/>

                            <!-- Latitude check -->
                            <div ngShow ng-show="stationAddForm.latitude.$error" ng-messages="stationAddForm.latitude.$error">
                                <div class="input-warning" ng-message="required">Field is required</div>
                                <div class="input-warning" ng-message="pattern">Invalid data</div>
                            </div>
                            <div ngShow ng-show="stationAddForm.latitude.$error" ng-messages="stationAddForm.latitude">
                                <div class="input-success" ng-message="$valid">&#10004;</div>
                            </div>
                            <label for="latitude">Latitude</label>
                            <form:input pattern="[0-9]+([\.,][0-9]+)?" path="latitude" type="text" placeholder="Latitude" name="latitude" ng-model="latitude" id="latitude" autocomplete="false" required="required"/>

                            <!-- Longitude check -->
                            <div ngShow ng-show="stationAddForm.longitude.$error" ng-messages="stationAddForm.longitude.$error">
                                <div class="input-warning" ng-message="required">Field is required</div>
                                <div class="input-warning" ng-message="pattern">Invalid data</div>
                            </div>
                            <div ngShow ng-show="stationAddForm.longitude.$error" ng-messages="stationAddForm.longitude">
                                <div class="input-success" ng-message="$valid">&#10004;</div>
                            </div>
                            <label for="longitude">Longitude</label>
                            <form:input pattern="[0-9]+([\.,][0-9]+)?" path="longitude" type="text" placeholder="Longitude" name="longitude" id="longitude" autocomplete="false" ng-model="longitude" required="required"/>
                            <div class="button-wrapper text-center">
                                <button disabled ng-disabled="!stationAddForm.$valid" class="brand-pink-button">Add station</button>
                            </div>
                        </form:form>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-messages.min.js" />"></script>
<script>
    var app = angular.module("stationAddApp", ['ngMessages']);
    app.directive('ngShow', function() { return function(scope, elem, attrs) { pageLoaded(); var doShow = scope.$eval(attrs.ngShow); elem[doShow ? 'removeClass' : 'addClass']('ng-hide'); }; })
</script>
<script>
    $(".nav-link-stations").css('color', '#FF5A5F');
</script>
</body>
</html>
