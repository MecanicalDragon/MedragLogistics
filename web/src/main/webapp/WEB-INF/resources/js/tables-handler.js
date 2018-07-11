$(document).ready(function () {
    // this is for datatables
    $('#dto-Table').DataTable({
        responsive: true
    });

    // handler-script for edit button
    $(".btn-edit").click(function () {
        var buttonId = $(this).attr("id");
        var arr = buttonId.split('/');
        $("#editeddtoId").val(arr[0]);
        var dto = $("#deldtoButton").text().split(" ")[1];
        $("#editdtoLabel").text("Edit " + dto + " " + arr[1]);
    });

    // handler-script for remove button
    $(".btn-remove").click(function () {
        var buttonId = $(this).attr("id");
        var arr = buttonId.split('*');
        var del = $("#deldtoButton");
        var dto = del.text().split(" ")[1];
        del.attr("href", "/rsm-" + dto + "/remove/" + arr[0]);
        $("#deletingdtoQuestion").text("Are you sure you want to remove " + dto + " " + arr[1] + " from the database?");
        $("#deldtoLabel").text("Removing " + dto + " " + arr[1]);
    });
});
