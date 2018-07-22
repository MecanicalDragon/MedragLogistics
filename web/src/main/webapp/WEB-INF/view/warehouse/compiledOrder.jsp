<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Order compiled
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <!-- My palitre -->
    <link href="/resources/css/palitre.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <h2 class="greentext">Order compiled!</h2>
        <h4>Customer's name: <span class="othertext">${order.owner.name} ${order.owner.surname}</span></h4>
        <h4>Order index: <span class="othertext">${order.index}</span></h4>
        <h4>Certifying document: <span class="othertext">${order.owner.passport}</span></h4>
        <br>
        <h4>Taken cargoes:</h4>

        <c:forEach items="${order.cargoes}" var="cargo">
            <h5>Cargo index: <span class="bluetext">${cargo.index}</span> </h5>
            <h5>Cargo name: <span class="bluetext">${cargo.name}</span> </h5>
            <h5>Departure: <span class="bluetext">${cargo.departureName}</span> </h5>
            <h5>Destination: <span class="bluetext">${cargo.destinationName}</span> </h5>
            <br>
        </c:forEach>

        <div  class="text-primary text-right">
            <a href="${contextPath}/whm-main">To the main warehouse page</a>
        </div>
    </div>

    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>
</body>
</html>
