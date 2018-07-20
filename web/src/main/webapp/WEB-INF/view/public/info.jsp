<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Order info</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h2>Order ${order.index} delivery status</h2>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${order == null}">
                        <h2>There is no such order in our database. Maybe you have entered a wrong name?</h2>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${order.cargoes}" var="cargo">
                            <div>
                                <p>
                                    <strong>${cargo.name}</strong>
                                    <span class="pull-right text-muted">${cargo.index}</span>
                                </p>
                                <c:choose>
                                    <c:when test="${cargo.state.equals('DELIVERED')}">
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-success" role="progressbar"
                                                 aria-valuenow="15"
                                                 aria-valuemin="0" aria-valuemax="15"
                                                 style="width: 100%">
                                                <span class="sr-only">Delivered</span>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${cargo.state.equals('DESTINATION')}">
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-primary" role="progressbar"
                                                 aria-valuenow="13}"
                                                 aria-valuemin="0" aria-valuemax="15"
                                                 style="width: 90%">
                                                <span class="sr-only">On it's destination </span>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${cargo.route.size() == 0}">
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-warning" role="progressbar"
                                                 aria-valuenow="2}"
                                                 aria-valuemin="0" aria-valuemax="15"
                                                 style="width: 15%">
                                                <span class="sr-only">15% Complete</span>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${cargo.route.size()>6}">
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-warning" role="progressbar"
                                                 aria-valuenow="10}"
                                                 aria-valuemin="0" aria-valuemax="15"
                                                 style="width: 75%">
                                                <span class="sr-only">75% Complete</span>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-warning" role="progressbar"
                                                 aria-valuenow="${cargo.route.size()*2}"
                                                 aria-valuemin="0" aria-valuemax="15"
                                                 style="width: ${cargo.route.size()*2}0%">
                                                <span class="sr-only">${cargo.route.size()*2}0% Complete</span>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </div>
    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>
</body>
</html>
