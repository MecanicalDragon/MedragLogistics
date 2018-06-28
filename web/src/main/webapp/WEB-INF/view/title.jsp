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

            <div class="text-primary text-right">
                <a href="${contextPath}/truck">to Trucks</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/driver">to Drivers</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/city">To Cities</a>
            </div>
            <div class="text-primary text-right">
                <a href="${contextPath}/login">Log in</a>
            </div>

        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
