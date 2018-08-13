<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-clockpicker.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/trip_create.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css" />"/>

<title>RW | New trip</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="tripCreateApp" ng-controller="tripCreateCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="brand-panel">

                    <div class="right-side">
                        <div class="header">
                            <span class="header-caption">
                                Station stops
                            </span>
                        </div>

                        <div class="right-content">
                            <table ng-if="columnsReady" style="margin: 0 auto;">
                                <tbody>
                                <tr ng-repeat="station in stations">
                                    <td>

                                        <div class="input-group">

                                            <div class="input-group-prepend">
                                                <span class="input-group-text station-input">
                                                     {{ station.name }}
                                                </span>
                                            </div>

                                            <div ng-if="$index == 0" class="clockpicker text-center" data-placement="left" data-align="top"
                                                 data-autoclose="true">
                                                <input id="timepicker{{ $index }}1" value="-" type="text"
                                                class="form-control clockpicker-border-0 unselectable-input" readonly>
                                            </div>

                                            <div ng-if="$index > 0 && $index != stations.length - 1" class="clockpicker text-center" data-placement="left" data-align="top"
                                                 data-autoclose="true" clockpicker>
                                                <input id="timepicker{{ $index }}" value="00:05" type="text"
                                                       class="form-control clockpicker-border-0" readonly style="background: white;">
                                            </div>

                                            <div ng-if="$index == stations.length - 1" class="clockpicker text-center" data-placement="left" data-align="top"
                                                 data-autoclose="true">
                                                <input  id="timepicker{{ $index }}3" value="-" type="text"
                                                       class="form-control clockpicker-border-0 unselectable-input" readonly>
                                            </div>

                                            <div class="input-group-append">
                                                <span class="input-group-text clock-icon-border-0">
                                                    <span class="fa fa-clock-o fa-lg"></span>
                                                </span>
                                            </div>

                                        </div>

                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <div class="left-side">
                        <div class="header">
                            <span class="header-caption">
                                Create trip
                            </span>
                        </div>

                        <div class="left-content">

                            <form action="/admin/trip/create" method="post" onsubmit="prepareData()">
                                <!-- Pick date and time -->
                                <table style="margin: 0 auto;">
                                    <tr>
                                        <td>
                                            <div class="input-group">

                                                <div class="input-group-prepend">
                                                    <input type="text" id="datepicker"
                                                           class="form-control datepicker-border-0" placeholder="Date" required autocomplete="off" onkeydown="return false;" style="background: white;">
                                                </div>

                                                <div class="clockpicker text-center" data-placement="bottom"
                                                     data-align="right" data-autoclose="true">
                                                    <input id="timepicker" type="text"
                                                           class="form-control clockpicker-border-0" value="00:00"
                                                           readonly style="background-color: white;" required>
                                                </div>

                                                <div class="input-group-append">
                                                    <span class="input-group-text clock-icon-border-0">
                                                        <span class="fa fa-clock-o fa-lg"></span>
                                                    </span>
                                                </div>

                                            </div>
                                        </td>
                                    </tr>
                                </table>

                                <table ng-if="trainsReady" class="trains-table">
                                    <thead>
                                        <tr>
                                            <th>Train #</th>
                                            <th>Title</th>
                                            <th>Seats per carriage</th>
                                            <th>Carriages</th>
                                            <th>Speed</th>
                                            <th>Choose</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="train in trains">
                                            <td>{{ train.id }}</td>
                                            <td>{{ train.name }}</td>
                                            <td>{{ train.seats_amount }}</td>
                                            <td>{{ train.carriage_amount }}</td>
                                            <td>{{ train.speed}}</td>
                                            <td>
                                                <input type="radio" name="trainId" value="{{ train.id }}" required/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                                <input type="hidden" name="routeId" value="${routeId}" />
                                <input type="hidden" name="stationStopTimes" id="stationStopTimes" />
                                <input type="hidden" name="startTime" id="startTime" />
                                <button class="brand-pink-button">Create</button>
                            </form>

                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-clockpicker.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/tripCreateApp.js" />"></script>
<script>
    app.controller("tripCreateCtrl", function ($scope, $http) {

        $scope.stations = {};

        $http({
            url: "/route/get/station/list/",
            method: "GET",
            params: {
                routeId: ${routeId}
            }
        }).then(function success(response) {
            console.log('response.data: ', response.data);
            $scope.stations = response.data;
            $scope.columnsReady = true;
        });

        $http({
            url: "/train/get/available",
            method: "GET"
        }).then(function success(response) {
            $scope.trains = response.data;

            $scope.trainsReady = true;
            pageLoaded();
        });

    });
</script>
<script>
    $('#datepicker').datepicker();
    $('.clockpicker').clockpicker();

    function prepareData() {
        pageLoading();
        timePickersStopStations[0] = '00:00';
        timePickersStopStations.push('00:00');
        $("#stationStopTimes").val(timePickersStopStations);
        $("#startTime").val($("#datepicker").val() + ' ' + $("#timepicker").val());
        return true;
    }

</script>
</body>
</html>
