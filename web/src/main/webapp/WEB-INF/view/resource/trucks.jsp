<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Trucks Page
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">

    <%--Heading Tab Panel--%>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-city">Cities</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Drivers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="${contextPath}/rsm-truck">Trucks</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Users</a>
        </li>
    </ul>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">

                    <c:choose>
                        <c:when test="${err}">
                            <%--Add new Truck Error Button--%>
                            <button class="btn btn-danger" data-toggle="modal"
                                    data-target="#addNewTruckModal">Wasn't added!
                            </button>
                        </c:when>
                        <c:otherwise>
                            <%--Add new Truck Button--%>
                            <button class="btn btn-primary" data-toggle="modal"
                                    data-target="#addNewTruckModal">Add new Truck
                            </button>
                        </c:otherwise>
                    </c:choose>

                </div>

                <%--Trucks Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="trucks-Table">
                        <thead>
                        <tr>
                            <th>Truck reg. number</th>
                            <th>Brigade str.</th>
                            <th>Capacity</th>
                            <th>Current city</th>
                            <th>Status</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.truckList}" var="truckUnit">
                            <tr class="odd gradeX">
                                <td>${truckUnit.regNumber}</td>
                                <td>${truckUnit.brigadeStr}</td>
                                <td>${truckUnit.capacity}</td>
                                <td>${truckUnit.currentCity.name}</td>
                                <td>${truckUnit.state}</td>
                                <td>
                                    <a type="button" class="btn btn-warning btn-xs" href="${contextPath}/rsm-truck/edit/${truckUnit.id}">Edit truck data</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs" href="${contextPath}/rsm-truck/remove/${truckUnit.id}">Remove truck</a>
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
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>

</div>

<!-- Modal window add city-->
<div class="modal fade" id="addNewTruckModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add new Truck</h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="addNewTruckForm" method="post" modelAttribute="truck"
                           action="${contextPath}/rsm-truck/addTruck">

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="regNumber">
                                <form:input name="regNumber" placeholder="Truck register number" path="regNumber" autofocus="true"
                                            class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="regNumber"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="brigadeStr">
                                <form:input name="brigadeStr" placeholder="Brigade strength" path="brigadeStr" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="brigadeStr"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="capacity">
                                <form:input name="capacity" placeholder="capacity" path="capacity" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="capacity"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="state">
                                <form:input name="state" placeholder="state" path="state" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="state"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="state">
                                <form:input name="currentCity" placeholder="current city" path="currentCity" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="currentCity"/>
                            </div>
                        </div>
                    </div>

                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="addNewTruckForm">Add new truck</button>
            </div>
        </div>
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
        $('#trucks-Table').DataTable({
            responsive: true
        });
    });
</script>


</body>
</html>