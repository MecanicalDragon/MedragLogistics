$(document).ready(function () {

    var table = $('#dto-Table').DataTable();

    $('#dto-Table tbody').on('click', 'tr', 'td', function () {
        var data = table.row(this).data()[0];
        var unitId = data.split('XXX')[1];
        var unitName = data.split('XXX')[2];
        var unitEmail = data.split('XXX')[3];
        var index = data.split('XXX')[4];

//            Filling new password modal window
        $("#newPasswordId").val(unitId);
        $("#newPasswordQuestion").text("Are you sure you want to generate new password for user number " + unitName + " and send it on email " + unitEmail);


//            Filling removing modal window
        $("#userIndex").val(index);
        $("#deletingdtoQuestion").text("Are you sure you want to remove user number " + unitName + " from the database?");

    });

});