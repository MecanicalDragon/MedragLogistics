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

    <form action="${contextPath}/${reroute != null ? 'mgr-compileRoute/complete' : 'mgr-compileRoute'}" method="POST" id="driverForm">
        <input type="hidden" id="driverHiddenField" name="drivers" value="">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="text-center">

                        <%--<h3>Step 4: compile a brigade.</h3>--%>
                        <h3>${reroute == null ? 'Step 4: compile a brigade.' : 'Brigade compiling for truck '
                        .concat(sessionScope.chosenTruck.regNumber). concat(' (destination: ')
                        .concat(sessionScope.chosenTruck.destinationName).concat(').')}</h3>

                        <c:set var="tripHours"
                               value="${fn:substringBefore(duration div 60, '.')}"/>
                        <fmt:formatNumber var="tripMinutes" minIntegerDigits="2"
                                          value="${duration - (tripHours*60) }"/>
                        <h4>Route length: ${distance} kms. It will take about (including cargo works) ${tripHours}:${tripMinutes} hours.</h4>

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
                                    <button class="btn btn-xs btn-choose btn-enabled btn-success"
                                            id="choose-${index.index}">
                                        Assign
                                    </button>
                                </td>
                                <td>${driverUnit.personalNumber} ${reroute != null && driverUnit.currentTruck != null ? '(now in brigade)' : '' }</td>
                                <td>${driverUnit.name}</td>
                                <td>${driverUnit.surname}</td>
                                <td>
                                    <c:set var="hours"
                                           value="${fn:substringBefore(driverUnit.workedTime div 60, '.')}"/>
                                    <fmt:formatNumber var="minutes" minIntegerDigits="2"
                                                      value="${driverUnit.workedTime - (hours*60) }"/>
                                        ${hours}:${minutes}
                                </td>

                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
                    <div class="text-center">
                        <c:if test="${reroute == null}">
                            <a class="btn btn-warning" href="${contextPath}/mgr-chooseCity" role="button">< Back</a>
                        </c:if>
                        <a class="btn btn-danger" href="${contextPath}/${reroute == null ? 'mgr-main' : 'mgr-route'}" role="button">Dismiss</a>
                        <button class="btn btn-success" form="driverForm" id="compile" disabled>Compile</button>
                    </div>
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
<script src="/resources/js/dt-base.js"></script>

<%--Drivers assigning script--%>
<script src="/resources/js/drivers-assigning.js"></script>

<script>
    var neededDrivers = parseInt("${sessionScope.brigade}", 10);
</script>

</body>
</html>