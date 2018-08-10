<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<title>RW | Edit route</title>
<body>

<div id="loader"></div>

<div id="pageContent">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">

            <!-- GOOGLE MAP SECTION -->
            <div class="col-md-12">
                <div id="map"></div>
                <form action="/admin/route/edit" method="post" onsubmit="return prepareData()">
                    <input type="hidden" name="routeId" id="routeId" />
                    <input type="hidden" name="stationSequence" id="stationSequence" />
                    <button class="brand-pink-button">Save this route</button>
                </form>
            </div>

        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script>

    function prepareData() {
        if (stations.length < 3) {
            alert('Route must consist of 3 stations at least!');
            return false;
        } else {
            $("#routeId").val(${routeId});

            $("#stationSequence").val(stations);
            return true;
        }
    }

    var map;
    var moscowCoordinates = {lat: 55.751244, lng: 37.618423};

    var redMarker = 'http://maps.google.com/mapfiles/ms/micons/red-dot.png';

    var infowindow;
    var infowindowContent;

    var chosenMarker;
    var markers = [];
    var allMarkers = [];

    var routeStations = [];

    var polylines = [];
    var stations = [];
    var routePath = [];
    var routePolylines = [];

    var anyStation = true;
    var availableStations = [];

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

                allMarkers.push(marker);

                marker.addListener("click", function () {

                    chosenMarker = marker;

                    if (infowindow) {
                        infowindow.close();
                    }

                    if (stations.includes(chosenMarker.getTitle())) {
                        if (stations.indexOf(chosenMarker.getTitle()) == stations.length - 1) {
                            // If station is green and the last - ALLOWED TO DELETE
                            infowindowContent = '<button class="brand-pink-button" style="margin: 12px; font-size: 16px;" onclick="deleteStation(chosenMarker.getTitle(), chosenMarker)">Remove from route</button>';
                        }
                        else {
                            // If station is green but not the last
                            infowindowContent = 'You\'re allowed to delete only the last station from the route.';
                        }
                    } else {
                        if (anyStation) {
                            infowindowContent = '<button class="brand-pink-button" style="margin: 12px; font-size: 16px;" onclick="addStation(chosenMarker.getTitle(), chosenMarker)">Add to route</button>';
                        } else {
                            if (availableStations.includes(chosenMarker.getTitle())) {
                                infowindowContent = '<button class="brand-pink-button" style="margin: 12px; font-size: 16px;" onclick="addStation(chosenMarker.getTitle(), chosenMarker)">Add to route</button>';
                            } else {
                                infowindowContent = 'This station don\'t have connection with the last added one.';
                            }
                        }
                    }

                    infowindow = new google.maps.InfoWindow({
                        content: infowindowContent
                    });
                    infowindow.open(map, marker);
                });

            });

            // Get all currentRoute stations.
            // Then draw them on the map.
            $.ajax({
                url: "/route/get/station/list/",
                method: "GET",
                data: {
                    routeId: ${routeId}
                }
            }).done(function (response) {
                routeStations = JSON.parse(response);
                console.log('Get all Route Stations: ', routeStations);
                routeStations.forEach(function (station) {

                    for (var i = 0; i < allMarkers.length; i++) {
                        if(allMarkers[i].getTitle() == station['name']) {
                            addStation(station['name'], allMarkers[i]);
                        }
                    }

                });
                pageLoaded();
            });

        });

    }

    function addStation(station, marker) {

        // If there are some blue lines on the map
        if (polylines.length != 0) {
            console.log('Clear blue lines!');
            polylines.forEach(function (element) {
                element.setMap(null);
            });
        }
        stations.push(station);
        if (infowindow) {
            infowindow.close();
        }
        var icon = 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=' + stations.length + '|00FF00|000000'
        marker.setIcon(icon);

        // var stationCoordinates = new google.maps.LatLng(marker.getPosition().lat(), marker.getPosition().lng());
        var stationCoordinates = {lat: marker.getPosition().lat(), lng: marker.getPosition().lng()};

        routePath.push(stationCoordinates);

        console.log(routePath);

        if (stations.length > 1) {
            var polyline = new google.maps.Polyline({
                path: routePath,
                strokeColor: "#00ff32",
                strokeOpacity: 1.0,
                strokeWeight: 4
            });
            polyline.setMap(map);
            routePolylines.push(polyline);
        }

        $.ajax({
            url: "/station/get/departure/coordinates",
            method: "GET",
            data: {
                stationName: station
            }
        }).done(function (response) {
            var coordinates = JSON.parse(response);

            if (polylines.length > 0) {
                console.log('Clear blue lines!');
                polylines.forEach(function (element) {
                    element.setMap(null);
                });
            }

            coordinates.forEach(function (element) {

                var coordinatesFrom = {lat: marker.getPosition().lat(), lng: marker.getPosition().lng()};
                // var coordinatesFrom = new google.maps.LatLng(marker.getPosition().lat(), marker.getPosition().lng());
                var coordinatesTo = {lat: element['latitude'], lng: element['longitude']};
                // var coordinatesTo = new google.maps.LatLng(element['latitude'], element['longitude']);
                var line = [coordinatesFrom, coordinatesTo];

                var draw = true;

                // Loop for checking if it is in routePath (means that we already have green connection line)
                routePath.forEach(function (element) {
                    // console.log(coordinatesTo['lat'] + ' and ' + element['lat']);
                    if (coordinatesTo['lat'].toFixed(5) == element['lat'].toFixed(5)) {
                        if (coordinatesTo['lng'].toFixed(5) == element['lng'].toFixed(5)) {
                            draw = false;
                        }
                    }
                });

                if (draw) {
                    if (station == stations[stations.length - 1]) {
                        console.log('Drawing a blue line!');
                        var polyline = new google.maps.Polyline({
                            path: line,
                            strokeColor: "#0059ff",
                            strokeOpacity: 1.0,
                            strokeWeight: 4
                        });

                        polylines.push(polyline);
                        polyline.setMap(map);
                    }
                }
            });
        });

        $.ajax({
            url: "/station/get/departure/stations",
            method: "GET",
            data: {
                stationName: marker.getTitle()
            }
        }).done(function (response) {
            availableStations = JSON.parse(response);
        });

        markers.push(marker);
        anyStation = false;

    }

    function deleteStation(station, marker) {
        if (markers.length == 1) {
            anyStation = true;
        }
        markers.pop();
        stations.pop();
        infowindow.close();
        marker.setIcon(redMarker);

        // If there are some blue lines on the map
        if (polylines.length != 0) {
            polylines.forEach(function (element) {
                element.setMap(null);
            });
        }

        // Route Polyline
        routePath.pop();
        if (routePolylines.length != 0) {
            routePolylines[routePolylines.length - 1].setMap(null);
            routePolylines.pop();
        }

        if (markers.length > 0) {
            console.log('markers.length > 1');
            console.log('Previous station:');
            console.log(markers[markers.length - 1].getTitle());
            $.ajax({
                url: "/station/get/departure/coordinates",
                method: "GET",
                data: {
                    stationName: markers[markers.length - 1].getTitle()
                }
            }).done(function (response) {
                console.log('ajax completed after removing');
                console.log(response);
                console.log('routePath size: ' + routePath.length);
                var coordinates = JSON.parse(response);
                coordinates.forEach(function (element) {
                    var coordinatesFrom = {lat: markers[markers.length - 1].getPosition().lat(), lng: markers[markers.length - 1].getPosition().lng()};
                    // var coordinatesFrom = new google.maps.LatLng(marker.getPosition().lat(), marker.getPosition().lng());
                    var coordinatesTo = {lat: element['latitude'], lng: element['longitude']};
                    // var coordinatesTo = new google.maps.LatLng(element['latitude'], element['longitude']);
                    var line = [coordinatesFrom, coordinatesTo];

                    var draw = true;

                    // Loop for checking if it is in routePath (means that we already have yellow connection line)
                    routePath.forEach(function (element) {
                        console.log(coordinatesTo['lat'] + ' and ' + element['lat']);
                        if (coordinatesTo['lat'].toFixed(5) == element['lat'].toFixed(5)) {
                            if (coordinatesTo['lng'].toFixed(5) == element['lng'].toFixed(5)) {
                                draw = false;
                                console.log('draw = false');
                            }
                        }
                    });

                    if (draw) {
                        var polyline = new google.maps.Polyline({
                            path: line,
                            strokeColor: "#0059ff",
                            strokeOpacity: 1.0,
                            strokeWeight: 4
                        });
                        console.log('draw!!!');
                        polylines.push(polyline);
                        polyline.setMap(map);
                    }
                });

            });
        }

        if (markers.length > 0) {
            $.ajax({
                url: "/station/get/departure/stations",
                method: "GET",
                data: {
                    stationName: markers[markers.length - 1].getTitle()
                }
            }).done(function (response) {
                availableStations = JSON.parse(response);
            });
        }

    }

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAZl4Ss3m1ac5VAQHmDWtu36oU65iMDeyw&callback=initMap"
        async defer></script>
<script>
    $(".nav-link-routes").css('color', '#FF5A5F');
</script>
</body>
</html>
