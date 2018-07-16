<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add new order
    </title>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="text-center">

            <h2>Step 2: forming the order.</h2>

            <h3>Cargo list of customer ${sessionScope.owner.name} ${sessionScope.owner.surname}, document
                # ${sessionScope.owner.passport}</h3>

            <%--Added cargoes table--%>
            <table width="100%" class="table table-striped table-bordered table-hover" id="cargo-Table">
                <thead>
                <tr>
                    <th>Cargo name</th>
                    <th>Cargo weight</th>
                    <th>Departure</th>
                    <th>Destination</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${sessionScope.cargoList}" var="cargoItem">
                    <tr class="odd gradeX">
                        <td>${cargoItem.name}</td>
                        <td>${cargoItem.weight}</td>
                        <td>${cargoItem.departureName}</td>
                        <td>${cargoItem.destinationName}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <div class="row">
                <a class="btn btn-danger" href="${contextPath}/whm-main" role="button">Dismiss orderr</a>

                <%--Add cargo Buttons--%>
                <c:choose>
                    <c:when test="${err}">
                        <%--Add new Cargo Error Button--%>
                        <button class="btn btn-danger" data-toggle="modal" id="wasntAdded"
                                data-target="#addCargoModal">Wasn't added!
                        </button>
                    </c:when>
                    <c:otherwise>
                        <%--Add new Cargo Button--%>
                        <button class="btn btn-primary" data-toggle="modal"
                                data-target="#addCargoModal">Add new Cargo
                        </button>
                    </c:otherwise>
                </c:choose>

                <a class="btn btn-success" href="${contextPath}/whm-order/compile" role="button">Compile orderr</a>
            </div>
        </div>
    </div>
    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>
</div>


<%--Add cargo modal window--%>
<div class="modal fade" id="addCargoModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title" id="cargoModalHeader">Add new cargo</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="jumbotron" style="margin-bottom: 2px;">

                    <form:form class="form" id="addNewCargo" method="post" modelAttribute="cargo"
                               action="${contextPath}/whm-cargo/addCargo">

                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-3">
                                <spring:bind path="name">
                                    <form:input name="name" placeholder="name" path="name" autofocus="true"
                                                class="form-control col-8"/>
                                </spring:bind>
                            </div>
                            <div class="secondary-text text-center text-danger">
                                <div class="font-italic">
                                    <form:errors path="name"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-3">
                                <spring:bind path="weight">
                                    <form:input name="weight" placeholder="weight" path="weight"
                                                class="form-control col-8"/>
                                </spring:bind>
                            </div>
                            <div class="secondary-text text-center text-danger">
                                <div class="font-italic">
                                    <form:errors path="weight"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-3">
                                <spring:bind path="departureName">
                                    <form:input name="departureName" placeholder="departure" path="departureName"
                                                class="form-control col-8"/>
                                </spring:bind>
                            </div>
                            <div class="secondary-text text-center text-danger">
                                <div class="font-italic">
                                    <form:errors path="departureName"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-3">
                                <spring:bind path="destinationName">
                                    <form:input name="destinationName" placeholder="destination"
                                                path="destinationName"
                                                class="form-control col-8"/>
                                </spring:bind>
                            </div>
                            <div class="secondary-text text-center text-danger">
                                <div class="font-italic">
                                    <form:errors path="destinationName"/>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                <button class="btn btn-success" form="addNewCargo">Add new Cargo
                </button>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/resources/js/wasnt-added.js"></script>

</body>
</html>
