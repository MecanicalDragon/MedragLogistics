$(document).ready(function () {
    // this is for datatables
    // $('#dto-Table').DataTable({
    //     responsive: true,
    //     lengthMenu: [5, 10, 25, 50],
    //     pageLength: 25
    // });

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

    $(function () {
        if ($(this).attr("err") !== null) {
            $("#wasntAdded").trigger("click");
        }
    });

    // var table = $('#dto-Table').DataTable();
    //
    // $('#dto-Table tbody').on('click', 'tr', 'td', function () {
    //     var data = table.row(this).data();
    //     alert(data[0]);
    //     var id = data[0].split("XXX")[1];
    //     var name = data[0].split("XXX")[2];
    //     alert(id);
    //     alert(name);
    //     $("#editeddtoId").val(id);
    //     $("#editdtoLabel").text("Edit " + name + " " + arr[1]);
    // });
});
