function pageLoaded() {
    document.getElementById("loader").style.display = "none";
    document.getElementById("pageContent").style.display = "block";
}

function pageLoading() {
    document.getElementById("loader").style.display = "block";
    document.getElementById("pageContent").style.display = "none";
}