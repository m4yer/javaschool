<%@ include file="../includes.jsp" %>
<html>
<link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<style>
    #map {
        height: 65vh;
    }
</style>
<title>RW | Railways</title>
<body>

<div id="loader"></div>

<div id="pageContent">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div id="map"></div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script>

    var map;
    var moscowCoordinates = {lat: 55.751244, lng: 37.618423};

    var redMarker = 'http://maps.google.com/mapfiles/ms/micons/red-dot.png';
    var greenMarker = 'http://maps.google.com/mapfiles/ms/micons/green-dot.png';

    var tempMarker;
    var markerRightClicked;
    var polylines = [];

    var availableDirections = [];
    var infowindow;
    var infowindowContent;

    function initMap() {

        $.ajax({
            url: "/admin/station/list/get",
            method: "GET"
        }).done(function (response) {

            var allStationsArray = JSON.parse(response);

            map = new google.maps.Map(document.getElementById('map'), {
                center: moscowCoordinates,
                zoom: 5
            });

            allStationsArray.forEach(function (station) {
                var stationCoordinates = {lat: station['latitude'], lng: station['longitude']};
                var marker = new google.maps.Marker({
                    position: stationCoordinates,
                    map: map,
                    icon: redMarker,
                    title: station['name']
                });

                // Marker - leftclick
                marker.addListener("click", function () {

                    if(infowindow) {
                        infowindow.close();
                    }

                    // Make chosen marker green
                    marker.setIcon(greenMarker);

                    if (tempMarker != null) {
                        // If previously we've chosen any of markers
                        if (tempMarker != marker) {
                            tempMarker.setIcon(redMarker);
                        }
                    }

                    tempMarker = marker;

                    // console.log('Position: ' + marker.getPosition());
                    // console.log('title: ' + marker.getTitle());
                    // console.log('lat: ' + marker.getPosition().lat() + ' / lng: ' + marker.getPosition().lng());

                    $.ajax({
                        url: "/station/get/departure/stations",
                        method: "GET",
                        data: {
                            stationName: marker.getTitle()
                        }
                    }).done(function (response) {
                        // console.log('Available directions: ' + response);
                        availableDirections = JSON.parse(response);
                        // console.log(availableDirections);

                        $.ajax({
                            url: "/station/get/departure/coordinates",
                            method: "GET",
                            data: {
                                stationName: marker.getTitle()
                            }
                        }).done(function (response) {

                            polylines.forEach(function (polyline) {
                                polyline.setMap(null);
                            });
                            polylines = [];

                            // console.log('It\'s coordinates: ' + response);

                            var coordinatesArray = JSON.parse(response);
                            coordinatesArray.forEach(function (element) {
                                // console.log(element['latitude'] + '@' + element['longitude']);

                                var stationFrom = new google.maps.LatLng(marker.getPosition().lat(), marker.getPosition().lng());
                                var stationTo = new google.maps.LatLng(element['latitude'], element['longitude']);

                                var line = [stationFrom, stationTo];

                                var polyline = new google.maps.Polyline({
                                    path: line,
                                    strokeColor:"#0059ff",
                                    strokeOpacity:1.0,
                                    strokeWeight:4
                                });

                                polylines.push(polyline);

                                polyline.setMap(map);

                            })

                        });

                    });

                });

                // Marker - rightclick
                marker.addListener("rightclick", function () {
                    markerRightClicked = marker;
                    if (tempMarker != null) {
                        if (marker.getTitle() != tempMarker.getTitle()) {
                            if (infowindow) {
                                infowindow.close();
                            }

                            if (availableDirections.includes(marker.getTitle())) {
                                // We can only delete such directions
                                infowindowContent = '' +
                                    '<button class="btn btn-danger" onclick="removeDirection(tempMarker.getTitle(), markerRightClicked.getTitle(), tempMarker)">- ' + marker.getTitle() + '</button>';
                            } else {
                                // We can only add such directions
                                infowindowContent = '' +
                                    '<button class="btn btn-success" onclick="addDirection(tempMarker.getTitle(), markerRightClicked.getTitle(), tempMarker)">+ ' +  marker.getTitle() + '</button>';
                            }


                            infowindow = new google.maps.InfoWindow({
                                content: infowindowContent
                            });

                            infowindow.open(map, marker);
                        }
                    }

                });

            });
            $(".nav-link-roads").css('color', '#FF5A5F');
            pageLoaded();
        });
    }

    function removeDirection(stationFrom, stationTo, marker) {
        // console.log('removing direction: ' + stationFrom + ' -> ' + stationTo);
        $.ajax({
            url: "/admin/direction/remove",
            method: "POST",
            data: {
                stationFromName: stationFrom,
                stationToName: stationTo
            }
        }).done(function () {
            // console.log('Direction removed:');
            // console.log(stationFrom + ' -> ' + stationTo);
            google.maps.event.trigger(marker, 'click');
        });
    }
    function addDirection(stationFrom, stationTo, marker) {
        $.ajax({
            url: "/admin/direction/add",
            method: "POST",
            data: {
                stationFromName: stationFrom,
                stationToName: stationTo
            }
        }).done(function () {
            // console.log('Direction added:');
            // console.log(stationFrom + ' -> ' + stationTo);
            google.maps.event.trigger(marker, 'click');
        });
    }

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAZl4Ss3m1ac5VAQHmDWtu36oU65iMDeyw&callback=initMap"
        async defer></script>
</body>
</html>
