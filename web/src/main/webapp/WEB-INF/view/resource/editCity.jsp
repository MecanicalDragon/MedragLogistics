<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add new driver
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">
            <h1>Editing city ${editingCity.name}</h1>
            <br>
            <form:form class="form" id="editCityForm" method="post" modelAttribute="editingCity"
                       action="${contextPath}/rsm-city/acceptEdit">

                <spring:bind path="id">
                    <form:input mane="id" type="hidden" path="id" value="${editingCity.id}"/>
                </spring:bind>


                <spring:bind path="name">
                    <form:input name="name" type="hidden" value="${editingCity.name}" path="name"/>
                </spring:bind>
            

                <div class="row-justify-content-center">
                    <div class="col-sm-6">
                        <spring:bind path="index">
                            <form:input name="index" placeholder="${editingCity.index}" path="index" autofocus="true"
                                        class="form-control col-6"/>
                        </spring:bind>
                    </div>
                    <div class="secondary-text text-center text-danger">
                        <div class="font-italic">
                            <form:errors path="index"/>
                        </div>
                    </div>
                </div>

                <div class="row-justify-content-center">
                    <div class="col-sm-6">
                        <spring:bind path="coordinatesX">
                            <form:input name="x" placeholder="${editingCity.coordinatesX}" path="coordinatesX"
                                        class="form-control col-6"/>
                        </spring:bind>
                    </div>
                    <div class="secondary-text text-center text-danger">
                        <div class="font-italic">
                            <form:errors path="coordinatesX"/>
                        </div>
                    </div>
                </div>

                <div class="row-justify-content-center">
                    <div class="col-sm-6">
                        <spring:bind path="coordinatesY">
                            <form:input name="y" placeholder="${editingCity.coordinatesY}" path="coordinatesY"
                                        class="form-control col-6"/>
                        </spring:bind>
                    </div>
                    <div class="secondary-text text-center text-danger">
                        <div class="font-italic">
                            <form:errors path="coordinatesY"/>
                        </div>
                    </div>
                </div>
                <br>
                <%--<button class="btn btn-success">Accept changes</button>--%>
            </form:form>
            <br>
            <div class="row justify-content-around">
                <a type="button" class="btn btn-danger" href="${contextPath}/rsm-city">Cancel changes</a>
                <button type="submit" class="btn btn-success" form="editCityForm">Accept changes</button>
            </div>
        </div>
    </div>
</div>
<div class="footer">
    <p><a href="dbfs">&copy; DBFS 20!8</a></p>
</div>
</div>
</body>
</html>
