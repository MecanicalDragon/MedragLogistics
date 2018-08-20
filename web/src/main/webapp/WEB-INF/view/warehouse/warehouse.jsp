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
        ${sessionScope.warehouseOfCity}
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

                    <form class="form" id="refresh" action="${contextPath}/whm-wp">
                            <input type="hidden" name="city" value="${sessionScope.warehouseOfCity}"/>
                            <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                    </form>

                    <div class="text-center">
                    <h1>Warehouse of city ${sessionScope.warehouseOfCity}</h1>
                    </div>
    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
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
                                <td>${wp.currentTruck.regNumber}<span hidden>XXX${index.index}XXX</span></td>
                                <td>${wp.wayPointType.toString().equals("CHECK") ? "CHECKPOINT" : wp.cargo.index}</td>
                                <td>${wp.wayPointType.toString()}</td>
                                <td>
                                    <button class="btn btn-success btn-xs btn-wp"
                                       form="completeWP">Complete</button>
                                </td>
                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<form action="${contextPath}/whm-wp" method="POST" id="completeWP">
    <input type="hidden" id="targetField" name="index" value="">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="/resources/js/dt-base.js"></script>

<%--Websockets state notifier--%>
<script src="/resources/js/sockjs.js"></script>
<script src="/resources/js/stomp.js"></script>
<script>
    $(document).ready(function () {
        var ch = ${changed == null ? false : changed};
        if (ch === true) {
            var socket = new SockJS('/waypoints');
            var stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.send("/medrag/waypoints", {}, "arrival");
            });
        }
    });
</script>

</body>
</html>
