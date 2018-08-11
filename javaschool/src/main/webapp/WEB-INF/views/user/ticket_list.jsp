<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ticket-list.css" />"/>
<title>RW | My tickets</title>
<style>
    .brand-pink-button {
        font-size: 12px;
    }
    .brand-pink-button i {
        color: #414141;
    }
    .brand-pink-button:hover{
        text-decoration: none;
        color: #414141;
    }
</style>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="ticketListApp" ng-controller="ticketListCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12" style="margin-top: 18px;">

                <div ng-repeat="ticket in tickets" class="ticket-wrapper">
                    <div class="ticket-header"><span class="ticket-header-caption">Ticket</span></div>
                    <div class="ticket-body">
                        <span class="ticket-line ticket-right"><span class="bold">Passenger:</span> {{ ticket.user.firstname + ' ' + ticket.user.lastname }}</span>
                        <span class="ticket-line"><span class="bold">From:</span> {{ ticket.station_from.name }}</span>
                        <span class="ticket-line"><span
                                class="bold">Destination:</span> {{ ticket.station_to.name }}</span>
                        <span class="ticket-line"><span class="bold">Seat</span> \#{{ ticket.seat_id }}</span>
                        <span class="ticket-line ticket-right"><a class="brand-pink-button"
                                                                  href="/user/ticket/{{ ticket.id }}.pdf"
                                                                  target="_blank"><span class="bold"><i
                                class="fa fa-download"></i> DETAILS</span></a></span>
                        <span class="ticket-line"><span class="bold">Carriage</span> \#{{ ticket.carriage_num }}</span>
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
<script src="<c:url value="/resources/js/angular/ticketListApp.js" />"></script>
<script>
    app.controller("ticketListCtrl", function ($scope, $http) {
        $http({
            url: "/user/ticket/list/get/",
            method: "GET",
            params: {
                userId: <sec:authentication property="principal.id" />
            }
        }).then(function success(response) {
            $scope.tickets = response.data;
            console.log('$scope.tickets: ', $scope.tickets);
            pageLoaded();
        });
    });
</script>
</body>
</html>
