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
        Trucks Page
    </title>
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
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
            <a class="nav-link" href="${contextPath}/rsm-driver">Drivers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="${contextPath}/rsm-truck">Trucks</a>
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
                            <%--Add new Truck Error Button--%>
                            <button class="btn btn-danger" data-toggle="modal" id="wasntAdded"
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

                    <%--This will be shown in wrong edit case--%>
                    <c:if test="${editErr}">
                        <button class="btn btn-danger" data-toggle="modal"  id="editButton"
                                data-target="#editTruckModal">Wasn't saved!
                        </button>
                    </c:if>

                    <div class="pull-right">
                        <form method="post" action="/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                            <button class="btn btn-danger offset-xs-6">Logout</button>
                        </form>
                    </div>

                </div>

                <%--Trucks Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>Truck reg. number</th>
                            <th>Brigade str.</th>
                            <th>Capacity</th>
                            <th>Current city</th>
                            <th>Status</th>
                            <th>Update</th>
                            <th>Remove</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.truckList}" var="truckUnit" varStatus="index">
                            <tr class="odd gradeX">
                                <td>${truckUnit.regNumber}<span hidden>XXX${truckUnit.id}XXX${truckUnit.regNumber}XXX${index.index}XXX</span></td>
                                <td>${truckUnit.brigadeStr}</td>
                                <td>${truckUnit.capacity}</td>
                                <td>${truckUnit.cityName}</td>
                                <td>
                                    <div class="btn-group">
                                        <button type="button" class="btn ${truckUnit.status.toString().equals('IN_USE') ? 'btn-success'
                                        : truckUnit.status.toString().equals('STAY_IDLE') ? 'btn-info' : 'btn-primary'} btn-xs dropdown-toggle"
                                                data-toggle="dropdown" style="width:105px; text-align:right;">
                                            <c:if test="${truckUnit.status.toString().equals('IN_USE')}">
                                                On the route
                                            </c:if>
                                            <c:if test="${truckUnit.status.toString().equals('STAY_IDLE')}">
                                                Staying idle
                                            </c:if>
                                            <c:if test="${truckUnit.status.toString().equals('IN_SERVICE')}">
                                                In service
                                            </c:if>
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a role="button" class="in-use">
                                                On the route</a>
                                            </li>
                                            <li><a role="button" class="staying-idle">
                                                Staying idle</a>
                                            </li>
                                            <li><a role="button" class="in-service">
                                                In service</a>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-warning btn-xs btn-edit"
                                       data-toggle="modal" data-target="#editTruckModal">Edit truck data</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs btn-remove"
                                       data-toggle="modal" data-target="#deleteTruckModal">Remove truck</a>
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

<!-- Modal window edit truck-->
<div class="modal fade" id="editTruckModal" tabindex="-1" role="dialog" aria-labelledby="editdtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="editdtoLabel"></h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="editTruckForm" method="post" modelAttribute="editableTruck"
                           action="${contextPath}/rsm-truck/editTruck">

                    <spring:bind path="id">
                        <form:input type="hidden" name="id" value="" path="id" id="editeddtoId"/>
                    </spring:bind>

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="regNumber">
                                <form:input name="regNumber" placeholder="Registration Number" path="regNumber"
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
                                <form:input name="brigadeStr" placeholder="Brigade strength" path="brigadeStr"
                                            class="form-control col-8"/>
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
                                <form:input name="capacity" placeholder="capacity" path="capacity"
                                            class="form-control col-8"/>
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
                            <spring:bind path="cityName">
                                <form:input name="cityName" placeholder="current city" path="cityName"
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
                <button class="btn btn-success" form="editTruckForm">Accept changes</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal window add truck-->
<div class="modal fade" id="addNewTruckModal" tabindex="-1" role="dialog" aria-labelledby="truckLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="truckLabel">Add new Truck</h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="addNewTruckForm" method="post" modelAttribute="truck"
                           action="${contextPath}/rsm-truck/addTruck">

                    <div class="row row-justify-content-center">
                        <div class="col-sm-6">
                            <spring:bind path="regNumber">
                                <form:input name="regNumber" placeholder="Truck register number" path="regNumber"
                                            autofocus="true"
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
                                <form:input name="brigadeStr" placeholder="Brigade strength" path="brigadeStr"
                                            class="form-control col-8"/>
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
                                <form:input name="capacity" placeholder="capacity" path="capacity"
                                            class="form-control col-8"/>
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
                            <spring:bind path="cityName">
                                <form:input name="cityName" placeholder="current city" path="cityName"
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
                <button class="btn btn-success" form="addNewTruckForm">Add new truck</button>
            </div>
        </div>
    </div>
</div>

<%--Modal window remove truck--%>
<div class="modal fade" id="deleteTruckModal" tabindex="-1" role="dialog" aria-labelledby="deldtoLabel"
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
            <form action="${contextPath}/rsm-truck/remove" method="POST" id="targetForm">
                <input type="hidden" id="targetField" name="index" value="">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button class="btn btn-danger" form="targetForm" id="deldtoButton">Remove truck</button>
            </div>
        </div>
    </div>
</div>

<form action="${contextPath}/rsm-truck/changeState" method="POST" id="unitForm">
    <input type="hidden" id="unitIndex" name="index" value="">
    <input type="hidden" id="unitState" name="state" value="">
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

<%--Datatable buttons handler and automatically opened modal window script--%>
<script src="/resources/js/rsm-tables-handler.js"></script>
<script src="/resources/js/wasnt-added.js"></script>

<%--Websockets status changer--%>
<script src="/resources/js/sockjs.js"></script>
<script src="/resources/js/stomp.js"></script>
<script>
    $(document).ready(function () {
        var socketWP = new SockJS('/waypoints');
        var stompClientWP = Stomp.over(socketWP);
        stompClientWP.connect({}, function (frame) {
            stompClientWP.subscribe('/changes/inWaypoints', function () {
                location.reload(true);
            });
        });
    });
</script>

</body>
</html>