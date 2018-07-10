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
<div class="container">

    <%--Heading Tab Panel--%>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="${contextPath}/rsm-city">Cities</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-driver">Drivers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-truck">Trucks</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-user">Users</a>
        </li>
    </ul>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">

                    <c:choose>
                        <c:when test="${err}">
                            <%--Add new Driver Error Button--%>
                            <button class="btn btn-danger" data-toggle="modal"
                                    data-target="#addNewDriverModal">Wasn't added!
                            </button>
                        </c:when>
                        <c:otherwise>
                            <%--Add new Driver Button--%>
                            <button class="btn btn-primary" data-toggle="modal"
                                    data-target="#addNewDriverModal">Add new Driver
                            </button>
                        </c:otherwise>
                    </c:choose>

                    <%--This will be shown in wrong edit case--%>
                    <c:if test="${editErr}">
                        <button class="btn btn-danger" data-toggle="modal"
                                data-target="#editDriverModal">Wasn't saved!
                        </button>
                    </c:if>

                </div>

                <%--Driver Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>P. Number</th>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Email</th>
                            <th>Worked time</th>
                            <th>Paid time</th>
                            <th>State</th>
                            <th>Current city</th>
                            <th>Current truck</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.driverList}" var="driverUnit">

                            <tr class="odd gradeX">
                                <td>${driverUnit.personalNumber}</td>
                                <td>${driverUnit.name}</td>
                                <td>${driverUnit.surname}</td>
                                <td>${driverUnit.email}</td>
                                <td>${driverUnit.workedTime}</td>
                                <td>${driverUnit.paidTime}</td>
                                <td>
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-info btn-xs dropdown-toggle"
                                                data-toggle="dropdown">
                                            <c:if test="${driverUnit.state.equals('REST')}">
                                                Is resting
                                            </c:if>
                                            <c:if test="${driverUnit.state.equals('ON_SHIFT')}">
                                                On the shift
                                            </c:if>
                                            <c:if test="${driverUnit.state.equals('DRIVING')}">
                                                Is driving
                                            </c:if>
                                            <c:if test="${driverUnit.state.equals('PORTER')}">
                                                Cargo works
                                            </c:if>
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="${contextPath}/rsm-driver/changeState?id=${driverUnit.id}&op=0">
                                                Is resting</a>
                                            </li>
                                            <li><a href="${contextPath}/rsm-driver/changeState?id=${driverUnit.id}&op=1">
                                                On the shift</a>
                                            </li>
                                            <li><a href="${contextPath}/rsm-driver/changeState?id=${driverUnit.id}&op=2">
                                                Is driving</a>
                                            </li>
                                            <li><a href="${contextPath}/rsm-driver/changeState?id=${driverUnit.id}&op=3">
                                                Cargo works</a>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                                <td>${driverUnit.cityName}</td>
                                <td>${driverUnit.truckRegNumber}</td>
                                <td>
                                    <a type="button" class="btn btn-edit btn-warning btn-xs"
                                       id="${driverUnit.id}/${driverUnit.name}"
                                       data-toggle="modal" data-target="#editDriverModal">Edit driver</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs btn-remove"
                                       id="${driverUnit.id}*${driverUnit.name}"
                                       data-toggle="modal" data-target="#deleteDriverModal">Remove driver</a>
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

<!-- Modal window add Driver-->
<div class="modal fade" id="addNewDriverModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add new driver</h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="addNewDriverForm" method="post" modelAttribute="driver"
                           action="${contextPath}/rsm-driver/addDriver">

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="name">
                                <form:input name="name" placeholder="name" path="name" autofocus="true"
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
                            <spring:bind path="surname">
                                <form:input name="surname" placeholder="surname" path="surname"
                                            class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="surname"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="email">
                                <form:input name="email" placeholder="email" path="email" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="email"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="cityName">
                                <form:input name="cityName" placeholder="dislocation city" path="cityName"
                                            class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="cityName"/>
                            </div>
                        </div>
                    </div>

                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="addNewDriverForm">Add new Driver</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal window edit Driver-->
<div class="modal fade" id="editDriverModal" tabindex="-1" role="dialog" aria-labelledby="editdtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="editdtoLabel">Edit driver ${editableDriver.personalNumber}</h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="editDriverForm" method="post" modelAttribute="editableDriver"
                           action="${contextPath}/rsm-driver/editDriver">

                    <spring:bind path="id">
                        <form:input type="hidden" name="id" value="" path="id" id="editeddtoId"/>
                    </spring:bind>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="name">
                                <form:input name="name" placeholder="name" path="name" class="form-control col-8"/>
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
                            <spring:bind path="surname">
                                <form:input name="surname" placeholder="surname" path="surname"
                                            class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="surname"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="email">
                                <form:input name="email" placeholder="email" path="email" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="email"/>
                            </div>
                        </div>
                    </div>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="cityName">
                                <form:input name="cityName" placeholder="current city name" path="cityName" class="form-control col-8"/>
                            </spring:bind>
                        </div>
                        <div class="secondary-text text-center text-danger">
                            <div class="font-italic">
                                <form:errors path="cityName"/>
                            </div>
                        </div>
                    </div>

                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="editDriverForm">Accept changes</button>
            </div>
        </div>
    </div>
</div>

<%--Modal window remove Driver--%>
<div class="modal fade" id="deleteDriverModal" tabindex="-1" role="dialog" aria-labelledby="deldtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="deldtoLabel"></h4>
            </div>
            <div class="modal-body">
                <h2 id="deletingdtoQuestion"></h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a type="button" class="btn btn-danger" id="deldtoButton" href="">Remove driver</a>
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

<!--My generic script-->
<script src="/resources/js/tables-handler.js"></script>


</body>
</html>