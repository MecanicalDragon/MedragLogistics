$(document).ready(function () {

    // this is for datatables
    $('#dto-Table').DataTable({
        responsive: true
    });

    // handler fo sending new password
    $(".btn-newPassword").click(function () {
        var buttonId = $(this).attr("id");
        var arr = buttonId.split('/');
        $("#newPasswordId").val(arr[0]);
        $("#newPasswordQuestion").text("Are you sure you want to generate new password for user number " + arr[1] + " and send it on email " + arr[2]);
    });

    // handler of removing form
    $(".btn-remove").click(function () {
        var buttonId = $(this).attr("id");
        var arr = buttonId.split('*');
        $("#removableUserId").val(arr[0]);
        $("#removableUserName").val(arr[1]);
        $("#deletingdtoQuestion").text("Are you sure you want to remove user number " + arr[1] + " from the database?");
    });
});