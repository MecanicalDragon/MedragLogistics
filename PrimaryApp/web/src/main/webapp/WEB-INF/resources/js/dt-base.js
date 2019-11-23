$(document).ready(function () {

    $('#dto-Table').DataTable({
        responsive: true
    });

    var table = $('#dto-Table').DataTable();

    $('#dto-Table tbody').on('click', 'tr', 'td', function () {
        var data = table.row(this).data();
        var index = data[0].split("XXX")[1];
        $("#targetField").val(index);
    });
});