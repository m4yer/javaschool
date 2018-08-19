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
                <div class="col-lg-12 mx-auto">
                    <p class="text-faded mb-5">Start your journey with the most intelligent platform for planning your
                        route
                    </p>
                </div>
                <div class="col-lg-3 mx-auto" style="background-color: rgba(0,0,0,0.72); height: 100px;margin: 0 auto; border-radius: 4px;">
                    <div class="center-vert-horz index-input-line">
                        <table style="margin: 0 auto;">
                            <tr>
                                <td>
                                    <form action="/user/trip/find/" method="get" >
                                        <div class="input-group">
                                            <input type="submit" value="Search trips" style="width: 150px;"/>
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
<script>
    $("nav").addClass("fixed-top");
    pageLoaded();
</script>
</body>
</html>
