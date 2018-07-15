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

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">


</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%--This button starts a process of adding new orderr.--%>
                    <%--First step is adding new customer or choosing one of added earlier.--%>
                    <%--goto .../warehouse/CustomerController, GetMethod--%>
                    <a class="btn btn-primary" href="${contextPath}/whm-newCustomer" role="button">Add new
                        orderr</a>
                        <div class="pull-right">
                            <form method="post" action="logout">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                                <button class="btn btn-danger offset-xs-6">Logout</button>
                            </form>
                        </div>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">

                    <table width="100%" class="table table-striped table-bordered table-hover" id="orderr-Table">
                        <thead>
                        <tr>
                            <%--<th>Start manage</th>--%>
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

                                <td>${cargo.index}</td>
                                <td>${cargo.owner.passport}</td>
                                <td>${cargo.currentCityName}</td>
                                <td>${cargo.destinationName}</td>
                                <td>
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-info btn-xs dropdown-toggle"
                                                data-toggle="dropdown">
                                            <c:if test="${cargo.state.equals('TRANSIENT')}">
                                                Transient
                                            </c:if>
                                            <c:if test="${cargo.state.equals('PREPARED')}">
                                                Prepared
                                            </c:if>
                                            <c:if test="${cargo.state.equals('ON_BOARD')}">
                                                On the way
                                            </c:if>
                                            <c:if test="${cargo.state.equals('DESTINATION')}">
                                                Destination
                                            </c:if>
                                            <c:if test="${cargo.state.equals('DELIVERED')}">
                                                Delivered
                                            </c:if>
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="${contextPath}/whm-main/changeState?id=${cargo.id}&op=0">
                                                Transient</a>
                                            </li>
                                            <li><a href="${contextPath}/whm-main/changeState?id=${cargo.id}&op=1">
                                                Prepared</a>
                                            </li>
                                            <li><a href="${contextPath}/whm-main/changeState?id=${cargo.id}&op=2">
                                                On the way</a>
                                            </li>
                                            <li><a href="${contextPath}/whm-main/changeState?id=${cargo.id}&op=3">
                                                Destination</a>
                                            </li>
                                            <li><a href="${contextPath}/whm-main/changeState?id=${cargo.id}&op=4">
                                                Delivered</a>
                                            </li>
                                        </ul>
                                    </div>
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


<%--&lt;%&ndash;Modal window add cargo to truck&ndash;%&gt;--%>
<%--<div class="modal fade" id="addCargoToTruck" tabindex="-1" role="dialog" aria-labelledby="modalLabel"--%>
<%--aria-hidden="true">--%>
<%--<div class="modal-dialog modal-dialog-centered">--%>
<%--<div class="modal-content">--%>
<%--<div class="modal-header">--%>
<%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
<%--<h4 class="modal-title" id="modalLabel"></h4>--%>
<%--</div>--%>
<%--<div class="modal-body">--%>


<%--</div>--%>
<%--<div class="modal-footer">--%>
<%--<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>--%>
<%--<a type="button" class="btn btn-danger" id="choose" href="">Choose truck</a>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>

<%--<div>--%>
<%--<form:form id="addCargo" method="post" action="${contextPath}/whm-wp/initTruck">--%>
<%--<form:input type="hidden" path="cargoId" id="cargoListId" value=""/>--%>
<%--</form:form>--%>
<%--</div>--%>


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
        $('#orderr-Table').DataTable({
            responsive: true
        });
    });
</script>
<%--<script>--%>
    <%--$(document).ready(function () {--%>
        <%--$(".start-order").click(function () {--%>
            <%--var idCargo = $(this).attr("id");--%>
            <%--var index = idCargo.split('-')[1];--%>
            <%--var cargoU = $("#sessionScope.globalCargoes[index]");--%>
            <%--$.ajax({--%>
                <%--type: "POST",--%>
                <%--contentType: "application/json",--%>
                <%--url: window.location + "/whm-wp/initTruck",--%>
                <%--data: JSON.stringify(cargoU),--%>
                <%--dataType: 'json',--%>
                <%--success: function (result) {--%>
                    <%--alert("success");--%>
                <%--},--%>
                <%--error: function (error) {--%>
                    <%--alert("Error!");--%>
                <%--}--%>
            <%--});--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>

</body>
</html>
