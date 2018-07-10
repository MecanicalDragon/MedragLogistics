$(document).ready(function () {
    $('#dto-Table').DataTable({
        responsive: true
    });
});
$(document).ready(function () {
    $(".btn-newPassword").click(function () {
        var buttonId = $(this).attr("id");
        var arr = buttonId.split('/');
        $("#newPasswordId").val(arr[0]);
        $("#newPasswordQuestion").text("Are you sure you want to generate new password for user number " + arr[1] + " and send it on email " + arr[2]);
    });
});
$(document).ready(function () {
    $(".btn-remove").click(function () {
        var buttonId = $(this).attr("id");
        var arr = buttonId.split('*');
        $("#removableUserForm").val(arr[0]);
        $("#deletingdtoQuestion").text("Are you sure you want to remove user number " + arr[1] + " from the database?");
    });
});