<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dragonborn
  Date: 25.06.2018
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Medrag Logistics</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h2>Title page of Medrag Logistics</h2>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <form method="post" action="${contextPath}/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="text-primary">
                                You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                            </div>
                            <p><a href="${contextPath}/title/identify">Back to work</a></p>
                            <div>
                                <button class="btn btn-danger">Logout</button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form class="form-signin" method="post" action="${contextPath}/tryLog">
                            <fieldset>
                                <div class="row justify-content-center">
                                    <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Personal ID" name="username"
                                               autofocus>
                                    </div>
                                    </div>
                                </div>
                                <div class="row justify-content-center">
                                    <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Password" name="password"
                                               type="password">
                                    </div>
                                    </div>
                                </div>
                                <div class="row justify-content-center">
                                    <div class="secondary-text text-danger">
                                            <span>${error}</span>
                                    </div>
                                </div>

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <button class="btn btn-success">Sign in</button>
                            </fieldset>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
<a href="${contextPath}/title/map">To the map</a>
        </div>
    </div>
    <div class="footer">
        <p><a href="${contextPath}/error">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>
</body>
</html>
