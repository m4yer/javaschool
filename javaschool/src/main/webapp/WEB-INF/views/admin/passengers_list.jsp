<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/search-trip-results-table.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/carriage-line.css" />"/>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/datatables.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-datatables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/angular-datatables.custom.css" />"/>

<title>RW | Passengers</title>
<body>

<div id="loader"></div>

<div id="pageContent" ng-app="passengersListApp" ng-controller="passengersListCtrl">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">


            <div class="col-lg-12 text-center caption">
                <a href="/admin/trip/list" class="brand-dark-grey-button" style="
                font-size: 12px;margin-right: 24px;">&larr; Trips</a>
                <span class="caption-attention">Select carriage</span>
            </div>
            <div class="col-lg-12 carriages">
                <table class="unselectable outer-table">
                    <tr>

                        <c:forEach var="i" begin="0" end="${trip.trainDto.carriage_amount - 1}" varStatus="loop">
                            <td class="outer-td">
                                <table class="inner-table">
                                    <tr style="border-bottom: 1px solid grey;">
                                        <c:choose>
                                            <c:when test="${(loop.count) == carriageNum}">
                                                <th colspan="2" class="active-carriage" style="min-width: 28px;"><c:out
                                                        value="${loop.count}"/></th>
                                            </c:when>
                                            <c:otherwise>
                                                <th class="carriage-selector" colspan="2" style="min-width: 28px;" id="carriage-${loop.count}"
                                                    onclick="changeCarriage(this.id)"><c:out
                                                        value="${loop.count}"/></th>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </table>
                            </td>
                        </c:forEach>

                    </tr>
                </table>
            </div>

            <div class="col-lg-12" style="overflow-x: auto;">
                <table ng-if="columnsReady" datatable="" dt-option="dtOptions" dt-columns="dtColumns" class="search-trip-results-table">
                    <thead>
                        <tr ng-show="tickets.length > 0">
                            <th>Seat #</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Passenger</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="ticket in tickets">
                            <td>{{ ticket.seat_id }}</td>
                            <td>{{ ticket.station_from.name }}</td>
                            <td>{{ ticket.station_to.name }}</td>
                            <td style="font-size: 12px;"><button id="user-{{ ticket.user.id }}" class="brand-pink-button" ng-click="showUserInformation($event)">{{ ticket.user.firstname + ' ' + ticket.user.lastname  }}</button></td>
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
<script src="<c:url value="/resources/js/angular/passengersListApp.js" />"></script>
<script>
    app.controller("passengersListCtrl", function($scope, $http, DTOptionsBuilder, DTColumnBuilder) {

        $http({
            url: "/admin/trip/passengers/get",
            method: "GET",
            params: {
                tripId: ${trip.id},
                carriageNum: ${carriageNum}
            }
        }).then(function success(response) {
            $scope.tickets = response.data;
            console.log('$scope.tickets: ', $scope.tickets);

            //create options
            $scope.dtOptions = DTOptionsBuilder.newOptions()
                .withOption('scrollY', '300px')
                .withOption('scrollX', '100%')
                .withOption('scrollCollapse', true);

            //initialize the dataTable
            $scope.columnsReady = true;
            $(".nav-link-trips").css('color', '#FF5A5F');
            pageLoaded();
        });

        $scope.showUserInformation = function (event) {
            pageLoading();
            var chosenUserId = event.target.id.split('user-').join('');

            for (var i = 0; i < $scope.tickets.length; i++) {
                if ($scope.tickets[i]['user']['id'] == chosenUserId) {
                    $scope.userDetails = $scope.tickets[i]['user'];
                    console.log('Gotten user, exit the loop');
                    break;
                }
            }

            openModalForm();
            pageLoaded();


        }

    });

    function changeCarriage(carriageNum) {
        carriageNum = carriageNum.split('carriage-').join('');
        window.location.replace("/admin/trip/passengers/?tripId=" + ${trip.id} +"&carriageNum=" + carriageNum);
    }
</script>
</body>
</html>
