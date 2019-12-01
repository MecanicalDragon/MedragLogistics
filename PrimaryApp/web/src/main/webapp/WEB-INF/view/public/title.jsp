<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Medrag Logistics</title>
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- title window css -->
    <link href="/resources/css/title.css" rel="stylesheet" type="text/css">

</head>
<body>
<br>
<br>
<br>
<div class="container">
    <div id="login" class="signin-card">
        <div class="logo-image">
            <img src="/resources/vendor/images/favicon.png" alt="Logo" title="Logo"
                 width="150">
        </div>
        <h1 class="display1">Medrag Logistics</h1>
        <p class="subhead">Next Generation Trucking Company</p>
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <br>
                <form method="post" action="${contextPath}/logout">
                    <div class="text-center">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="text-primary">
                            You signed as a ${pageContext.request.userPrincipal.name} Personal Number
                        </div>
                        <br>
                        <a href="${contextPath}/title/identify" role="button" class="btn btn-block btn-warning">Back to
                            work</a>
                        <button class="btn btn-block btn-danger">Logout</button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <form class="form-signin" method="post" action="${contextPath}/tryLog">
                    <div id="form-login-username" class="form-group">
                        <input class="form-control" placeholder="Personal ID" name="username"
                               type="text" size="18" alt="login" id="username" autofocus>
                            <%--<span class="form-highlight"></span>--%>
                            <%--<span class="form-bar"></span>--%>
                    </div>
                    <br>
                    <div id="form-login-password" class="form-group">
                        <input id="password" class="form-control" name="password" placeholder="Password" type="password"
                               size="18" alt="password">
                            <%--<span class="form-highlight"></span>--%>
                            <%--<span class="form-bar"></span>--%>
                    </div>
                    <div class="row justify-content-center">
                        <div class="secondary-text text-danger">
                            <c:choose>
                                <c:when test="${error == null}">
                                    <br>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center">
                                        <span>${error}</span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <br>
                    <button class="btn btn-block btn-primary ripple-effect" type="submit" name="Submit" alt="sign in">Sign
                        in
                    </button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
