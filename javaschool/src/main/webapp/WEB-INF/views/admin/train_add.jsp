<%@ include file="../includes.jsp" %>
<html>
<link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form.css" />"/>
<title>RW | New train</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="trainAddApp">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="brand-form-wrapper mx-auto">
                    <div class="form-header">
                        <span class="header-caption">NEW TRAIN</span>
                    </div>
                    <div class="form-content text-left">

                        <form:form modelAttribute="train" action="/admin/train/add" method="post" name="trainAddForm">

                            <!-- Title check -->
                            <div ngShow ng-show="trainAddForm.name.$error" ng-messages="trainAddForm.name.$error">
                                <div class="input-warning" ng-message="required">Field is required</div>
                                <div class="input-warning" ng-message="minlength">At least 3 symbols</div>
                            </div>
                            <div ngShow ng-show="trainAddForm.name.$error" ng-messages="trainAddForm.name">
                                <div class="input-success" ng-message="$valid">&#10004;</div>
                            </div>
                            <label for="name">Title</label>
                            <form:input path="name" type="text" placeholder="Title" name="name" id="name" autocomplete="false" ng-model="name" required="required" ng-minlength="3"/>

                            <!-- Amount check -->
                            <div ngShow ng-show="trainAddForm.carriage_amount.$error" ng-messages="trainAddForm.carriage_amount.$error">
                                <div class="input-warning" ng-message="required">Field is required</div>
                                <div class="input-warning" ng-message="pattern">Invalid data</div>
                            </div>
                            <div ngShow ng-show="trainAddForm.carriage_amount.$error" ng-messages="trainAddForm.carriage_amount">
                                <div class="input-success" ng-message="$valid">&#10004;</div>
                            </div>
                            <label for="carriage_amount">Amount of carriages</label>
                            <form:input pattern="^[1-3]{1}[0-9]{0,1}$|^[1-9]{1}" path="carriage_amount" type="text" placeholder="Amount" name="carriage_amount" id="carriage_amount" autocomplete="false" required="required" ng-model="carriage_amount"/>

                            <!-- Carriages type -->
                            <div class="input-success">&#10004;</div>
                            <label for="carriage_type">Carriage type</label>
                            <form:select path="seats_amount" id="carriage_type">
                                <option value="36">Compartment</option>
                                <option value="54">Platzkart</option>
                                <option value="18">Wagon-lit</option>
                                <option value="66">Seating</option>
                            </form:select>

                            <!-- Speed check -->
                            <div ngShow ng-show="trainAddForm.speed.$error" ng-messages="trainAddForm.speed.$error">
                                <div class="input-warning" ng-message="required">Field is required</div>
                                <div class="input-warning" ng-message="pattern">Invalid data</div>
                            </div>
                            <div ngShow ng-show="trainAddForm.speed.$error" ng-messages="trainAddForm.speed">
                                <div class="input-success" ng-message="$valid">&#10004;</div>
                            </div>
                            <label for="speed">Train speed, <font color="#FF5A5F">km/h</font></label>
                            <form:input pattern="^[1-2]{1}[0-9]{2,2}|^[5-9]{1}[0-9]{1}" path="speed" type="text" placeholder="Speed" name="speed" id="speed" autocomplete="false" required="required" ng-model="speed"/>

                            <div class="button-wrapper text-center">
                                <button disabled ng-disabled="!trainAddForm.$valid" class="brand-pink-button">Add train</button>
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
    var app = angular.module("trainAddApp", ['ngMessages']);
    app.directive('ngShow', function() { return function(scope, elem, attrs) { pageLoaded(); var doShow = scope.$eval(attrs.ngShow); elem[doShow ? 'removeClass' : 'addClass']('ng-hide'); }; })
</script>
<script>
    $(".nav-link-trains").css('color', '#FF5A5F');
</script>
</body>
</html>
