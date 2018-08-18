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
            margin-top: 84px;
        }

        @media (max-width: 991px) {
            .brand-form-wrapper {
                margin-top: -60px;
            }
        }

        .example {
            width: 33%;
            min-width: 400px;
            padding: 15px;
            display: inline-block;
            box-sizing: border-box;
            text-align: center;
        }

        .example input {
            display: block;
            margin: 0 auto 20px auto;
            width: 150px;
            padding: 8px 10px;
            border: 1px solid #CCCCCC;
            border-radius: 3px;
            background: #F2F2F2;
            text-align: center;
            font-size: 1em;
            letter-spacing: 0.02em;
            font-family: "Roboto Condensed", helvetica, arial, sans-serif;
        }

        .example select {
            padding: 10px;
            background: #ffffff;
            border: 1px solid #CCCCCC;
            border-radius: 3px;
            margin: 0 3px;
        }

    </style>
    <title>RW | Register</title>
</head>
<body id="page-top" ng-app="registerApp" >

<div id="loader"></div>

<div id="pageContent" ng-controller="registerCtrl">
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

                        <div class="form-error-message">xaxa</div>
                        <div class="form-content text-left">
                            <form:form action="/register" method="post" modelAttribute="user" name="registerForm" id="registerForm">


                                <div ngShow="registerForm.username.$dirty && !registerForm.username.$valid" ng-messages="registerForm.username.$error">
                                    <div class="input-warning" ng-message="required">Field is required</div>
                                    <div class="input-warning" ng-message="minlength">At least 4 symbols</div>
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
                                            required="required" ng-model="email" ng-pattern="/^[a-zA-Z]+[a-zA-Z0-9._]+@[a-zA-Z]+\.[a-zA-Z.]{2,5}$/"/>

                                <div ngShow ng-show="registerForm.password.$dirty && !registerForm.password.$valid" ng-messages="registerForm.password">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <div ngShow="registerForm.password.$dirty && !registerForm.password.$valid" ng-messages="registerForm.password.$error">
                                    <div class="input-warning" ng-message="required">Field is required</div>
                                    <div class="input-warning" ng-message="pattern">Only digits and characters</div>
                                    <div class="input-warning" ng-message="minlength">At least 6 symbols</div>
                                </div>
                                <label for="password">Password</label>
                                <form:input path="password" type="password" placeholder="Password" id="password" name="password"
                                            autocomplete="off" required="required" ng-keyup="checkPasswordMatching()" ng-model="password" ng-minlength="6" pattern="[a-zA-Z0-9]+"/>

                                <div id="retypePasswordValid" class="input-success" style="display: none;">&#10004;</div>
                                <div id="retypePasswordNotMatch" class="input-warning" style="display: none;">Passwords does not match</div>
                                <div id="retypePasswordCheckFirstInput" class="input-warning" style="display: block;">Fix password</div>
                                <label for="rePassword">Retype password</label>
                                <input type="password" placeholder="Password again" id="rePassword" autocomplete="off"
                                       required ng-model="rePassword" name="rePassword" ng-keyup="checkPasswordMatching()" />

                                <div ngShow="registerForm.firstname.$dirty && !registerForm.firstname.$valid" ng-messages="registerForm">
                                    <div class="input-success" ng-message="$valid">&#10004;</div>
                                </div>
                                <div ngShow="registerForm.firstname.$dirty && !registerForm.firstname.$valid" ng-messages="registerForm.$error">
                                    <div class="input-warning" ng-message="required">Fields are required</div>
                                </div>
                                <label for="lastname">Personal information</label>
                                <div class="personal-information">
                                    <form:input path="firstname" type="text" placeholder="Firstname" id="firstname" name="firstname"
                                                autocomplete="off" required="required" ng-model="firstname"/>
                                    <form:input path="lastname" type="text" placeholder="Lastname" id="lastname" name="lastname"
                                                autocomplete="off" required="required" ng-model="lastname"/>
                                </div>
                                <div class="example">
                                    <form:input path="birthday" type="text" id="birthday" ng-model="birthday" name="birthday" required="required"/>
                                </div>
                                <div class="button-wrapper text-center">
                                    <button disabled ng-disabled="!registerForm.$valid || !retypeMatch" id="btn-reg-submit" class="brand-pink-button">Sign Up</button>
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
<script src="<c:url value="/resources/js/jquery.date-dropdowns.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/angular-messages.min.js" />"></script>
<script src="<c:url value="/resources/js/angular/registerApp.js" />"></script>
<script>
    $("#birthday").dateDropdowns({
        submitFormat: "mm/dd/yyyy",
        required: true
    });
    $("#birthday").prop('type', 'text');
    $("#birthday").css('display', 'none');
    var needCheck = true;
    $("#registerForm").submit(function (e) {
        if (needCheck) {
            e.preventDefault();
            var username = $("#username").val();
            $.ajax({
                url: "/register/is-allowed/username",
                method: "GET",
                data: {
                    username: username
                }
            }).done(function (response) {
                if (response == false) {
                    // sorry such username already exist in db
                    $('.form-error-message').html("Such username already exist.");
                    $('.form-error-message').css('display', 'block');
                } else if (response == true) {
                    $('.form-error-message').css('display', 'none');
                    var email = $("#email").val();
                    $.ajax({
                        url: "/register/is-allowed/email",
                        method: "GET",
                        data: {
                            email: email
                        }
                    }).done(function (response) {
                        if (response == false) {
                            $('.form-error-message').html("Such email already exist.");
                            $('.form-error-message').css('display', 'block');
                        } else if (response == true) {
                            // allow registration
                            $('.form-error-message').css('display', 'none');
                            needCheck = false;
                            $('#registerForm').submit();
                        }
                    });
                }
            });
        } else {
            this.submit();
        }
    });
    $("#birthday").datepicker({
        startDate: '-100y',
        endDate: '-8y',
        startView: 3
    });
    $("nav").addClass("fixed-top");
    // checkPasswordsMatching();
</script>
</body>

</html>
