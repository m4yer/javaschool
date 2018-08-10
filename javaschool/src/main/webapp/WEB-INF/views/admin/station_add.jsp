<%@ include file="../includes.jsp" %>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-white.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-admin.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form.css" />"/>
<title>RW | New station</title>
<body>

<div id="loader"></div>

<div id="pageContent">

    <%@ include file="../navigation.jsp" %>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <%@ include file="../admin_navigation.jsp" %>
    </sec:authorize>

    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="brand-form-wrapper mx-auto">
                    <div class="form-header">
                        <span class="header-caption">NEW STATION</span>
                    </div>
                    <div class="form-content text-left">

                        <form:form modelAttribute="station" action="/admin/station/add" method="post" >

                            <label for="name">Title</label>
                            <form:input path="name" type="text" placeholder="Title" name="name" id="name" autocomplete="false"/>

                            <label for="latitude">Latitude</label>
                            <form:input path="latitude" type="text" placeholder="Latitude" name="latitude" id="latitude" autocomplete="false"/>

                            <label for="longitude">Longitude</label>
                            <form:input path="longitude" type="text" placeholder="Longitude" name="longitude" id="longitude" autocomplete="false"/>

                            <input type="submit" value="Add" class="mx-auto"/>

                        </form:form>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script>
    $(".nav-link-trains").css('color', '#FF5A5F');
    pageLoaded();
</script>
</body>
</html>
