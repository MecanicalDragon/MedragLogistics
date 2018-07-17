$(document).ready(function () {

    // this is for datatables
    $('#dto-Table').DataTable({
        responsive: true
    });

    // this is for hidden form
    $(".btn-target-go").click(function () {
        var index = $(this).attr("id").split('-')[1];
        $("#targetField").val(index);
    });
});