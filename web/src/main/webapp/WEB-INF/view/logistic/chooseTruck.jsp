<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Choose the truck
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">

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

                        <h1>Step 1: choose a truck for delivery.</h1>

                        <h3>List of working trucks in city ${sessionScope.truckLoad[0].currentCityName},
                            that aren't reserved in other orders</h3>


                    </div>

                </div>

                <%--Table--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>Choose truck</th>
                            <th>Truck reg. number</th>
                            <th>Brigade str.</th>
                            <th>Capacity, weight of cargo = ${sessionScope.currentWeight}kgs.</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.trucksInCity}" var="truckUnit" varStatus="index">
                            <tr class="odd gradeX">
                                <td>
                                    <a class="button btn-success btn-sm" role="button"
                                       href="${contextPath}/mgr-wp/confirmTruck/${index.index}">
                                        Choose this
                                    </a>
                                </td>
                                <td>${truckUnit.regNumber}</td>
                                <td>${truckUnit.brigadeStr}</td>
                                <td>${truckUnit.capacity}</td>

                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
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
            $('#dto-Table').DataTable({
                responsive: true
            });
        });
    </script>

</body>
</html>
