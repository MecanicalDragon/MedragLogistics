<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Medrag Logistics Warehouse Page
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
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">

                <%--Panel heading--%>
                <div class="panel-heading">

                    <%--This button starts a process of adding new orderr.--%>
                    <%--First step is adding new customer or choosing one of added earlier.--%>
                    <%--goto .../warehouse/CustomerController, GetMethod--%>
                    <a class="btn btn-primary" href="${contextPath}/whm-newCustomer" role="button">Add new orderr</a>

                    <%--Open modal window of choosing city, what warehouse you want to go--%>
                    <button class="btn btn-success" data-toggle="modal"
                            data-target="#cityModal">Go to the city warehouse
                    </button>

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

                        <%--Table header--%>
                        <thead>
                        <tr>
                            <th>Cargo index</th>
                            <th>Owner document</th>
                            <th>Current city</th>
                            <th>Destination point</th>
                            <th>Cargo state</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.globalCargoes}" var="cargo" varStatus="index">
                            <tr class="odd gradeX">

                                <td>${cargo.index}<span hidden>XXX${cargo.id}XXX</span></td>
                                <td>${cargo.owner.passport}</td>
                                <td>${cargo.currentCityName}</td>
                                <td>${cargo.destinationName}</td>
                                <td>
                                        <%--Cases for enum--%>
                                        <c:if test="${cargo.state.equals('TRANSIENT')}">
                                        <button type="button" class="btn btn-xs btn-outline btn-primary" disabled>
                                        Transient
                                        </button>
                                        </c:if>
                                        <c:if test="${cargo.state.equals('PREPARED')}">
                                        <button type="button" class="btn btn-xs btn-outline btn-info" disabled>
                                        Prepared
                                        </button>
                                        </c:if>
                                        <c:if test="${cargo.state.equals('ON_BOARD')}">
                                        <button type="button" class="btn btn-xs btn-outline btn-warning" disabled>En
                                        route
                                        </button>
                                        </c:if>
                                        <c:if test="${cargo.state.equals('DESTINATION')}">
                                        <a type="button" class="btn btn-xs btn-success"
                                        href="${contextPath}/whm-order/deliver/${index.index}">Destination</a>
                                        </c:if>
                                        <c:if test="${cargo.state.equals('DELIVERED')}">
                                        <button type="button" class="btn btn-xs btn-outline btn-success" disabled>
                                        Delivered
                                        </button>
                                        </c:if>
                                    <%--<div class="btn-group">--%>
                                        <%--<button type="button" class="btn btn-info btn-xs dropdown-toggle"--%>
                                                <%--data-toggle="dropdown">--%>
                                            <%--<c:if test="${cargo.state.equals('TRANSIENT')}">--%>
                                                <%--Transient--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${cargo.state.equals('PREPARED')}">--%>
                                                <%--Prepared--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${cargo.state.equals('ON_BOARD')}">--%>
                                                <%--En route--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${cargo.state.equals('DESTINATION')}">--%>
                                                <%--Destination--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${cargo.state.equals('DELIVERED')}">--%>
                                                <%--Delivered--%>
                                            <%--</c:if>--%>
                                            <%--<span class="caret"></span>--%>
                                        <%--</button>--%>
                                        <%--<ul class="dropdown-menu pull-right" role="menu">--%>
                                            <%--<li>--%>
                                                <%--<a href="${contextPath}/whm-cargo/changeState?id=${cargo.id}&op=TRANSIENT">--%>
                                                    <%--Transient</a>--%>
                                            <%--</li>--%>
                                            <%--<li>--%>
                                                <%--<a href="${contextPath}/whm-cargo/changeState?id=${cargo.id}&op=PREPARED">--%>
                                                    <%--Prepared</a>--%>
                                            <%--</li>--%>
                                            <%--<li>--%>
                                                <%--<a href="${contextPath}/whm-cargo/changeState?id=${cargo.id}&op=ON_BOARD">--%>
                                                    <%--En route</a>--%>
                                            <%--</li>--%>
                                        <%--</ul>--%>
                                    <%--</div>--%>
                                </td>
                            </tr>

                        </c:forEach>
                        <%--End of table--%>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <%--Footer--%>
    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>

<%--Modal window of choosing city warehouse--%>
<div class="modal fade" id="cityModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Enter city name</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form" id="gotoCity" method="post" action="${contextPath}/whm-wp/actual">
                        <div class="col-4">
                            <input name="name" placeholder="Enter city name" autofocus="true"
                                   class="form-control col-4"/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button class="btn btn-success" form="gotoCity">Go</button>
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
        $('#dto-Table').DataTable({
            responsive: true
        });
    });
</script>

</body>
</html>
