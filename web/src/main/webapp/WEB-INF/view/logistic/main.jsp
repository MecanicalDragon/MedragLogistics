<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Logistic Page
    </title>

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
                <div class="panel-heading">
                    <a class="btn btn-warning" href="" role="button">Refresh</a>

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

                    <table width="100%" class="table table-striped table-bordered table-hover" id="orderr-Table">
                        <thead>
                        <tr>
                            <th>Start manage</th>
                            <th>Cargo index</th>
                            <th>Cargo weight</th>
                            <th>Current city</th>
                            <th>Destination point</th>
                            <th>Cargo state</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.globalCargoes}" var="cargo" varStatus="index">
                            <tr class="odd gradeX">
                                <td>
                                    <a type="button" class="btn btn-success btn-xs start-order"
                                       href="${contextPath}/mgr-wp/chooseTruck/${index.index}">
                                        Add to truck
                                    </a>
                                </td>
                                <td>${cargo.index}</td>
                                <td>${cargo.weight}</td>
                                <td>${cargo.currentCityName}</td>
                                <td>${cargo.destinationName}</td>
                                <td>${cargo.state}</td>
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

</body>
</html>
