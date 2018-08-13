<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ticket-list.css" />"/>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/datatables.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>
<title>RW | My tickets</title>
<style>
    .brand-pink-button {
        font-size: 12px;
    }

    .brand-pink-button i {
        color: #414141;
    }

    .brand-pink-button:hover {
        text-decoration: none;
        color: #414141;
    }
    .span-wrapper {
        display: block;
        margin-top: 9px;
        font-weight: 700;
    }

    .span-number {
        padding: 6px;
        background: #FF5A5F;
        display: inline-block;
        font-size: 14px;
        color: black;
        border-bottom: 2px solid red;
        margin: 0;
        border-top-left-radius: 2px;
        border-bottom-left-radius: 2px;
        min-width: 36px;
    }

    .span-station {
        background: #414141;
        display: inline-block;
        color: white;
        border-bottom: 2px solid black;
        min-width: 140px;
        padding: 6px;
        margin: 0 auto;
        font-size: 14px;
    }

    .span-time {
        background: #414141;
        color: #FF5A5F;
        min-width: 160px;
        padding: 6px;
        display: inline-block;
        border-bottom-right-radius: 2px;
        border-top-right-radius: 2px;
        border-bottom: 2px solid black;
        border-left: 2px solid black;
        font-size: 14px;
    }

    .brand-form-modal-content {
        width: 40%;
        margin-top: 5%;
    }

    @media (max-width: 920px) {
        .brand-form-modal-content {
            width: 52%;
            margin-top: 8%;
        }
    }

    @media (max-width: 708px) {
        .brand-form-modal-content {
            width: 68%;
            margin-top: 8%;
        }
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
                                class="fa fa-download"></i> DOWNLOAD</span></a></span>
                        <a style="margin-right: 12px; margin-top: -4px; padding: 4px 12px; padding-top: 4px;"
                                href="#" class="ticket-line ticket-right brand-pink-button" id="trip-{{ ticket.trip.id }}" ng-click="showRoute($event)" >
                            <i class="fa fa-download"></i> ROUTE</a>

                        <span class="ticket-line"><span class="bold">Carriage</span> \#{{ ticket.carriage_num }}</span>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- Route Modal Form -->
    <div id="brand-form-modal" class="brand-form-modal">

        <!-- Modal content -->
        <div class="brand-form-modal-content">
            <div class="brand-form-modal-header" style="margin-bottom: 12px;">
                <span class="close">&times;</span>
                <span class="caption" style="margin-left: 32px;">Route stations</span>
            </div>
            <div class="brand-form-modal-body">
                <span ng-repeat="schedule in schedules" class="span-wrapper">
                    <span class="span-number">{{ $index + 1}}</span><span class="span-station">{{ schedule.stationDto.name }}</span><span ng-if="!schedule.time_arrival.epochSecond" class="span-time">-</span><span ng-if="schedule.time_arrival.epochSecond" class="span-time">{{ schedule.time_arrival.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</span>
                </span>
            </div>
        </div>

    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/brand-form-modal.js" />"></script>
<script src="<c:url value="/resources/js/angular/ticketListApp.js" />"></script>
<script>
    app.controller("ticketListCtrl", function ($scope, $http, DTOptionsBuilder) {

        $scope.showRoute = function(event) {
            var chosenTripId = event.target.id.split('trip-').join('');
            $http({
                url: "/admin/schedule/get/",
                method: "GET",
                params: {
                    tripId: chosenTripId
                }
            }).then(function success(response) {
                console.log('$scope.schedules: ', response.data);
                $scope.schedules = response.data;
                //create options
                $scope.rtOptions = DTOptionsBuilder.newOptions()
                    .withOption('scrollY', '300px')
                    .withOption('scrollX', '100%')
                    .withOption('scrollCollapse', true);

                //initialize the dataTable
                $scope.rtColumnsReady = true;
                openModalForm();
            });
        };

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
