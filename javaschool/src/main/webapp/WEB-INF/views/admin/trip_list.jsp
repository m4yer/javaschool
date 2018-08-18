<%@ include file="../includes.jsp" %>
<html>
<link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-clockpicker.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css" />"/>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/datatables.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>
<style>
    .clockpicker-modal-form {
        width: 68px;
    }
    .save-button-modal-form {
        font-size: 12px;
    }
    @media (max-width: 802px) {
        .clockpicker-modal-form {
            width: 60px;
        }
    }
    @media (max-width: 666px) {
        .clockpicker-modal-form {
            font-size: 10px;
            width: 52px;
        }
    }
</style>
<title>RW | Trips</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="tripManageApp" ng-controller="tripManageCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12" style="overflow-x: auto;">
                <table ng-if="columnsReady" datatable="" dt-options="dtOptions" class="search-trip-results-table">
                    <thead>
                        <tr ng-show="trips.length > 0">
                            <th>Train #</th>
                            <th>Route #</th>
                            <th>Departure</th>
                            <th>Arrival</th>
                            <th>Passengers</th>
                            <th>Late</th>
                            <th>Status</th>
                            <th>Cancel</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="trip in trips" trip-row="">
                            <td>{{ trip.trainDto.id }}</td>
                            <td>{{ trip.route_id }}</td>
                            <td>{{ trip.start_time.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</td>
                            <td>{{ trip.arrival_time.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</td>
                            <td>
                                <a class="brand-pink-button" href="/admin/trip/passengers/?tripId={{ trip.id }}&carriageNum=1" style="font-size: 12px;">Passengers</a>
                            </td>
                            <td style="font-size: 12px;">
                                <button id="{{ trip.id }}" class="brand-pink-button" ng-if="trip.active" ng-click="manageLateness($event)">Manage</button>
                                <button id="{{ trip.id }}" class="brand-pink-button" disabled ng-if="!trip.active">Manage</button>
                            </td>
                            <td>
                                <%--<font color="#7CFC00" ng-if="trip.active">&#10004;</font>--%>
                                <%--<font color="red" ng-if="!trip.active">&#10008;</font>--%>
                                <span style="font-weight: 700;" ng-if="trip.active">Active</span>
                                <span style="font-weight: 700;" ng-if="!trip.active">Cancelled</span>

                            </td>
                            <td style="font-size: 12px;">
                                <form action="/admin/trip/cancel" method="post" style="margin: 0;">
                                    <input type="hidden" name="tripId" value="{{ trip.id }}" />
                                    <button id="trip{{ trip.id }}" class="brand-pink-button" ng-if="trip.active" onclick="pageLoading()">Cancel</button>
                                </form>
                                <button class="brand-pink-button" disabled ng-if="!trip.active">Cancel</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Schedule Modal Form -->
    <div id="brand-form-modal" class="brand-form-modal">

        <!-- Modal content -->
        <div class="brand-form-modal-content">
            <div class="brand-form-modal-header">
                <span class="close">&times;</span>
                <span class="caption">Time management</span>
            </div>
            <div class="brand-form-modal-body">

                <table id="scheduleTable" datatable="" ng-if="tmColumnsReady" dt-options="tmOptions"  class="search-trip-results-table" >
                    <thead>
                    <tr ng-show="schedules.length > 0">
                        <th>Station</th>
                        <th>Arrival</th>
                        <th>Time stop</th>
                        <th>Departure</th>
                        <th>Late</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="schedule in schedules">
                        <td>{{ schedule.stationDto.name }}</td>
                        <td>
                            <span ng-if="schedule.time_arrival">{{ (schedule.time_arrival.epochSecond + getLate($index)) * 1000 | date:'dd/MM/yyyy HH:mm' }}</span>
                            <font color="#FF5A5F" style="font-weight: 700;" ng-if="!schedule.time_arrival">Start</font>
                        </td>
                        <td>{{ schedule.time_stop ? (schedule.time_stop.hour < 10 ? '0' + schedule.time_stop.hour : schedule.time_stop.hour ) + ':' + (schedule.time_stop.minute < 10 ? '0' + schedule.time_stop.minute : schedule.time_stop.minute) : '-'}}</td>
                        <td>
                            <span ng-if="schedule.time_departure">{{ (schedule.time_departure.epochSecond + getLate($index)) * 1000  | date:'dd/MM/yyyy HH:mm' }}</span>
                            <font color="#FF5A5F" style="font-weight: 700;" ng-if="!schedule.time_departure">Finish</font>
                        </td>
                        <td>

                            <div class="input-group" clockpicker ng-attr-id="schedule{{ schedule.id }}">

                                <div class="input-group-prepend">
                                    <div class="clockpicker text-center">
                                        <input id="timepicker" type="text" class="form-control clockpicker-border-0 clockpicker-modal-form" value="{{ (schedule.time_late.hour < 10 ? '0' + schedule.time_late.hour : schedule.time_late.hour ) + ':' + (schedule.time_late.minute < 10 ? '0' + schedule.time_late.minute : schedule.time_late.minute)}}" readonly style="background-color: white; border-radius: 0;font-size: 12px;border-bottom-left-radius: 2px;border-top-left-radius: 2px;" />
                                    </div>
                                </div>

                                <button type="submit" class="input-group-append brand-pink-button save-button-modal-form">
                                    <span class="fa fa-save fa-lg"></span>
                                </button>

                            </div>

                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </div>

    </div>

</div>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-clockpicker.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/brand-form-modal.js" />"></script>
<script>
    $(".nav-link-trips").css('color', '#FF5A5F');
</script>
<script src="<c:url value="/resources/js/angular/tripManageApp.js" />"></script>
<script src="<c:url value="/resources/js/angular/tripManageCtrl.js" />"></script>
</body>
</html>
