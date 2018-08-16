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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/awesomplete.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-transparent.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker.min.css"/>"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>
    <title>RW | Home</title>
</head>
<body id="page-top">
<div id="loader"></div>
<div id="pageContent" ng-app="indexManageApp" ng-controller="indexManageCtrl">

    <!-- Navigation -->
    <%@ include file="navigation.jsp" %>

    <!-- Main content -->
    <header class="masthead text-center text-white d-flex" style="position: relative;">
        <div class="container-fluid my-auto">
            <div class="row">
                <div class="col-lg-10 mx-auto">
                    <h1 class="text-uppercase">
                        <strong>Discover the world</strong>
                    </h1>
                    <hr>
                </div>
                <div class="col-lg-8 mx-auto">
                    <p class="text-faded mb-5">Start your journey with the most intelligent platform for planning your
                        route
                    </p>
                </div>
                <div class="col-lg-7 mx-auto" style="background-color: rgba(0,0,0,0.82); height: 100px;margin: 0 auto; border-radius: 4px;">
                    <div class="center-vert-horz index-input-line">
                        <table style="margin: 0 auto;">
                            <tr>
                                <td>
                                    <form action="/trip/find/" method="get" >
                                        <div class="input-group">
                                            <span class="autocomplete-from">
                                                <input type="text" placeholder="From" class="input-place" name="stationFrom" required/>
                                            </span>
                                            <span class="autocomplete-to">
                                                <input type="text" placeholder="To" class="input-place autocomplete-to" name="stationTo" required
                                                       style="
                                                   border-top-left-radius: 0px;
                                                   border-bottom-left-radius: 0px;"/>
                                            </span>
                                            <input type="text" placeholder="Date" id="datepicker1" class="input-date" name="dateStart" required autocomplete="off" onkeydown="return false;"/>
                                            <input type="text" placeholder="Date" id="datepicker2" class="input-date" name="dateEnd" required autocomplete="off" onkeydown="return false;"/>
                                            <input type="submit" value="Search"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </header>

</div>
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/creative.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/awesomplete.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script>
    $("#datepicker1").datepicker({
        startDate: 'now',
        endDate: '+1m'
    });
    $("#datepicker2").datepicker({
        startDate: 'now',
        endDate: '+1m'
    });
    $("nav").addClass("fixed-top");
</script>
<script src="<c:url value="/resources/js/angular/indexManageApp.js" />"></script>
<script src="<c:url value="/resources/js/angular/indexManageCtrl.js" />"></script>
</body>
</html>
