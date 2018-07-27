<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add cargoes
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

    <form action="${contextPath}/mgr-addCargoes" method="POST" id="cargoForm">
        <input type="hidden" id="cargoHiddenField" name="cargoesList" value="">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="text-center">

                        <h3>Step 2: add more cargoes to the truck.</h3>
                        <h3 id="msg">Now loaded 0 kgs of ${sessionScope.chosenTruck.capacity} total capacity.</h3>
                    </div>
                </div>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover"
                           id="dto-Table">
                        <thead>
                        <tr>
                            <th>Check</th>
                            <th>Cargo index</th>
                            <th>Cargo weight</th>
                            <th>Current city</th>
                            <th>Destination</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.cityCargoes}" var="cargoItem" varStatus="index">
                            <tr class="odd gradeX">
                                <td>
                                    <c:choose>
                                        <c:when test="${cargoItem.id.equals(sessionScope.chosenCargoId)}">
                                            <button class="btn btn-xs btn-choose btn-enabled btn-success chosenCargo"
                                                    id="choose-${index.index}-${cargoItem.weight}">Add this
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-xs btn-choose btn-enabled btn-success"
                                                    id="choose-${index.index}-${cargoItem.weight}">Add this
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${cargoItem.index}</td>
                                <td>${cargoItem.weight}</td>
                                <td>${cargoItem.currentCityName}</td>
                                <td>${cargoItem.destinationName}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

                </div>
                <div class="panel-footer">
                    <div class="text-center">
                        <div class="row">
                            <a class="btn btn-danger" href="${contextPath}/mgr-main" role="button">Dismiss</a>
                            <a class="btn btn-warning" href="${contextPath}/mgr-startManage" role="button">Choose another truck</a>
                            <button class="btn btn-success" form="cargoForm" id="compile">Compile</button>
                        </div>
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

<%--Choosing cargoes script--%>
<script src="/resources/js/adding-cargoes.js"></script>

<script>
    var capacity = parseInt("${sessionScope.chosenTruck.capacity}", 10);
</script>


</body>
</html>
