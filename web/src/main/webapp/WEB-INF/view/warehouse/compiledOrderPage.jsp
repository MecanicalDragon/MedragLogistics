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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <h1>Order compiled!</h1>
        <h3>Customer's name: ${order.owner.name} ${order.owner.surname}</h3>
        <h3>Order index: ${order.orderIndex}</h3>
        <h3>Certifying document: ${order.owner.passport}</h3>
        <h3>Taken cargos:</h3>
        <c:forEach items="${order.waypoints}" var="waypoint">
            <h5>Cargo index: ${waypoint.cargo.cargoIndex}</h5>
            <h5>Cargo name: ${waypoint.cargo.name}</h5>
            <h5>Cargo owner: ${waypoint.cargo.owner.name} ${waypoint.cargo.owner.surname}</h5>
            <h5>Cargo weight: ${waypoint.cargo.weight}</h5>
            <h5>Cargo status: ${waypoint.cargo.state}</h5>
            <h5>Cargo point: ${waypoint.city.name}</h5>
            <h5>Cargo action type ${waypoint.wayPointType}:</h5>
        </c:forEach>
        <div  class="text-primary text-right">
            <a href="${contextPath}/whm-main">To the naim warehouse page</a>
        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
