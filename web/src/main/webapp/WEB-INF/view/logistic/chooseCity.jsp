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
        Choose city
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

</head>
<body>
<br>
<div class="container">

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">

                    <div class="text-center">

                        <h1>Step 3: Choose the next destination city.</h1>

                    </div>

                </div>
                <%--Cities Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>Choose</th>
                            <th>City name</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.cities}" var="cityUnit" varStatus="index">

                            <tr class="odd gradeX">
                                <td>
                                    <a href="/mgr-wp/confirmCity/${index.index}" class="btn btn-success btn-xs"
                                       type="button">Choose this</a>
                                </td>
                                <td>${cityUnit.name}</td>
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

<script>
    $(document).ready(function () {
        // this is for datatables
        $('#dto-Table').DataTable({
            responsive: true
        });
    });
</script>


</body>
</html>