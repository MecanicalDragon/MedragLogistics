$(document).ready(function () {

//            Options for driver
    $(".rest").click(function () {
        $("#unitState").val("REST");
        $("#that").trigger("click");
    });
    $(".shift").click(function () {
        $("#unitState").val("ON_SHIFT");
        $("#that").trigger("click");
    });
    $(".drive").click(function () {
        $("#unitState").val("DRIVING");
        $("#that").trigger("click");
    });
    $(".porter").click(function () {
        $("#unitState").val("PORTER");
        $("#that").trigger("click");
    });
    $(".ready").on("click", function () {
        $("#unitState").val("READY_TO_ROUTE");
        $("#that").trigger("click");
    });

//            Options for truck
    $(".in-use").click(function () {
        $("#unitState").val("IN_USE");
        $("#that").trigger("click");
    });
    $(".staying-idle").click(function () {
        $("#unitState").val("STAY_IDLE");
        $("#that").trigger("click");
    });
    $(".in-service").click(function () {
        $("#unitState").val("IN_SERVICE");
        $("#that").trigger("click");
    });

    var table = $('#dto-Table').DataTable();

    $('#dto-Table tbody').on('click', 'tr', 'td', function () {
        var data = table.row(this).data()[0];
        var unitId = data.split('XXX')[1];
        var unitName = data.split('XXX')[2];
        var index = data.split('XXX')[3];

//            Filling editing modal window
        $("#editeddtoId").val(unitId);
        var dto = $("#deldtoButton").text().split(" ")[1];
        $("#editdtoLabel").text("Edit " + dto + " " + unitName);

//            Filling removing modal window
        $("#targetField").val(index);
        $("#deletingdtoQuestion").text("Are you sure you want to remove " + dto + " " + unitName + " from the database?");
        $("#deldtoLabel").text("Removing " + dto + " " + unitName);

//            Filing the changeStateForm
        $("#unitIndex").val(index);
    });

});