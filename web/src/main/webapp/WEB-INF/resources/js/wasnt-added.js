$(document).ready(function () {
    $(function () {
        if ($(this).attr("err") !== null) {
            $("#wasntAdded").trigger("click");
        }
    });
});