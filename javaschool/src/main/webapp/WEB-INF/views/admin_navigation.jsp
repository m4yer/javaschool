<nav class="navbar navbar-expand-sm navbar-light" id="adminNav">
    <div class="container">
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarAdmin" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarAdmin">
            <ul class="navbar-nav mx-auto">

                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger nav-link-trips" href="#"><i class="fa fa-calendar"></i> Trips</a>
                    <div class="dropdown-content">
                        <a href="/admin/trip/list">Manage</a>
                        <a href="/admin/route/list">Create</a>
                    </div>
                </li>

                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger nav-link-routes" href="#"><i class="fa fa-exchange"></i> Routes</a>
                    <div class="dropdown-content">
                        <a href="/admin/route/list">Manage</a>
                        <a href="/admin/route/add">Create</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger nav-link-stations" href="#"><i class="fa fa-map-marker"></i> Stations</a>
                    <div class="dropdown-content">
                        <a href="/admin/station/list">Manage</a>
                        <a href="/admin/station/add">Add</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger nav-link-roads" href="#"><i class="fa fa-road"></i> Roads</a>
                    <div class="dropdown-content">
                        <a href="/admin/directions/map">Manage</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger nav-link-trains" href="#"><i class="fa fa-train"></i> Trains</a>
                    <div class="dropdown-content">
                        <a href="/admin/train/list">Manage</a>
                        <a href="/admin/train/add">Add</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger nav-link-users" href="#"><i class="fa fa-users"></i> Users</a>
                    <div class="dropdown-content">
                        <a href="/admin/user/list">Manage</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>