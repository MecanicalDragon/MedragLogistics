<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add new orderr
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h2>Step 1: add new customer or just type in the document id of existing one.</h2>

            <form:form class="form" method="post" modelAttribute="newCustomer"
                       action="/whm-newCustomer/clarify">

                <spring:bind path="passport">
                    <div class="row justify-content-center">
                        <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                            <form:input name="document" placeholder="document" path="passport"
                                        class="form-control"/>
                        </div>
                            <div class="secondary-text text-left text-danger">
                                    <form:errors path="passport"/>
                            </div>
                    </div>
                </spring:bind>

                <spring:bind path="name">
                    <div class="row justify-content-center">
                        <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                            <form:input name="name" placeholder="name" path="name"
                                        class="form-control"/>
                        </div>
                            <div class="secondary-text text-left text-danger">
                                    <form:errors path="name"/>
                            </div>
                    </div>
                </spring:bind>

                <spring:bind path="surname">
                    <div class="row justify-content-center">
                        <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                            <form:input name="surname" placeholder="surname" path="surname"
                                        class="form-control"/>
                        </div>
                            <div class="secondary-text text-left text-danger">
                                    <form:errors path="surname"/>
                            </div>
                    </div>
                </spring:bind>

                <div class="row justify-content-center">
                    <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                        <form:input name="email" placeholder="email" path="email"
                                    class="form-control"/>
                    </div>
                    <div class="secondary-text text-left text-danger">
                        <form:errors path="email"/>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">
                        <form:input name="phone" placeholder="phone" path="phone"
                                    class="form-control"/>
                    </div>
                </div>


                <br>
                <div class="row justify-content-center">
                    <div class="btn-group">

                        <a class="btn btn-danger" href="${contextPath}/whm-main" role="button">Dismiss orderr</a>
                    </div>
                    <div class="btn-group">

                        <button class="btn btn-success">Add Customer</button>
                    </div>

                </div>
            </form:form>


        </div>
    </div>
    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>


<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>


</body>
</html>
