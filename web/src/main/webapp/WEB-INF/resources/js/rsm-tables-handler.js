$(document).ready(function () {
    var table = $('#dto-Table').DataTable();

    $('#dto-Table tbody').on('click', 'tr', 'td', function () {
        var data = table.row(this).data()[0];
        var unitId = data.split('XXX')[1];
        var unitName = data.split('XXX')[2];

//            Filling editing modal window
        $("#editeddtoId").val(unitId);
        var dto = $("#deldtoButton").text().split(" ")[1];
        $("#editdtoLabel").text("Edit " + dto + " " + unitName);

//            Filling removing modal window
        $("#deldtoButton").attr("href", "/rsm-" + dto + "/remove/" + unitId);
        $("#deletingdtoQuestion").text("Are you sure you want to remove " + dto + " " + unitName + " from the database?");
        $("#deldtoLabel").text("Removing " + dto + " " + unitName);

    });

});