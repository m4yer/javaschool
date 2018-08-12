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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datepicker.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/navigation-transparent.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/brand-form.css"/>"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>
    <style>
        .brand-form-wrapper {
            margin-top: 68px;
        }

        @media (max-width: 991px) {
            .brand-form-wrapper {
                margin-top: -60px;
            }
        }
        .button-wrapper button {
            margin: 0 auto;
        }
        .input-warning {
            float: right;
            color: red;
            font-weight: 700;
            font-size: 12px;
            margin-top: 3px;
        }
        .input-success {
            float: right;
            color: #0dff00;
            font-weight: 700;
            font-size: 12px;
            margin-top: 3px;
        }
    </style>
    <title>RW | Register</title>
</head>
<body id="page-top">

<div id="loader"></div>

<div id="pageContent" ng-app="registerApp">
    <!-- Navigation -->
    <%@ include file="navigation.jsp" %>

    <!-- Main content -->
    <header class="masthead text-center text-white d-flex">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <div class="brand-form-wrapper mx-auto">
                        <div class="form-header">
                            <span class="header-caption">Sign Up</span>
                        </div>
                        <c:choose>
                            <c:when test="${param.status == 'error'}">
                                <div class="form-error-message">Something went wrong ;(</div>
                            </c:when>
                        </c:choose>
                        <div class="form-content text-left">
                            <form:form action="/register" method="post" modelAttribute="user" name="registerForm">


                                <div ngShow="registerForm.username.$dirty && !registerForm.username.$valid" ng-messages="registerForm.username.$error">
                                    <div class="input-warning" ng-message="required">Field is required</div>
                                    <div class="input-warning" ng-message="minlength">Too short</div>
                                </div>
                                <div ngShow="registerForm.username.$dirty && !registerForm.username.$valid" ng-messages="registerForm.username">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <label for="username">Username</label>
                                <form:input path="username" type="text" placeholder="Username" id="username"
                                            autocomplete="false" ng-model="username" ng-minlength="4" required="required"/>

                                <div ngShow="registerForm.email.$dirty && !registerForm.email.$valid" ng-messages="registerForm.email">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <div ngShow="registerForm.email.$dirty && !registerForm.email.$valid" ng-messages="registerForm.email.$error">
                                    <div class="input-warning" ng-message="required">Field is required</div>
                                    <div class="input-warning" ng-message="pattern">Invalid Email</div>
                                </div>
                                <label for="email">Email</label>
                                <form:input path="email" type="email" placeholder="Email" id="email" autocomplete="false"
                                            required="required" ng-model="email" ng-pattern="/^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/"/>

                                <div ngShow ng-show="registerForm.password.$dirty && !registerForm.password.$valid" ng-messages="registerForm.password">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <div ngShow="registerForm.password.$dirty && !registerForm.password.$valid" ng-messages="registerForm.password.$error">
                                    <div class="input-warning" ng-message="required">Field is required</div>
                                    <div class="input-warning" ng-message="minlength">At least 6 symbols</div>
                                </div>
                                <label for="password">Password</label>
                                <form:input path="password" type="password" placeholder="Password" id="password" name="password"
                                            autocomplete="off" required="required" ng-model="password" ng-minlength="6"/>

                                <div ngShow="registerForm.rePassword.$dirty && !registerForm.rePassword.$valid" ng-messages="registerForm.rePassword">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <div ngShow="registerForm.rePassword.$dirty && !registerForm.rePassword.$valid" ng-messages="registerForm.rePassword.$error">
                                    <div class="input-warning" ng-message="required">Field is required</div>
                                    <div class="input-warning" ng-message="pattern">Passwords don't match</div>
                                </div>
                                <label for="rePassword">Retype password</label>
                                <input type="password" placeholder="Password again" id="rePassword" autocomplete="off"
                                       required ng-model="rePassword" name="rePassword" ng-pattern="\b{{ password }}\b"/>

                                <div ngShow="registerForm.rePassword.$dirty && !registerForm.rePassword.$valid" ng-messages="registerForm">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <div ngShow="registerForm.rePassword.$dirty && !registerForm.rePassword.$valid" ng-messages="registerForm.$error">
                                    <div class="input-warning" ng-message="required">Fields are required</div>
                                </div>
                                <label for="lastname">Personal information</label>
                                <div class="personal-information">
                                    <form:input path="firstname" type="text" placeholder="Firstname" id="firstname" name="firstname"
                                                autocomplete="off" required="required" ng-model="firstname"/>
                                    <form:input path="lastname" type="text" placeholder="Lastname" id="lastname" name="lastname"
                                                autocomplete="off" required="required" ng-model="lastname"/>
                                    <form:input path="birthday" type="text" placeholder="Birthday" id="birthday" name="birthday"
                                                autocomplete="off" required="required" ng-model="birthday" readonly="true"/>
                                </div>
                                <div class="button-wrapper text-center">
                                    <button disabled class="brand-pink-button" ng-disabled="!registerForm.$valid">Sign Up</button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
</div>
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/resources/js/creative.js" />"></script>
<script src="<c:url value="/resources/js/loading.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-messages.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/registerApp.js" />"></script>
<script>
    $("#birthday").datepicker();
    $("nav").addClass("fixed-top");
</script>
</body>

</html>
