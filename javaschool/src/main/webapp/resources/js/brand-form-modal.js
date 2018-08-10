var modal = $("#brand-form-modal");

function openModalForm() {
    modal.css('display', 'block');
}

$(".close").click(function () {
    modal.css('display', 'none');
});

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    var modal = document.getElementById('brand-form-modal');
    if (event.target == modal) {
        $("#brand-form-modal").css('display', 'none');
    }
};