<%@ include file="../includes.jsp" %>
<html>
<link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tickets.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/carriage-line.css" />"/>
<title>RW | Buy ticket</title>
<body>

<div id="loader"></div>

<div id="pageContent">

    <%@ include file="../navigation.jsp" %>
    <%@ include file="../admin_navigation.jsp" %>


    <div class="container-fluid" ng-app="ticketApp" ng-controller="ticketCtrl">

        <div class="row">
            <div class="col-lg-12 text-center caption">
                <span class="caption-attention">Select carriage</span>
            </div>
            <div class="col-lg-12 carriages">
                <table class="unselectable outer-table">
                    <tr>

                        <c:forEach var="carriage" items="${seatsAmountMap}">
                            <td class="outer-td">
                                <table class="inner-table">
                                    <tr style="border-bottom: 1px solid grey;">
                                        <c:choose>
                                            <c:when test="${(carriage.key) == carriageNum}">
                                                <th colspan="2" class="active-carriage"><c:out
                                                        value="${carriage.key}"/></th>
                                            </c:when>
                                            <c:otherwise>
                                                <th class="carriage-selector" colspan="2" id="carriage-${carriage.key}"
                                                    onclick="changeCarriage(this.id)"><c:out
                                                        value="${carriage.key}"/></th>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                    <tr>
                                        <td class="amount-available"><c:out
                                                value="${trip.trainDto.seats_amount - carriage.value}"/></td>
                                        <td class="amount-unavailable"><c:out value="${carriage.value}"/></td>
                                    </tr>
                                </table>
                            </td>
                        </c:forEach>
                    </tr>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12 text-center caption">
                <span class="caption-attention">Select seat</span>
            </div>
            <c:choose>
                <c:when test="${trip.trainDto.seats_amount == 18}">
                    <%@ include file="../carriage_scheme/18.jsp" %>
                </c:when>
                <c:when test="${trip.trainDto.seats_amount == 36}">
                    <%@ include file="../carriage_scheme/36.jsp" %>
                </c:when>
                <c:when test="${trip.trainDto.seats_amount == 54}">
                    <%@ include file="../carriage_scheme/54.jsp" %>
                </c:when>
                <c:when test="${trip.trainDto.seats_amount == 66}">
                    <%@ include file="../carriage_scheme/66.jsp" %>
                </c:when>
            </c:choose>


            <div class="col-lg-12 text-center">
                <div class="info-section">
                    ${stationFromName} [{{ departureTime * 1000 | date:'dd/MM/yyyy HH:mm' }}] <span class="brand-pink-color">&rarr;</span> ${stationToName} [{{ arrivalTime * 1000 | date:'dd/MM/yyyy HH:mm' }}]
                </div>
            </div>

            <div class="col-md-12 text-center">

                <table style="margin: 0 auto;">
                    <tr>
                        <td>
                            <div class="input-group">
                                <div class="input-group-text carriage-input">
                                    Carriage<span class="input-important-number">{{ carriageNum }}</span>
                                </div>
                                <div class="input-group-text seat-input">
                                    Seat<span class="input-important-number">{{ seatNum }}</span>
                                </div>
                                <input type="submit" id="btn_buy_ticket" class="button_buy" value="Buy">
                            </div>
                        </td>
                    </tr>
                </table>

            </div>

            <div class="col-md-12 text-center" style="font-size: 32px;color:red;margin-top: 20px;">
                <span id="error_msg"></span>
            </div>

        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/ticketApp.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script>
    var oldSeatId;
    var chosenSeatId;

    function chooseSeat(seatId) {
        chosenSeatId = seatId;
        $('#' + seatId).addClass('chosen-seat');
        if (oldSeatId != null) {
            if (oldSeatId != seatId) {
                $('#' + oldSeatId).removeClass('chosen-seat');
            }
        }
        oldSeatId = seatId;
    }
</script>
<script>

    $("#carriageSelector option[value=${carriageNum}]").attr('selected', true);

    $("#btn_buy_ticket").click(function () {
        $.ajax({
            url: "/user/ticket/buy/",
            method: "POST",
            data: {
                tripId: ${trip.id},
                seatId: chosenSeatId,
                stationFromName: '${stationFromName}',
                stationToName: '${stationToName}',
                carriageNum: ${carriageNum}
            }
        }).done(function (response) {
            if (response == "notavailable") {
                $("#error_msg").html("Sorry, you cannot buy ticket for this trip.<br>It's currently unavailable.");
            } else if (response == "alreadybought") {
                $("#error_msg").html("You've already bought a ticket for this trip!");
            } else if (response == "success") {
                window.location.replace("/user/ticket/list");
            }
        });
    });

    function changeCarriage(carriageNum) {
        carriageNum = carriageNum.split('carriage-').join('');
        window.location.replace("/user/ticket/buy/?tripId=" + ${trip.id} +"&carriageNum=" + carriageNum + "&stationFrom=" + '${stationFromName}' + "&stationTo=" + '${stationToName}');
    }

</script>
<script>
    <c:forEach var="i" begin="1" end="${trip.trainDto.seats_amount}" varStatus="loop">

    <c:set var="inside" value="${false}" scope="page"/>

    <c:forEach var="bookedTicket" items="${bookedTickets}">
    <c:choose>
    <c:when test="${i == bookedTicket.seat_id}">
    <c:set var="inside" value="${true}" scope="page"/>
    </c:when>
    </c:choose>
    </c:forEach>

    <c:choose>
    <c:when test="${inside == true}"> // this ticket is booked already
    $("#${i}").css('background-color', 'rgba(255, 107, 0, 0.36)');
    $("#${i}").addClass("booked-ticket");
    $("#${i}").removeAttr('onclick');
    $("#${i}").removeAttr('ng-click');
    </c:when>
    </c:choose>

    </c:forEach>

    app.controller("ticketCtrl", function($scope, $http) {
        $scope.carriageNum = '${carriageNum}';
        $scope.chooseSeat = function(event) {
            $scope.seatNum = event.target.id;
            console.log('$scope.seatNum: ' + $scope.seatNum);
            console.log('ng-click');
        };
        $http({
            url: "/trip/departure-time",
            method: "GET",
            params: {
                tripId: '${trip.id}'
            }
        }).then(function success(response) {
            $scope.departureTime = response.data['epochSecond'];
            console.log('Departure time: ' + $scope.departureTime);

            $http({
                url: "/trip/arrival-time",
                method: "GET",
                params: {
                    tripId: '${trip.id}',
                    stationTo: '${stationToName}'
                }
            }).then(function success(response) {
                $scope.arrivalTime = response.data['epochSecond'];
                console.log('Arrival time: ' + $scope.arrivalTime);
                pageLoaded();
            });

        });
    });

</script>
</body>
</html>
