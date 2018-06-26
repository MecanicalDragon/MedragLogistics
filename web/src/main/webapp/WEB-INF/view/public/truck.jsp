<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Medrag Logistics
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>Trucks</h1>

            <form class="form" method="post" action="${contextPath}truck/dbint">
                <div class="row justify-content-sm-center">
                    <input name="number" placeholder="number"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2">
                </div>
                <div class="row justify-content-center">
                    <input name="brigade" placeholder="brigade"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2">
                </div>
                <div class="row justify-content-sm-center">
                    <input name="capacity" placeholder="capacity"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2">
                </div>
                <div class="row justify-content-sm-center">
                    <input name="state" placeholder="state"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2">
                </div>
                <div class="row justify-content-sm-center">
                    <input name="currentCity" placeholder="currentCity"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2">
                </div>

                <br>
                <button class="btn btn-success">Add truck</button>
                <br>
                <div class="text-primary text-right">
                    <a href="${contextPath}city">to Cities</a>
                </div>
                <div class="text-primary text-right">
                    <a href="${contextPath}/driver">to Drivers</a>
                </div>
                <div class="text-primary text-right">
                    <a href="${contextPath}truck/printTruck">print Truck</a>
                </div>
            </form>

        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
