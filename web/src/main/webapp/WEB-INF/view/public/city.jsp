<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Medrag Logistics
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>Cities</h1>

            <form:form class="form" method="post" modelAttribute="city" action="/city/dbin">

                <div class="row justify-content-sm-center">
                    <form:input name="name" placeholder="City" path="name" autofocus="true"
                                class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-center">
                    <form:input name="x" placeholder="X" path="coordinates_X"
                                class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <div class="row justify-content-sm-center">
                    <form:input name="y" placeholder="Y" path="coordinates_Y"
                           class="form-control col-8 col-sm-8 col-md-6 col-lg-4 col-xl-2"/>
                </div>

                <br>
                <button class="btn btn-success">Add city</button>
                <br>
            </form:form>
                <div class="text-primary text-right">
                    <a href="/truck">to Trucks</a>
                </div>
                <div class="text-primary text-right">
                    <a href="/driver">to Drivers</a>
                </div>
            <div class="text-primary text-right">
                <a href="/city/printCity">print City</a>
            </div>

        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
