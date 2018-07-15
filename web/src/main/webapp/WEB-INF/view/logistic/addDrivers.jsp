<!DOCTYPE html>
<%@ page buffer="16kb" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Drivers Page
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

</head>
<body>
<br>
<div class="container">

    <form action="${contextPath}/mgr-wp/compileWP" method="POST" id="driverForm">
        <input type="hidden" id="driverHiddenField" name="drivers" value="">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">

                    <div class="text-center">

                        <h1>Step 4: compile a brigade.</h1>

                        <h3 id="msg">Now assigned 0 of ${sessionScope.brigade} drivers.</h3>

                    </div>

                </div>

                <%--Driver Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>Choose</th>
                            <th>Personal Number</th>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Worked time</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.drivers}" var="driverUnit" varStatus="index">

                            <tr class="odd gradeX">
                                <td>
                                    <button class="btn btn-sm btn-choose btn-enabled btn-success" id="choose-${index.index}">
                                        Assign
                                    </button>
                                </td>
                                <td>${driverUnit.personalNumber}</td>
                                <td>${driverUnit.name}</td>
                                <td>${driverUnit.surname}</td>
                                <td>${driverUnit.workedTime}</td>

                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
                    <div class="text-center">
                        <a class="btn btn-danger" href="${contextPath}/mgr-main" role="button">Dismiss</a>


                        <button class="btn btn-success" form="driverForm" id="compile" disabled>Compile</button>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>

</div>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>

<script>
    $(document).ready(function () {
        // this is for datatables
        $('#dto-Table').DataTable({
            responsive: true
        });
    });
</script>
<script>
    $(document).ready(function () {
        var totalDrivers = 0;
        var neededDrivers = parseInt("${sessionScope.brigade}", 10);
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
</script>

</body>
</html>