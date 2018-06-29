<%--
  Created by IntelliJ IDEA.
  User: Dragonborn
  Date: 29.06.2018
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Driver Page</title>

    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>This page is only for drivers</h1>

            <div class="text-primary text-right">
                <a href="${contextPath}/mgr-truck">to Trucks</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/mgr-driver">to Drivers</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/mgr-city">To Cities</a>
            </div>

        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
