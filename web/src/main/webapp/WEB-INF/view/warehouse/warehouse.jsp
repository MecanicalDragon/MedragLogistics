<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Medrag Logistics Warehouse Page
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet"
          type="text/css">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <%--This button starts a process of adding new orderr.--%>
                        <%--First step is adding new customer or choosing one of added earlier.--%>
                        <%--goto .../warehouse/CustomerController, GetMethod--%>
                        <a class="btn btn-primary" href="${contextPath}/whm-newCustomer" role="button">Add new
                            orderr</a>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <table width="100%" class="table table-striped table-bordered table-hover" id="orderr-Table">
                            <thead>
                            <tr>
                                <th>Cargo index</th>
                                <th>Destination point</th>
                                <th>Cargo state</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${sessionScope.globalCargoes}" var="cargo">
                                <tr class="odd gradeX">
                                    <td>${cargo.cargoIndex}</td>
                                    <td>${cargo.destination.name}</td>
                                    <td>
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-default btn-xs dropdown-toggle"
                                                    data-toggle="dropdown">
                                                    ${cargo.state}
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu pull-right" role="menu">
                                                <li><a href="${contextPath}/whm-cargo/changeState?id=${cargo.id}&op=2">
                                                    On the way</a>
                                                </li>
                                                <li><a href="${contextPath}/whm-cargo/changeState?id=${cargo.id}&op=3">
                                                    At transfer point</a>
                                                </li>
                                                <li><a href="${contextPath}/whm-cargo/changeState?id=${cargo.id}&op=4">
                                                    Delivered</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </td>
                                    <%--<script>--%>
                                        <%--function successFunction() {--%>

                                        <%--}--%>

                                        <%--$(document).ready(function () {--%>
                                            <%--$("#deliver").bind("click", function () {--%>
                                                <%--var cargoUnit = ${cargo.id};--%>
                                                <%--$.ajax ({--%>
                                                    <%--url:"${contextPath}/whm-cargo/deliver",--%>
                                                    <%--type: "GET",--%>
                                                    <%--data: ({name: cargoUnit}),--%>
                                                    <%--dataType: "html",--%>
<%--//                                                   beforeSend: funcBefore,--%>
                                                    <%--success: successFunction--%>
                                                <%--});--%>
                                            <%--});--%>
                                        <%--});--%>
                                    <%--</script>--%>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="/resources/vendor/jquery/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

        <!-- DataTables JavaScript -->
        <script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
        <script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
        <script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="/resources/dist/js/sb-admin-2.js"></script>

        <!-- Page-Level Demo Scripts - Tables - Use for reference -->
        <script>
            $(document).ready(function () {
                $('#orderr-Table').DataTable({
                    responsive: true
                });
            });
        </script>

    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
