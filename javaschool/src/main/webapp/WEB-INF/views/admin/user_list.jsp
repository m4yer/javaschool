<%@ include file="../includes.jsp" %>
<html>
<link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.css" />"/>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/datatables.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>

<title>RW | Users</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="userListApp" ng-controller="userListCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12" style="overflow-x: auto;">
                <table ng-if="columnsReady" datatable="" dt-options="dtOptions" class="search-trip-results-table">
                    <thead>
                    <tr ng-show="users.length > 0">
                        <th>#id</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Personal information</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="user in users">
                        <td>{{ user.id }}</td>
                        <td>{{ user.username }}</td>
                        <td>{{ user.email }}</td>
                        <td><button id="user-{{ user.id }}" class="brand-pink-button" style="font-size: 12px; padding: 4px 12px;" ng-click="showUserInfo($event)">USER INFO</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- User Information Modal Form -->
    <div id="brand-form-modal" class="brand-form-modal">

        <!-- Modal content -->
        <div class="brand-form-modal-content" style="width: 50%;">
            <div class="brand-form-modal-header">
                <span class="close">&times;</span>
                <span class="caption">User details</span>
            </div>
            <div class="brand-form-modal-body text-left" style="padding-bottom: 6px; padding-top: 6px;">

                Firstname: {{ userDetails.firstname }} <br>
                Lastname: {{ userDetails.lastname }} <br>
                Birthday: {{ userDetails.birthday | date:'dd/MM/yyyy' }} <br>
                Username: {{ userDetails.username }} <br>
                E-mail: {{ userDetails.email }} <br>

            </div>
        </div>

    </div>

</div>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/brand-form-modal.js" />"></script>
<script src="<c:url value="/resources/js/angular/userListApp.js" />"></script>
<script src="<c:url value="/resources/js/angular/userListCtrl.js" />"></script>
</body>
</html>
