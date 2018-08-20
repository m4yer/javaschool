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
                            <td><a href="/admin/route/delete/{{ route.route_id }}" style="font-size: 12px;">Delete</a></td>
                            <td>
                                <a href="/admin/trip/create/{{ route.route_id }}" style="font-size: 12px;">Create</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Result of route manage operation Modal Form -->
    <div id="brand-form-modal" class="brand-form-modal">
        <!-- Modal content -->
        <div class="brand-form-modal-content" style="width: 50%; margin-top: 6%;">
            <div class="brand-form-modal-header">
                <span class="close">&times;</span>
                <span class="caption">Edit route information</span>
            </div>
            <div class="brand-form-modal-body text-center" style="padding-bottom: 6px; padding-top: 6px;">
                <div id="edit-route-info" style="font-size: 20px; font-weight: 700; padding: 42px 0; font-family: 'Lato', sans-serif;"></div>
            </div>
        </div>
    </div>

</div>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/brand-form-modal.js" />"></script>
<script src="<c:url value="/resources/js/angular/routeListApp.js" />"></script>
<script src="<c:url value="/resources/js/angular/routeListCtrl.js" />"></script>
<script>
    <c:choose>
        <c:when test="${param.editAllowed == false}">
            $("#edit-route-info").html("You cannot edit route " + ${param.routeId} + " because <br><br> there are active trips that use this route.");
            openModalForm();
        </c:when>
        <c:when test="${param.routeDeletedResult == true}">
            $("#edit-route-info").html("Route was successfully deleted.");
            openModalForm();
        </c:when>
        <c:when test="${param.routeDeletedResult == false}">
            $("#edit-route-info").html("You cannot delete route " + ${param.routeId} + " because <br><br> there are active trips that use this route!");
            openModalForm();
        </c:when>
    </c:choose>
</script>
</body>
</html>
