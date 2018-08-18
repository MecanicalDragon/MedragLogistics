<!DOCTYPE html>
<%@ page buffer="16kb" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Drivers Page</title>

    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">


</head>
<body>
<div class="container">

    <%--Heading Tab Panel--%>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-city">Cities</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="${contextPath}/rsm-driver">Drivers</a>
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
                            <button class="btn btn-danger" data-toggle="modal" id="wasntAdded"
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
                        <button class="btn btn-danger" data-toggle="modal" id="editButton"
                                data-target="#editDriverModal">Wasn't saved!
                        </button>
                    </c:if>

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
                            <th>P.I.N.</th>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Worked</th>
                            <th>Paid</th>
                            <th>Prev.</th>
                            <th>Status</th>
                            <th>City</th>
                            <th>Truck</th>
                            <th>Edit</th>
                            <th>Remove</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.driverList}" var="driverUnit" varStatus="index">

                            <tr class="odd gradeX">
                                <td>${driverUnit.personalNumber}<span
                                        hidden>XXX${driverUnit.id}XXX${driverUnit.personalNumber}XXX${index.index}XXX</span>
                                </td>
                                <td>${driverUnit.name}</td>
                                <td>${driverUnit.surname}</td>
                                <td>
                                    <c:set var="hours"
                                           value="${fn:substringBefore(driverUnit.workedTime div 60, '.')}"/>
                                    <fmt:formatNumber var="minutes" minIntegerDigits="2"
                                                      value="${driverUnit.workedTime - (hours*60) }"/>
                                        ${hours}:${minutes}
                                </td>
                                <td>
                                    <c:set var="pHours" value="${fn:substringBefore(driverUnit.paidTime div 60, '.')}"/>
                                    <fmt:formatNumber var="pMinutes" minIntegerDigits="2"
                                                      value="${driverUnit.paidTime - (pHours*60) }"/>
                                        ${pHours}:${pMinutes}
                                </td>
                                <td>
                                    <c:set var="lastMonthHours"
                                           value="${fn:substringBefore(driverUnit.hoursLastMonth div 60, '.')}"/>
                                    <fmt:formatNumber var="lastMonthMinutes" minIntegerDigits="2"
                                                      value="${driverUnit.hoursLastMonth - (lastMonthHours*60) }"/>
                                        ${lastMonthHours}:${lastMonthMinutes}
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <button type="button" class="btn ${driverUnit.state.toString().equals('REST') ? 'btn-primary'
                                        : driverUnit.state.toString().equals('READY_TO_ROUTE') ? 'btn-info' : 'btn-success'} btn-xs dropdown-toggle"
                                                data-toggle="dropdown" style="width:105px; text-align:right;">
                                            <c:if test="${driverUnit.state.toString().equals('REST')}">
                                                Is resting
                                            </c:if>
                                            <c:if test="${driverUnit.state.toString().equals('ON_SHIFT')}">
                                                On the shift
                                            </c:if>
                                            <c:if test="${driverUnit.state.toString().equals('DRIVING')}">
                                                Is driving
                                            </c:if>
                                            <c:if test="${driverUnit.state.toString().equals('PORTER')}">
                                                Cargo works
                                            </c:if>
                                            <c:if test="${driverUnit.state.toString().equals('READY_TO_ROUTE')}">
                                                Ready to route
                                            </c:if>
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li>
                                                <a role="button" class="rest">
                                                    Is resting</a>
                                            </li>
                                            <li>
                                                <a role="button" class="shift">
                                                    On the shift</a>
                                            </li>
                                            <li>
                                                <a role="button" class="drive">
                                                    Is driving</a>
                                            </li>
                                            <li>
                                                <a role="button" class="porter">
                                                    Cargo works</a>
                                            </li>
                                            <li>
                                                <a role="button" class="ready">
                                                    Ready to route</a>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                                <td>${driverUnit.cityName}</td>
                                <td>${driverUnit.currentTruck.regNumber}</td>
                                <td>
                                    <a type="button" class="btn btn-edit btn-warning btn-xs"
                                       data-toggle="modal" data-target="#editDriverModal">Edit driver</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs btn-remove"
                                       data-toggle="modal" data-target="#deleteDriverModal">Remove</a>
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

<form action="${contextPath}/rsm-driver/changeState" method="POST" id="unitForm">
    <input type="hidden" id="unitIndex" name="index" value="">
    <input type="hidden" id="unitState" name="state" value="">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="that" hidden></button>
</form>

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
                                <form:input name="cityName" placeholder="current city name" path="cityName"
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
            <form action="${contextPath}/rsm-driver/remove" method="POST" id="targetForm">
                <input type="hidden" id="targetField" name="index" value="">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button class="btn btn-danger" form="targetForm" id="deldtoButton">Remove driver</button>
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
<script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

<%--Datatable buttons handler and automatically opened modal window script--%>
<script src="/resources/js/rsm-tables-handler.js"></script>
<script src="/resources/js/wasnt-added.js"></script>

<%--Websockets status changer--%>
<script src="/resources/js/sockjs.js"></script>
<script src="/resources/js/stomp.js"></script>
<script>
    $(document).ready(function () {
        var socket = new SockJS('/drivers');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/changes/inDrivers', function () {
                    location.reload(true);
            });
        });
    });
</script>

</body>
</html>
