<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Route Page
    </title>
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/palitre.css" rel="stylesheet" type="text/css">

</head>
<body>
<br>
<div class="container">

    <form action="${contextPath}/mgr-rerouteTruck" method="POST" id="targetForm">
        <input type="hidden" id="targetField" name="index" value="">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <a class="btn btn-primary" href="${contextPath}/mgr-main" role="button">To the cargoes</a>

                    <%--Logout button--%>
                    <div class="pull-right">
                        <form method="post" action="${contextPath}/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                            <button class="btn btn-danger offset-xs-6">Logout</button>
                        </form>
                    </div>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">

                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>Select</th>
                            <th>Truck number</th>
                            <th>Current city</th>
                            <th>Destination</th>
                            <th>Capacity</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.trucksInUse}" var="truck" varStatus="index">
                            <tr class="odd gradeX">
                                <td>
                                    <button class="btn ${truck.manageable.toString().equals('NEED_TO_COMPLETE') ? 'btn-danger' : 'btn-success'} btn-xs btn-target-go"
                                            form="targetForm" style="width:70px;"
                                            id="XXX${index.index}XXX">${truck.manageable.toString().equals('NEED_TO_COMPLETE') ? 'Staff' : 'Reroute'}
                                    </button>
                                </td>
                                <td>${truck.regNumber}</td>
                                <td>${truck.cityName}</td>
                                <td>${truck.destinationName}</td>
                                <td>${truck.capacity}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
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
<script src="/resources/js/dt-base.js"></script>

<%--Websockets status changer--%>
<script src="/resources/js/sockjs.js"></script>
<script src="/resources/js/stomp.js"></script>
<script>
    $(document).ready(function () {
        var socket = new SockJS('/waypoints');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/changes/inWaypoints', function () {
                location.reload(true);
            });
        });
    });
</script>

</body>
</html>
