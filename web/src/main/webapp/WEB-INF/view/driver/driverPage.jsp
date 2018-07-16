<!DOCTYPE html>
<%@ page buffer="16kb" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Driver Page</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">


</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">

            <div class="row">
                <div class="text-left col-xs-6">
                    <h2>${sessionScope.driver.personalNumber}</h2>
                </div>
                <div class="text-right col-xs-6">
                    <h2>${sessionScope.driver.name} ${sessionScope.driver.surname}</h2>
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
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Working
                                                information</a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="text-left col-xs-6">
                                                    <h4>Worked time: ${sessionScope.driver.workedTime} hours</h4>
                                                </div>
                                                <div class="text-right col-xs-6">
                                                    <h4>Paid time: ${sessionScope.driver.paidTime} hours</h4>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="btn-group">
                                                <button type="button" class="btn btn-info btn-lg btn-block dropdown-toggle" data-toggle="dropdown">
                                                    <c:if test="${sessionScope.driver.state.equals('REST')}">
                                                        Is resting
                                                    </c:if>
                                                    <c:if test="${sessionScope.driver.state.equals('ON_SHIFT')}">
                                                        On the shift
                                                    </c:if>
                                                    <c:if test="${sessionScope.driver.state.equals('DRIVING')}">
                                                        Is driving
                                                    </c:if>
                                                    <c:if test="${sessionScope.driver.state.equals('PORTER')}">
                                                        Cargo works
                                                    </c:if>
                                                    <c:if test="${sessionScope.driver.state.equals('READY_TO_ROUTE')}">
                                                        Ready to route
                                                    </c:if>
                                                </button>
                                                    <ul class="dropdown-menu pull-right" role="menu">
                                                        <li><a href="${contextPath}/drv-main/changeState/REST">
                                                            Is resting</a>
                                                        </li>
                                                        <li><a href="${contextPath}/drv-main/changeState/ON_SHIFT">
                                                            On the shift</a>
                                                        </li>
                                                        <li><a href="${contextPath}/drv-main/changeState/DRIVING">
                                                            Is driving</a>
                                                        </li>
                                                        <li><a href="${contextPath}/drv-main/changeState/PORTER">
                                                            Cargo works</a>
                                                        </li>
                                                        <li><a href="${contextPath}/drv-main/changeState/READY_TO_ROUTE">
                                                            Ready to route</a>
                                                        </li>
                                                    </ul>
                                            </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Brigade
                                                information</a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <c:choose>
                                                <c:when test="${!sessionScope.driver.state.equals('REST') &&
                                            !sessionScope.driver.state.equals('READY_TO_ROUTE')}">
                                                    <div class="row">
                                                    <div class="text-left col-xs-6">
                                                        <h4>Truck:</h4>
                                                    </div>
                                                    <div class="text-right col-xs-6">
                                                        <h4>${sessionScope.driver.currentTruck.regNumber}</h4>
                                                    </div>
                                                    </div>
                                                        <c:forEach items="${sessionScope.driver.currentTruck.brigade}"
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
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">Order information</a>
                                        </h4>
                                    </div>
                                    <div id="collapseThree" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <c:choose>
                                                <c:when test="${!sessionScope.driver.state.equals('REST') &&
                                            !sessionScope.driver.state.equals('READY_TO_ROUTE')}">
                                                <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                                                    <thead>
                                                    <tr>
                                                        <th>type</th>
                                                        <th>city</th>
                                                        <th>cargo index</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${sessionScope.wps}"
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
