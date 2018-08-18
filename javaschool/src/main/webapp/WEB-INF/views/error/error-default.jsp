<%@ include file="../includes.jsp" %>
<html>
<link rel="icon" type="image/png" href="<c:url value="/resources/img/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form-modal.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ticket-list.css" />"/>
<title>RW | 404</title>
<style>
    .brand-pink-button {
        font-size: 12px;
    }
    .brand-pink-button:hover {
        text-decoration: none;
        color: #414141;
    }
    .brand-dark-grey-button {
        font-size: 12px;
    }

    .brand-dark-grey-button:hover {
        color: #FF5A5F;
        text-decoration: none;
    }
    .info-page-center {
        margin-top: 5%;
    }
</style>
<body>

<div id="loader"></div>

<div id="pageContent">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12" style="margin-top: 24px;">
                <div class="info-page-center">
                    <img src="<c:url value="/resources/img/error-pic.png" />" /><br>
                    Something went wrong...
                    <%--but you can:--%>
                    <br><br>
                    <a href="/" class="brand-dark-grey-button" >BACK HOME</a>
                    <a href="/trip/find/" class="brand-pink-button" >FIND TRIPS</a>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script>
    pageLoaded();
</script>
</body>
</html>
