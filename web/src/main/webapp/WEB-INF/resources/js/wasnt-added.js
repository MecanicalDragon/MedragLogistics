$(document).ready(function () {
    $(function () {
        if ($(this).attr("err") !== null) {
            $("#wasntAdded").trigger("click");
        }
    });
    $(function () {
        if ($(this).attr("editErr") !== null) {
            $("#editButton").trigger("click");
        }
    });
});