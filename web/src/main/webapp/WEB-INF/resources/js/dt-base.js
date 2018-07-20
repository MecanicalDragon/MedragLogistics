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
// $(document).ready(function () {
//
//     // this is for datatables
//     $('#dto-Table').DataTable({
//         responsive: true
//     });
//
//     // this is for hidden form
//     $(".btn-target-go").click(function () {
//         var index = $(this).attr("id").split('-')[1];
//         $("#targetField").val(index);
//     });
//     var table = $('#dto-Table').DataTable();
//
//     $('#dto-Table tbody').on('click', 'tr', function () {
//         var data = table.row(this).data();
//         alert('You clicked on ' + data[0] + '\'s row');
//     });
// });