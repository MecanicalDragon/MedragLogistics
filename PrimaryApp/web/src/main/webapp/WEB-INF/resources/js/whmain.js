$(document).ready(function () {
    $('.modal').on('shown.bs.modal', function () {
        $(this).find('[autofocus]').focus();
    });

    var table = $('#dto-Table').DataTable();

    $('#dto-Table tbody').on('click', 'tr', 'td', function () {
        var data = table.row(this).data()[0];

//            Set header
        var t = table.row(this).data()[4];
        if (t !== "Undefined") {
            var tr = t.split(">")[1];
            var truck = tr.split("<")[0];
            $("#infoLabel").text("Truck number " + truck + " brigade:");

//            Set body
            var b = data.split('XXX')[2];
            var brigade = b.split('DriverDto');
            var field = $("#infoField");
            $(field).empty();
            brigade.forEach(function (driver) {
                if (driver !== "[") {
//                        alert(driver);
                    var dismemberedDriver = driver.split("'");
                    $(field).html(field.html() + "<div class='row'><div class='col-xs-6 text-left'><h4>"+
                        dismemberedDriver[1] +
                        "</h4></div> <div class='col-xs-6 text-right'><h4>"
                        + dismemberedDriver[3] + " " + dismemberedDriver[5] +
                        "</h4></div></div>");
                }
            });
        }
    });
});