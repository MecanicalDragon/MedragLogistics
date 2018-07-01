<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add new driver
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>Add new driver</h1>

            <form:form class="form" method="post" modelAttribute="driver" action="/mgr-driver/dbind">

                <div class="row justify-content-sm-center">
                    <form:input name="number" placeholder="number" path="personalNumber"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-center">
                    <form:input name="name" placeholder="name" path="name"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-sm-center">
                    <form:input name="surname" placeholder="surname" path="surname"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-sm-center">
                    <form:input name="time" placeholder="time" path="workedTime"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-sm-center">
                    <form:input name="state" placeholder="state" path="state"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-sm-center">
                    <form:input name="city" placeholder="city" path="currentCity"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-sm-center">
                    <form:input name="truck" placeholder="truck" path="currentTruck"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <br>
                <button class="btn btn-success">Add Driver</button>
            </form:form>

        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
