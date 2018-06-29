<%--
  Created by IntelliJ IDEA.
  User: Dragonborn
  Date: 25.06.2018
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Medrag Logistics</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>Title page of Medrag Logistics</h1>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <form method="post" action="logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="text-primary">
                                You signed in as a ${pageContext.request.userPrincipal.name}
                            </div>
                            <div>
                                <button class="btn btn-danger">Logout</button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form class="form-signin" method="post" action="/tryLog">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Personal ID" name="username" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password">
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <div class="font-italic">
                                        <span>${error}</span>
                                    </div>
                                </div>

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                    <%--<div class="checkbox">--%>
                                    <%--<label>--%>
                                    <%--<input name="remember" type="checkbox" value="Remember Me">Remember Me--%>
                                    <%--</label>--%>
                                    <%--</div>--%>
                                <!-- Change this to a button or input when using this as a form -->
                                <button class="btn btn-success">Sign in</button>
                            </fieldset>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/mgr-truck">to Trucks</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/mgr-driver">to Drivers</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/mgr-city">To Cities</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/drv-driverPage">To Driver Pages</a>
            </div>

        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
