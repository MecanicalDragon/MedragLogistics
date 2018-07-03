<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add new order
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>Step 1: add new customer or just type in the document id of existing one.</h1>

            <form:form class="form" method="post" modelAttribute="newCustomer"
                       action="/whm-newCustomer/clarify">

                <div class="row justify-content-center">
                    <form:input name="document" placeholder="document" path="passport"
                                class="form-control col-6"/>
                </div>

                <div class="row justify-content-center">
                    <form:input name="name" placeholder="name" path="name"
                                class="form-control col-6"/>
                </div>

                <div class="row justify-content-center">
                    <form:input name="surname" placeholder="surname" path="surname"
                                class="form-control col-6"/>
                </div>

                <div class="row justify-content-center">
                    <form:input name="phone" placeholder="phone" path="phone"
                                class="form-control col-6"/>
                </div>

                <div class="row justify-content-center">
                    <form:input name="email" placeholder="email" path="email"
                                class="form-control col-6"/>
                </div>

                <br>
                <div class="row justify-content-center">
                    <div class="btn-group">

                    <a class="btn btn-secondary" href="/whm-main" role="button">Dismiss order</a>
                    </div>
                    <div class="btn-group">

                    <button class="btn btn-success">Add Customer</button>
                    </div>

                </div>
            </form:form>


        </div>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
