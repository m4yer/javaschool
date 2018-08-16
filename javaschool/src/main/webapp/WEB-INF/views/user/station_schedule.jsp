<%@ include file="../includes.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/magnific-popup.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/awesomplete.css"/>"/>

    <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/datatables.js" />"></script>
    <script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
    <script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>
    <style>
        .input-group .input-place {

            border: 0;
            padding: 12px;

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
    <title>RW | Schedule</title>
</head>
<body id="page-top">

<div id="loader"></div>

<div id="pageContent" class="animate-bottom" ng-app="stationScheduleApp" ng-controller="stationScheduleCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <!-- Search line section -->
    <div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12" style="background-color: rgba(0,0,0,0.7); width: 100%;height: 100px;">
                    <div class="center-vert-horz index-input-line">
                        <table style="margin: 0 auto;">
                            <tr>
                                <td>
                                    <div class="input-group">
                                        <span class="autocomplete-station">
                                            <input type="text" placeholder="Station" class="input-place"
                                                   ng-model="stationName" style="
                                                   border-top-right-radius: 0px;
                                                   border-bottom-right-radius: 0px;" id="stationNameInput"/>
                                        </span>
                                        <input type="text" placeholder="Date" id="datepicker" class="input-date" ng-model="searchDate" autocomplete="off" ng-click="dateWasClicked()" readonly/>
                                        <input type="button" id="searchScheduleButton" class="brand-pink-button" value="Browse schedule" ng-disabled="!stationWasTyped || !dateClicked" ng-click="getSchedule()"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div ng-if="!userWasFindingSchedule">
                <div class="info-page-center">
                    <img src="<c:url value="/resources/img/search-pic.png"/>"/><br>
                    Select station to browse it's schedule!<br><br>
                </div>
            </div>
            <div ng-if="userWasFindingSchedule && schedules.length == 0">
                <div class="info-page-center">
                    <img src="<c:url value="/resources/img/nothing-found.png" />"/><br>
                    Nothing matched your search parameters was found.<br><br>
                </div>
            </div>

            <div class="row">
                <table class="search-trip-results-table">
                    <tr ng-show="schedules.length > 0">
                        <th>Train #</th>
                        <th>Route #</th>
                        <th>Arriving</th>
                        <th>Departing</th>
                    </tr>
                    <tr ng-repeat="schedule in schedules">
                        <td>{{ schedule.tripDto.trainDto.id }}</td>
                        <td>
                            <button style="font-size: 12px; padding: 4px 12px;" class="brand-pink-button"
                                    id="trip-{{ schedule.tripDto.id }}" ng-click="showRoute($event)">ROUTE
                            </button>
                        </td>
                        <td>
                            <span ng-if="schedule.time_arrival">{{ schedule.time_arrival.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</span>
                            <span ng-if="!schedule.time_arrival">Start</span>
                        </td>
                        <td>
                            <span ng-if="schedule.time_departure">{{ schedule.time_departure.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</span>
                            <span ng-if="!schedule.time_departure">Finish</span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <!-- Schedule Modal Form -->
    <div id="brand-form-modal" class="brand-form-modal">

        <!-- Modal content -->
        <div class="brand-form-modal-content">
            <div class="brand-form-modal-header" style="margin-bottom: 12px;">
                <span class="close">&times;</span>
                <span class="caption" style="margin-left: 32px;">Route stations</span>
            </div>
            <div class="brand-form-modal-body">
                <span ng-repeat="item in routeSchedule" class="span-wrapper">
                    <span class="span-number">{{ $index + 1}}</span><span class="span-station">{{ item.stationDto.name }}</span><span ng-if="!item.time_arrival.epochSecond" class="span-time">-</span><span ng-if="item.time_arrival.epochSecond" class="span-time">{{ item.time_arrival.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</span>
                </span>
            </div>
        </div>
    </div>

<!-- div "pageContent" end -->
</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/resources/js/awesomplete.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/brand-form-modal.js" />"></script>
<script src="<c:url value="/resources/js/angular/stationScheduleApp.js" />"></script>
<script src="<c:url value="/resources/js/angular/stationScheduleCtrl.js" />"></script>
<script>
$(function () {
    pageLoaded();
});
</script>
</body>
</html>
