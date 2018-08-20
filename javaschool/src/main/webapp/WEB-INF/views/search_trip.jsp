<%@ include file="includes.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/magnific-popup.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/partial-trip.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/awesomplete.css"/>"/>

    <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/datatables.js" />"></script>
    <script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
    <script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>
    <title>RW | Search trip</title>
    <style>
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
            width: 62%;
            margin-top: 5%;
        }

        @media (max-width: 920px) {
            .brand-form-modal-content {
                width: 74%;
                margin-top: 8%;
            }
        }

        @media (max-width: 708px) {
            .brand-form-modal-content {
                width: 90%;
                margin-top: 8%;
            }
        }
        .brand-pink-button {
            padding-left: 12px;
            padding-right: 12px;
        }
    </style>
</head>
<body id="page-top">

<div id="loader"></div>

<div id="pageContent" class="animate-bottom" ng-app="searchTripApp" ng-controller="searchTripCtrl">

    <!-- Navigation -->
    <%@ include file="navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="admin_navigation.jsp" %>
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
                                        <span class="autocomplete-from">
                                            <input type="text" placeholder="From" class="input-place"
                                                   ng-model="stationFrom" required id="awesomplete-from"/>
                                        </span>
                                        <span class="autocomplete-to">
                                            <input type="text" placeholder="To" class="input-place" ng-model="stationTo"
                                                   style="
                                                   border-top-left-radius: 0px;
                                                   border-bottom-left-radius: 0px;" required  id="awesomplete-to"/>
                                        </span>
                                        <input type="text" placeholder="Date" id="datepicker1" class="input-date"
                                               ng-model="dateStart" autocomplete="off" required onkeydown="return false;"/>
                                        <input type="text" placeholder="Date" id="datepicker2" class="input-date"
                                               ng-model="dateEnd" autocomplete="off" required onkeydown="return false;"/>
                                        <input class="brand-pink-button" type="button" value="Search" ng-click="findTrips()"
                                               ng-disabled="stationFrom.length == 0 || stationTo.length == 0 || !dateStart || !dateEnd" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div ng-if="!userWasFindingTrips">
                <div class="info-page-center">
                    <img src="<c:url value="/resources/img/search-pic.png" />"/><br>
                    Let's find a trip for you!<br><br>
                </div>
            </div>
            <div ng-if="userWasFindingTrips && trips == 0 && partialTrips == 0">
                <div class="info-page-center">
                    <img src="<c:url value="/resources/img/nothing-found.png" />"/><br>
                    Nothing matched your search parameters was found.<br><br>
                </div>
            </div>

            <!-- Direct trips -->
            <div class="row">
                <table class="search-trip-results-table">
                    <tr ng-show="trips.length > 0">
                        <th>Train #</th>
                        <th>Departing</th>
                        <th>Arriving</th>
                        <th>Details</th>
                        <th>Book ticket</th>
                    </tr>
                    <tr ng-repeat="trip in trips">
                        <td>{{ trip.trainDto.id }}</td>
                        <td>{{ trip.departure_time.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</td>
                        <td>{{ trip.arrival_time.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</td>
                        <td>
                            <button style="font-size: 12px; padding: 4px 12px;" id="trip-{{ trip.id }}"
                                    class="brand-pink-button" ng-click="showTripDetails($event)">TRIP DETAILS
                            </button>
                        </td>
                        <td>
                            <form action="/user/ticket/buy/" method="get">
                                <input type="hidden" name="tripId" value="{{trip.id}}"/>
                                <input type="hidden" name="carriageNum" value="1"/>
                                <input type="hidden" name="stationFrom" value="{{stationFrom}}"/>
                                <input type="hidden" name="stationTo" value="{{ stationTo }}"/>
                                <button class="brand-pink-button" style="font-size: 12px; padding: 4px 12px;"
                                        href="/user/ticket/buy/?tripId={{trip.id}}&carriageNum=1&stationFrom={{stationFrom}}&stationTo={{stationTo}}">
                                    Buy!
                                </button>
                            </form>
                        </td>
                    </tr>
                </table>
            </div>

            <!-- Partial trips -->
            <div class="row">
                <div class="partial-wrapper" ng-repeat="i in [] | range: partialTrips.length">
                    <div class="partial-block">
                        <div class="partial-line">{{ stationFromFixed + ' [' + (partialTrips[i][0].timeArr.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm') + ']' + ' &rarr; ' + transferStations[i] + ' [' + (partialTrips[i][0].timeDep.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm') + ']' }}</div>
                        <div class="partial-line">{{ transferStations[i] + ' [' + (partialTrips[i][1].timeArr.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm') + ']' + ' &rarr; ' + stationToFixed + ' [' + (partialTrips[i][1].timeDep.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm') + ']' }}</div>
                        <form action="/user/ticket/buy/" method="get">
                            <input type="hidden" name="tripId" value="{{partialTrips[i][0].id}}"/>
                            <input type="hidden" name="carriageNum" value="1"/>
                            <input type="hidden" name="stationFrom" value="{{stationFrom}}"/>
                            <input type="hidden" name="lastStation" value="{{stationTo}}"/>
                            <input type="hidden" name="stationTo" value="{{transferStations[i]}}"/>
                            <input type="hidden" name="tripIds" value="{{partialTrips[i][0].id + '-' + partialTrips[i][1].id}}"/>
                            <button class="brand-pink-button" style="font-size: 12px; padding: 4px 12px;"
                                    href="/user/ticket/buy/?tripId={{partialTrips[i][0].id}}&carriageNum=1&stationFrom={{stationFrom}}&stationTo={{transferStations[i]}}&tripIds=">
                                Buy!
                            </button>
                        </form>
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
                <span class="caption" style="margin-left: 32px;">Trip details</span>
            </div>
            <div class="brand-form-modal-body">

                <span class="span-wrapper">
                    <span class="span-number">#</span><span class="span-station">Station</span><span
                    ng-if="!schedule.time_arrival.epochSecond" class="span-time">Arrival</span><span
                    ng-if="!schedule.time_departure.epochSecond" class="span-time">Departure</span>
                </span>

                <span ng-repeat="schedule in schedules" class="span-wrapper">
                    <span class="span-number">{{ $index + 1}}</span><span class="span-station">{{ schedule.stationDto.name }}</span><span
                        ng-if="!schedule.time_arrival.epochSecond" class="span-time">-</span><span
                        ng-if="schedule.time_arrival.epochSecond" class="span-time">{{ schedule.time_arrival.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</span><span
                        ng-if="!schedule.time_departure.epochSecond" class="span-time">-</span><span
                        ng-if="schedule.time_departure.epochSecond" class="span-time">{{ schedule.time_departure.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</span>
                </span>

            </div>
        </div>

    </div>

    <!-- div "pageContent" end -->
</div>

<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/brand-form-modal.js" />"></script>
<script src="<c:url value="/resources/js/awesomplete.js" />"></script>
<script src="<c:url value="/resources/js/angular/searchTripApp.js" />"></script>
<script>
    $("#datepicker1").datepicker({
        startDate: 'now',
        endDate: '+1m'
    });
    $("#datepicker2").datepicker({
        startDate: 'now',
        endDate: '+1m'
    });
</script>
<script>
    var fullTimes = [];
    app.controller("searchTripCtrl", function ($scope, $http, DTOptionsBuilder) {
        // If we have "GET" parameters (means that user came to this page from index and he filled all inputs)
        // Then we need to GET valid trips and paste it in result page
        <c:choose>
        <c:when test="${not empty stationFrom}">

            <c:choose>
                <c:when test="${not empty stationTo}">
                    <c:choose>
                        <c:when test="${not empty dateStart}">
                            <c:choose>
                                <c:when test="${not empty dateEnd}">
                                    $scope.stationFrom = '${stationFrom}';
                                    $scope.stationTo = '${stationTo}';
                                    $scope.dateStart = '${dateStart}';
                                    $scope.dateEnd = '${dateEnd}';
                                    $scope.userWasFindingTrips = true;
                                    $http({
                                        url: "/trip/get/",
                                        method: "GET",
                                        params: {
                                            stationFrom: '${stationFrom}',
                                            stationTo: '${stationTo}',
                                            dateStart: '${dateStart} 00:00',
                                            dateEnd: '${dateEnd} 00:00'
                                        }
                                    }).then(function success(response) {
                                        // console.log(response.data);
                                        $scope.trips = response.data;

                                        for (var i = 0; i < $scope.trips.length; i++) {
                                            closure(i);
                                        }

                                        $http({
                                            url: "/station/get/list/title",
                                            method: "GET"
                                        }).then(function success(response) {
                                            var list = response.data;
                                            // console.log('list: ', list);

                                            $http({
                                                url: "/station/get/list/title",
                                                method: "GET"
                                            }).then(function success(response) {
                                                var list = response.data;
                                                // console.log('list: ', list);
                                                new Awesomplete(document.querySelector(".autocomplete-from input"),{
                                                    list: list,
                                                    minChars: 1,
                                                    maxItems: 8
                                                });
                                                new Awesomplete(document.querySelector(".autocomplete-to input"),{
                                                    list: list,
                                                    minChars: 1,
                                                    maxItems: 8
                                                });
                                                pageLoaded();
                                            });

                                        });

                                    });
                                </c:when>
                            </c:choose>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>

        </c:when>
        <c:otherwise>
            $http({
                url: "/station/get/list/title",
                method: "GET"
            }).then(function success(response) {
                var list = response.data;
                // console.log('list: ', list);
                new Awesomplete(document.querySelector(".autocomplete-from input"), {
                    list: list,
                    minChars: 1,
                    maxItems: 8
                });
                new Awesomplete(document.querySelector(".autocomplete-to input"), {
                    list: list,
                    minChars: 1,
                    maxItems: 8
                });
                pageLoaded();
            });
        </c:otherwise>
        </c:choose>
        $scope.findTrips = function () {
            pageLoading();
            $scope.userWasFindingTrips = true;
            var dateTimeFrom;
            if ($scope.dateStart != null) {
                dateTimeFrom = $scope.dateStart + ' 00:00';
            } else {
                var today = new Date();
                var mm = today.getMonth() + 1; // january = 0
                if (mm < 10) mm = '0' + mm;
                var dd = today.getDate();
                var yyyy = today.getFullYear();
                dateTimeFrom = mm + '/' + dd + '/' + yyyy;
                alert('date doesnot filled so use today(): ' + dateTimeFrom);
            }
            var dateTimeTo;
            if($scope.dateEnd != null) {
                dateTimeTo = $scope.dateEnd + ' 00:00'
            }
            $http({
                url: "/trip/get/",
                method: "GET",
                params: {
                    stationFrom: $scope.stationFrom,
                    stationTo: $scope.stationTo,
                    dateStart: dateTimeFrom,
                    dateEnd: dateTimeTo
                }
            }).then(function success(response) {
                $scope.trips = response.data;
                var foundDirectTripsCount = $scope.trips.length;
                for (var i = 0; i < $scope.trips.length; i++) {
                    closure(i);
                }
                for (var i = 0; i < $scope.trips.length; i++) {
                    closureDepTime(i);
                }
                // console.log('We tried to find DIRECT trips, and foundDirectTripsCount: ', foundDirectTripsCount);
                if (foundDirectTripsCount == undefined) {
                    // console.log('Due to DIRECT trips = 0, trying to find PARTIAL trips.');
                    pageLoading();
                    $scope.stationFromFixed = $scope.stationFrom;
                    $scope.stationToFixed = $scope.stationTo;
                    $http({
                        url: "/trip/get/partial/",
                        method: "GET",
                        params: {
                            stationFrom: $scope.stationFrom,
                            stationTo: $scope.stationTo,
                            dateStart: dateTimeFrom,
                            dateEnd: dateTimeTo
                        }
                    }).then(function success(response) {
                        $scope.partialTrips = response.data;
                        var counter = 0;
                        var tripsArray = [];
                        var transferStations = [];
                        angular.forEach($scope.partialTrips, function (value, key) {
                            tripsArray.push(angular.fromJson(value));
                            var transfersTemp = key.split(',');
                            var tempArray = [];
                            transfersTemp.forEach(function (station) {
                                tempArray.push(station);
                            });
                            transferStations.push(tempArray);
                            // console.log('$scope.trips[counter]: ', $scope.trips[counter]);
                            // console.log('key: ', key);
                            // console.log('value: ', value);
                            counter++;
                        });
                        $scope.partialTrips = tripsArray;
                        // console.log('$scope.partialTrips: ', $scope.partialTrips);
                        $scope.transferStations = transferStations;
                        // console.log('$scope.transferStations: ', $scope.transferStations);

                        for (var i = 0; i < $scope.partialTrips.length; i++) {

                            stationsTemp = [];
                            stationsTemp.push($scope.stationFrom);
                            $scope.transferStations[i].forEach(function (station) {
                                stationsTemp.push(station);
                            });
                            stationsTemp.push($scope.stationTo);

                            for (var j = 0; j < $scope.partialTrips[i].length; j++) {
                                getArrivalTimePartialTrips(i, j);
                            }
                        }
                    });
                }

                if (foundDirectTripsCount != undefined) {
                    $scope.partialTrips = [];
                }

            }, function (response) {
                if(response.status == 500) {
                    // console.log('STATUS FAILED');
                    $scope.trips = [];
                    $scope.partialTrips = [];
                }
            });
        };

        var stationsTemp = [];
        function getArrivalTimePartialTrips(i, j) {
            pageLoading();
            // console.log('Params GET partial time:');
            // console.log('$scope.partialTrips[i][j][\'id\']: ', $scope.partialTrips[i][j]['id']);
            // console.log('$scope.stationFrom: ', $scope.stationFrom);
            // console.log('$scope.stationTo: ', $scope.stationTo);
            $http({
                url: "/trip/partial-time",
                method: "GET",
                params: {
                    tripId: $scope.partialTrips[i][j]['id'],
                    stationFrom: stationsTemp[j],
                    stationTo: stationsTemp[j+1]
                }
            }).then(function success(response) {
                // console.log('response from get PARTIAL time: ', response.data);
                $scope.partialTrips[i][j]['timeDep'] = response.data[0];
                $scope.partialTrips[i][j]['timeArr'] = response.data[1];
                // console.log('$scope.partialTrips AFTER get schedule: ', $scope.partialTrips);
                pageLoaded();
            });
        }

        $scope.showTripDetails = function (event) {
            var chosenTripId = event.target.id.split('trip-').join('');
            $http({
                url: "/route/schedule/get/",
                method: "GET",
                params: {
                    tripId: chosenTripId
                }
            }).then(function success(response) {
                // console.log('$scope.schedules: ', response.data);
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

        function closure(i) {
            $http({
                url: "/trip/arrival-time",
                method: "GET",
                params: {
                    tripId: $scope.trips[i].id,
                    stationTo: $scope.stationTo
                }
            }).then(function success(response) {
                // console.log(response.data);
                // console.log($scope.trips[i]);
                $scope.trips[i].arrival_time = response.data;
            });
            if (i == $scope.trips.length - 1) {
            }
        }

        function closureDepTime(i) {
            $http({
                url: "/trip/departure-time",
                method: "GET",
                params: {
                    tripId: $scope.trips[i].id,
                    stationFrom: $scope.stationFrom
                }
            }).then(function success(response) {
                // console.log(response.data);
                // console.log($scope.trips[i]);
                $scope.trips[i].departure_time = response.data;
            });
            if (i == $scope.trips.length - 1) {
                pageLoaded();
            }
        }
    });
</script>
</body>
</html>
