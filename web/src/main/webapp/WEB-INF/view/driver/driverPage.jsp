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
    <title>Driver Page</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

    <!-- My palitre -->
    <link href="/resources/css/palitre.css" rel="stylesheet" type="text/css">


</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">

            <div class="row">
                <div class="text-left col-xs-6">
                    <h2>${driver.personalNumber}</h2>
                </div>
                <div class="text-right col-xs-6">
                    <h2>${driver.name} ${driver.surname}</h2>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4>Some information</h4>
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">

                                            <%--Working information--%>
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Working
                                                information</a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <c:if test="${standalone != null && standalone == true}">
                                                <span class="redtext">You can't leave truck now, wait for shift change or call operator.</span>
                                            </c:if>
                                            <div class="row">
                                                <div class="text-left col-xs-4">
                                                    <c:set var="hours"
                                                           value="${fn:substringBefore(driver.workedTime div 60, '.')}"/>
                                                    <fmt:formatNumber var="minutes" minIntegerDigits="2"
                                                                      value="${driver.workedTime - (hours*60) }"/>
                                                    <h4>Worked time: ${hours}:${minutes}</h4>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="btn-group">
                                                        <button type="button" style="width: 150px;"
                                                                class="btn btn-info btn-lg btn-block dropdown-toggle"
                                                                data-toggle="dropdown">
                                                            <c:if test="${driver.state.equals('REST')}">
                                                                Rest
                                                            </c:if>
                                                            <c:if test="${driver.state.equals('ON_SHIFT')}">
                                                                On the shift
                                                            </c:if>
                                                            <c:if test="${driver.state.equals('DRIVING')}">
                                                                Driving
                                                            </c:if>
                                                            <c:if test="${driver.state.equals('PORTER')}">
                                                                Cargo works
                                                            </c:if>
                                                            <c:if test="${driver.state.equals('READY_TO_ROUTE')}">
                                                                Ready to route
                                                            </c:if>
                                                        </button>
                                                        <ul class="dropdown-menu pull-right" role="menu">
                                                            <c:choose>
                                                                <%--<c:when test="${wps.size() == 0 || wps == null}">--%>
                                                                <c:when test="${driver.destinationId == null || driver.destinationId.equals(driver.cityId)}">

                                                                    <li>
                                                                        <a id="REST" role="button" class="changeState">Go
                                                                            to rest</a>
                                                                    </li>
                                                                    <li>
                                                                        <a id="READY_TO_ROUTE" role="button"
                                                                           class="changeState">Ready to route</a>
                                                                    </li>
                                                                </c:when>
                                                                <%--<c:when test="${driver.currentTruck != null}">--%>
                                                                <c:otherwise>
                                                                    <li>
                                                                        <a id="ON_SHIFT" role="button"
                                                                           class="changeState">On the shift</a>
                                                                    </li>
                                                                    <li>
                                                                        <a id="DRIVING" role="button"
                                                                           class="changeState">Is driving</a>
                                                                    </li>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="text-right col-xs-4">
                                                    <c:set var="pHours"
                                                           value="${fn:substringBefore(driver.paidTime div 60, '.')}"/>
                                                    <fmt:formatNumber var="pMinutes" minIntegerDigits="2"
                                                                      value="${driver.paidTime - (pHours*60) }"/>
                                                    <h4>Paid time: ${pHours}:${pMinutes}</h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">

                                            <%--Brigade information--%>
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Brigade
                                                information</a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <c:choose>
                                                <c:when test="${driver.currentTruck != null}">
                                                    <div class="row">
                                                        <div class="text-left col-xs-6">
                                                            <h4>Truck:</h4>
                                                        </div>
                                                        <div class="text-right col-xs-6">
                                                            <h4>${driver.currentTruck.regNumber}</h4>
                                                        </div>
                                                    </div>
                                                    <c:forEach items="${driver.currentTruck.brigade}"
                                                               var="dUnit">
                                                        <div class="row">
                                                            <div class="text-left col-xs-6">
                                                                <h4>${dUnit.personalNumber}</h4>
                                                            </div>
                                                            <div class="text-right col-xs-6">
                                                                <h4>${dUnit.name} ${dUnit.surname}</h4>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="row">
                                                        <div class="text-center">
                                                            <h3>No active orders.</h3>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">

                                            <%--Order infprmation--%>
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">Order
                                                information</a>
                                        </h4>
                                    </div>
                                    <div id="collapseThree" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <c:choose>
                                                <c:when test="${wps != null && wps.size() != 0}">
                                                    <c:if test="${driver.destinationId != null}">
                                                        <div class="row">
                                                            <div class="text-left col-xs-6">
                                                                <h2>Current destination:</h2>
                                                            </div>
                                                            <div class="text-right col-xs-6">
                                                                <h2>${driver.destinationName}</h2>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                    <table width="100%"
                                                           class="table table-striped table-bordered table-hover"
                                                           id="dto-Table">
                                                        <thead>
                                                        <tr>
                                                            <th>Type</th>
                                                            <th>City</th>
                                                            <th>Cargo index</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach items="${wps}"
                                                                   var="waypoint">
                                                        <tr class="odd gradeX">
                                                            <td>
                                                                    ${waypoint.wayPointType}
                                                            </td>
                                                            <td>
                                                                    ${waypoint.city.name}
                                                            </td>
                                                            <td>
                                                                    ${waypoint.cargo.index}
                                                            </td>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="row">
                                                        <div class="text-center">
                                                            <h3>No active orders.</h3>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- .panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">
                <form method="post" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button class="btn btn-danger btn-lg">Logout</button>
                </form>
            </div>

        </div>
    </div>
    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>

<form action="${contextPath}/drv-main" method="POST" id="driverForm">
    <input type="hidden" id="driverHiddenField" name="option" value="">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="that" hidden></button>
</form>


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
        $(".changeState").click(function () {
            var state = $(this).attr("id");
            $("#driverHiddenField").val(state);
            $("#that").trigger("click");
        });
    });
</script>

<%--Websockets state notifier--%>
<script src="/resources/js/sockjs.js"></script>
<script src="/resources/js/stomp.js"></script>
<script>
    $(document).ready(function () {
        var ch = ${changed};
        if (ch === true) {
            var socket = new SockJS('/drivers');
            var stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.send("/medrag/drivers", {}, "stateChanged");
            });
        }
    });
</script>

</body>
</html>
