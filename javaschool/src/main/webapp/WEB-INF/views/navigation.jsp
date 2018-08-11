<nav class="navbar navbar-expand-sm navbar-light" id="mainNav">
    <div class="container">
        <a class="navbar-brand" href="/">Rail Travel</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="/user/schedule"><i class="fa fa-calendar"></i> Schedule</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="/trip/find/"><i class="fa fa-search"></i> Find Trip</a>
                </li>
                <sec:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/register">Sign Up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/login">Log In</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/logout"><i class="fa fa-user-circle"></i> <sec:authentication property="principal.firstname" /></a>
                        <div class="dropdown-content dropdown-upper">
                            <a href="/user/ticket/list">My Tickets</a>
                            <a href="/logout">Logout</a>
                        </div>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>