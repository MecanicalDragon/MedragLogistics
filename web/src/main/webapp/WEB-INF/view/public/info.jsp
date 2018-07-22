<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Order info</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <!-- My palitre -->
    <link href="/resources/css/palitre.css" rel="stylesheet" type="text/css">

</head>
<body>
<br>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="text-center">
                        <h2>Order ${order.index} delivery status</h2>
                    </div>
                </div>

                <div class="panel-body">

                    <br>
                    <c:forEach items="${order.cargoes}" var="cargo" varStatus="index">
                        <div>
                            <p>
                                <strong>${cargo.name}</strong>
                                <span class="pull-right text-muted">${cargo.index}</span>
                            </p>

                            <c:choose>
                                <c:when test="${cargo.state.equals('DELIVERED')}">
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-success" role="progressbar"
                                             aria-valuenow="100"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: 100%">
                                            <span>Delivered</span>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${cargo.state.equals('DESTINATION')}">
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-primary" role="progressbar"
                                             aria-valuenow="90}"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: 90%">
                                            <span>On it's destination </span>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${cargo.currentCityId.equals(cargo.departureId) &&
                                    cargo.state.equals('ON_BOARD')}">
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-warning" role="progressbar"
                                             aria-valuenow="15}"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: 15%">
                                            <span>On the way</span>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${cargo.currentCityId.equals(cargo.departureId)}">

                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-warning" role="progressbar"
                                             aria-valuenow="10}"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: 10%">
                                            <span>Making the route</span>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="progress progress-striped active">
                                        <div class="progress-bar progress-bar-info" role="progressbar"
                                             aria-valuenow="${completeness.get(index.index)}"
                                             aria-valuemin="0" aria-valuemax="100"
                                             style="width: ${completeness.get(index.index)}%">
                                            <span>Last seen: ${cargo.currentCityName}</span>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>
</body>
</html>
