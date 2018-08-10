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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-transparent.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form.css"/>"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>
    <style>
        .brand-form-wrapper {
            margin-top: 168px;
        }
        @media (max-width: 991px) {
            .brand-form-wrapper {
                margin-top: 10px;
            }
        }
    </style>
    <title>RW | Login</title>
</head>

<body id="page-top">

<div id="loader"></div>

<div id="pageContent">
    <!-- Navigation -->
    <%@ include file="navigation.jsp" %>

    <!-- Main content -->
    <header class="masthead text-center text-white d-flex">

        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <div class="brand-form-wrapper mx-auto">
                        <div class="form-header">
                            <span class="header-caption">Sign In</span>
                        </div>
                        <c:choose>
                            <c:when test="${param.status == 'error'}">
                                <div class="form-error-message">Invalid username or password</div>
                            </c:when>
                        </c:choose>
                        <div class="form-content text-left">

                            <form action="/signin" method="post" >

                                <label for="username">Username</label>
                                <input type="text" placeholder="Username" name="username" id="username" autocomplete="false" required/>

                                <label for="password">Password</label>
                                <input type="password" placeholder="Password" name="password" id="password" required/>

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <input type="submit" value="Sign In" class="mx-auto"/>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>



    </header>
</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/creative.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script>
    $("nav").addClass("fixed-top");
    pageLoaded();
</script>
</body>
</html>
