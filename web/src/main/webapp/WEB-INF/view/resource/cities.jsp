<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Cities Page
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
            <a class="nav-link active" href="${contextPath}/rsm-city">Cities</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Drivers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-truck">Trucks</a>
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
                            <%--Add new City Error Button--%>
                            <button class="btn btn-danger" data-toggle="modal"
                                    data-target="#addNewCityModal">Wasn't added!
                            </button>
                        </c:when>
                        <c:otherwise>
                            <%--Add new City Button--%>
                            <button class="btn btn-primary" data-toggle="modal"
                                    data-target="#addNewCityModal">Add new City
                            </button>
                        </c:otherwise>
                    </c:choose>

                </div>

                <%--Cities Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="cities-Table">
                        <thead>
                        <tr>
                            <th>City name</th>
                            <th>City index</th>
                            <th>Coordinates X</th>
                            <th>Coordinates Y</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.cities}" var="cityUnit">
                            <tr class="odd gradeX">
                                <td>${cityUnit.name}</td>
                                <td>${cityUnit.index}</td>
                                <td>${cityUnit.coordinatesX}</td>
                                <td>${cityUnit.coordinatesY}</td>
                                <td>
                                    <a type="button" class="btn btn-warning btn-xs" href="${contextPath}/rsm-city/edit/${cityUnit.id}">Edit city data</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs" href="${contextPath}/rsm-city/remove/${cityUnit.id}">Remove city</a>
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
<div class="modal fade" id="addNewCityModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add new City</h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="addNewCityForm" method="post" modelAttribute="city"
                           action="${contextPath}/rsm-city/addCity">

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="name">
                                <form:input name="name" placeholder="City" path="name" autofocus="true"
                                            class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="name"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="index">
                                <form:input name="index" placeholder="index" path="index" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="index"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="coordinatesX">
                                <form:input name="x" placeholder="X" path="coordinatesX" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="coordinatesX"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="coordinatesY">
                                <form:input name="y" placeholder="Y" path="coordinatesY" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="coordinatesY"/>
                            </div>
                        </div>
                    </div>

                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="addNewCityForm">Add new City</button>
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
        $('#cities-Table').DataTable({
            responsive: true
        });
    });
</script>


</body>
</html>