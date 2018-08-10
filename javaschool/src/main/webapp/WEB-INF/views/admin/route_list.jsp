<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.css" />"/>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/datatables.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>

<title>RW | Routes</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="routeListApp" ng-controller="routeListCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12" style="overflow-x: auto;">
                <table ng-if="columnsReady" datatable="" dt-option="dtOptions" dt-columns="dtColumns" class="search-trip-results-table">
                    <thead>
                        <tr ng-show="routes.length > 1">
                            <th>Route #</th>
                            <th>Departure</th>
                            <th>Destination</th>
                            <th>Edit</th>
                            <th>Delete</th>
                            <th>Create trip</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="route in routes" ng-if="$index % 2 == 0">
                            <td>{{ route.route_id }}</td>
                            <td>{{ route.stationDto.name }}</td>
                            <td>{{ routes[$index+1].stationDto.name }}</td>
                            <td><a href="/admin/route/edit/{{ route.route_id }}" style="font-size: 12px;">Edit</a></td>
                            <td>Delete</td>
                            <td>
                                <a href="/admin/trip/create/{{ route.route_id }}" style="font-size: 12px;">Create</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/angular/routeListApp.js" />"></script>
<script src="<c:url value="/resources/js/angular/routeListCtrl.js" />"></script>
</body>
</html>
