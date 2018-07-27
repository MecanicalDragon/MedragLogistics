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
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

</head>
<body>
<br>
<div class="container">

                    <form class="form" id="refresh" method="post" action="${contextPath}/whm-wp/actual">
                            <input type="hidden" name="name" value="${sessionScope.warehouseOfCity}"/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="text-center">
                    <h1>Warehouse of city ${sessionScope.warehouseOfCity}</h1>
                    </div>
                    <%--To cargo page button--%>
                    <a href = ${contextPath}/whm-main type="button" class="btn btn-primary" role="button">To cargo page</a>
                    <button class="btn btn-warning" form="refresh">Refresh</button>

                        <%--Logout button--%>
                    <div class="pull-right">
                        <form method="post" action="${contextPath}/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                            <button class="btn btn-danger offset-xs-6">Logout</button>
                        </form>
                    </div>

                </div>

                <%--Driver Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>Truck number</th>
                            <th>Cargo index</th>
                            <th>LOAD/UNLOAD</th>
                            <th>CHECK</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.wps}" var="wp" varStatus="index">

                            <tr class="odd gradeX">
                                <td>${wp.currentTruck.regNumber}</td>
                                <td>${wp.cargo.index}</td>
                                <td>${wp.wayPointType}</td>
                                <td>
                                    <a type="button" class="btn btn-success btn-xs btn-wp"
                                       href="${contextPath}/whm-wp/complete/${index.index}">Complete</a>
                                </td>
                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>

</div>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function () {
        $('#dto-Table').DataTable({
            responsive: true
        });
    });
</script>
</body>
</html>
