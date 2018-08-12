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
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">


</head>
<body>
<br>
<div class="container">

    <form action="${contextPath}/mgr-chooseTruck" method="POST" id="targetForm">
        <input type="hidden" id="targetField" name="index" value="">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <a class="btn btn-primary" href="${contextPath}/mgr-route" role="button">Route trucks</a>

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
                            <th>Start manage</th>
                            <th>Cargo index</th>
                            <th>Cargo weight</th>
                            <th>Current city</th>
                            <th>Destination point</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.globalCargoes}" var="cargo" varStatus="index">
                            <tr class="odd gradeX">
                                <td>
                                    <button class="btn btn-success btn-xs btn-target-go"
                                            form="targetForm" id="XXX${index.index}XXX">Start manage
                                    </button>
                                </td>
                                <td>${cargo.index}</td>
                                <td>${cargo.weight}</td>
                                <td>${cargo.currentCityName}</td>
                                <td>${cargo.destinationName}</td>
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

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="/resources/js/dt-base.js"></script>

</body>
</html>
