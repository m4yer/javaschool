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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/awesomplete.css"/>"/>
    <title>RW | Search trip</title>
</head>
<body id="page-top">

<div id="loader"></div>

<div id="pageContent" class="animate-bottom">

    <!-- Navigation -->
    <%@ include file="navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="admin_navigation.jsp" %>
    </sec:authorize>

    <!-- Search line section -->
    <div ng-app="searchTripApp" ng-controller="searchTripCtrl">
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
                                                   ng-model="stationFrom"/>
                                        </span>
                                        <span class="autocomplete-to">
                                            <input type="text" placeholder="To" class="input-place" ng-model="stationTo"
                                                   style="
                                                   border-top-left-radius: 0px;
                                                   border-bottom-left-radius: 0px;"/>
                                        </span>
                                        <input type="text" placeholder="Date" id="datepicker1" class="input-date"
                                               ng-model="dateStart" autocomplete="off"/>
                                        <input type="text" placeholder="Date" id="datepicker2" class="input-date"
                                               ng-model="dateEnd" autocomplete="off"/>
                                        <input type="submit" value="Search" ng-click="findTrips()"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div class="row">
                <table class="search-trip-results-table">
                    <tr ng-show="trips.length > 0">
                        <th>Train #</th>
                        <th>Route</th>
                        <th>Departing</th>
                        <th>Arriving</th>
                        <th>Book ticket</th>
                    </tr>
                    <tr ng-repeat="trip in trips">
                        <td>{{ trip.trainDto.id }}</td>
                        <td>{{ trip.route_id }}</td>
                        <td>{{ trip.start_time.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</td>
                        <td>{{ trip.arrival_time.epochSecond * 1000 | date:'dd/MM/yyyy HH:mm' }}</td>
                        <td>
                            <a href="/user/ticket/buy/?tripId={{trip.id}}&carriageNum=1&stationFrom={{stationFrom}}&stationTo={{stationTo}}">Buy!</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

<!-- div "pageContent" end -->
</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/awesomplete.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/searchTripApp.js" />"></script>
<script>
    $("#datepicker1").datepicker();
    $("#datepicker2").datepicker();
</script>
<script>

    app.controller("searchTripCtrl", function ($scope, $http) {
        // If we have "GET" parameters (means that user came to this page from index and he filled all inputs)
        // Then we need to GET valid trips and paste it in result page
        <c:choose>
        <c:when test="${not empty stationFrom}">
        $scope.stationFrom = '${stationFrom}';
        $scope.stationTo = '${stationTo}';
        $scope.dateStart = '${dateStart}';
        $scope.dateEnd = '${dateEnd}';
        $http({
            url: "/trip/find/",
            method: "POST",
            params: {
                stationFrom: '${stationFrom}',
                stationTo: '${stationTo}',
                dateStart: '${dateStart} 00:00',
                dateEnd: '${dateEnd} 00:00'
            }
        }).then(function success(response) {
            console.log(response.data);
            $scope.trips = response.data;

            for (var i = 0; i < $scope.trips.length; i++) {
                closure(i);
            }

            $http({
                url: "/station/get/list/title",
                method: "GET"
            }).then(function success(response) {
                var list = response.data;
                console.log('list: ', list);

                $http({
                    url: "/station/get/list/title",
                    method: "GET"
                }).then(function success(response) {
                    var list = response.data;
                    console.log('list: ', list);
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
        <c:otherwise>
        console.log('otherwise jstl');
        $http({
            url: "/station/get/list/title",
            method: "GET"
        }).then(function success(response) {
            var list = response.data;
            console.log('list: ', list);
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
        </c:otherwise>
        </c:choose>
        $scope.findTrips = function () {
            $http({
                url: "/trip/find/",
                method: "POST",
                params: {
                    stationFrom: $scope.stationFrom,
                    stationTo: $scope.stationTo,
                    dateStart: $scope.dateStart + ' 00:00',
                    dateEnd: $scope.dateEnd + ' 00:00'
                }
            }).then(function success(response) {
                console.log(response.data);
                $scope.trips = response.data;
                for (var i = 0; i < $scope.trips.length; i++) {
                    closure(i);
                }
            });
        }

        function closure(i) {
            $http({
                url: "/trip/arrival-time",
                method: "GET",
                params: {
                    tripId: $scope.trips[i].id,
                    stationTo: $scope.stationTo
                }
            }).then(function success(response) {
                console.log(response.data);
                console.log($scope.trips[i]);
                $scope.trips[i].arrival_time = response.data;
            });
        };
    });
</script>
</body>
</html>
