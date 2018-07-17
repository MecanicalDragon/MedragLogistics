$(document).ready(function () {
    var totalLoad = 0;
    $(".btn-choose").click(function () {
        var idButton = $(this).attr("id");
        var index = $(this).attr("id").split('-')[1];
        var value = $("#cargoHiddenField").val();
        var weight = $(this).attr("id").split('-')[2];
        var intWeight = parseInt(weight, 10);

        if (document.getElementById(idButton).classList.contains('btn-success')) {

            totalLoad += intWeight;
            document.getElementById(idButton).classList.remove('btn-success');
            document.getElementById(idButton).classList.add('btn-danger');
            $(this).text("Remove");
            value = value + index + "/";
            $("#cargoHiddenField").val(value);

            if (capacity < totalLoad) {
                $('#compile').prop('disabled', true);
            }

        } else {

            totalLoad -= intWeight;
            document.getElementById(idButton).classList.remove('btn-danger');
            document.getElementById(idButton).classList.add('btn-success');
            $("#" + idButton).text("Add this");

            value = value.replace("" + index + "/", "");
            $("#cargoHiddenField").val(value);
            if (capacity > totalLoad) {
                $('#compile').prop('disabled', false);
            }
        }

        $("#msg").text("Now loaded " + totalLoad + " kgs of " + capacity + " total capacity.");
    });
    $(".chosenCargo").trigger("click");
});