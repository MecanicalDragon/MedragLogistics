$(document).ready(function () {
    var totalDrivers = 0;
    $(".btn-choose").click(function () {
        var idButton = $(this).attr("id");
        var index = $(this).attr("id").split('-')[1];
        var value = $("#driverHiddenField").val();

        if (document.getElementById(idButton).classList.contains('btn-success')) {

            totalDrivers++;
            document.getElementById(idButton).classList.remove('btn-success');
            document.getElementById(idButton).classList.remove('btn-enabled');
            document.getElementById(idButton).classList.add('btn-danger');
            $(this).text("Remove");
            value = value + index + "/";
            $("#driverHiddenField").val(value);

            if (totalDrivers === neededDrivers) {
                $('#compile').prop('disabled', false);
                $(".btn-enabled").prop('disabled', true);
            }

        } else {

            totalDrivers--;
            document.getElementById(idButton).classList.remove('btn-danger');
            document.getElementById(idButton).classList.add('btn-success');
            document.getElementById(idButton).classList.add('btn-enabled');
            $("#"+idButton).text("Assign");

            value = value.replace("" + index + "/", "");
            $("#driverHiddenField").val(value);
            $('#compile').prop('disabled', true);
            $(".btn-enabled").prop('disabled', false);
        }

        $("#msg").text("Now assigned " + totalDrivers + " of " + neededDrivers + " drivers.");
    });
});