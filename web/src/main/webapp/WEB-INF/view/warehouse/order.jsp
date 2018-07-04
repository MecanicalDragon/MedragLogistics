<%@ page import="net.medrag.dto.CustomerDto" %>
<%@ page import="net.medrag.dto.CargoDto" %>
<%@ page import="java.util.List" %>
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

            <h1>Step 2: forming the order.</h1>

            <h3>Cargo list of customer ${sessionScope.owner.name} ${sessionScope.owner.surname}, document
                # ${sessionScope.owner.passport}</h3>

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
                        <td>${cargoItem.departure.name}</td>
                        <td>${cargoItem.destination.name}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <div class="row">
                <a class="btn btn-secondary" href="${contextPath}/whm-main" role="button">Dismiss order</a>

                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                        data-target="#addCargoModal">
                    Add new cargo
                </button>

                <a class="btn btn-success" href="${contextPath}/whm-order/compile" role="button">Compile order</a>
            </div>
            <div class="modal fade" id="addCargoModal" tabindex="-1" role="dialog"
                 aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalCenterTitle">Add new cargo</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">

                                <form:form class="form" id="addNewCargo" method="post" modelAttribute="cargo"
                                           action="${contextPath}/whm-cargo/addCargo">

                                    <div class="row">
                                        <spring:bind path="name">
                                            <form:input name="name" placeholder="name" path="name" autofocus="true"
                                                        class="form-control col-8"/>
                                            <div class="secondary-text text-center text-danger">
                                                <div class="font-italic">
                                                    <form:errors path="name"/>
                                                </div>
                                            </div>
                                        </spring:bind>
                                    </div>

                                    <div class="row">
                                        <spring:bind path="weight">
                                            <form:input name="weight" placeholder="weight" path="weight"
                                                        class="form-control col-8"/>
                                            <div class="secondary-text text-center text-danger">
                                                <div class="font-italic">
                                                    <form:errors path="weight"/>
                                                </div>
                                            </div>
                                        </spring:bind>
                                    </div>
                                    <div class="row">
                                        <spring:bind path="departure">
                                            <form:input name="departure" placeholder="departure" path="departure"
                                                        class="form-control col-8"/>
                                            <div class="secondary-text text-center text-danger">
                                                <div class="font-italic">
                                                    <form:errors path="departure"/>
                                                </div>
                                            </div>
                                        </spring:bind>
                                    </div>
                                    <div class="row">
                                        <spring:bind path="destination">
                                            <form:input name="destination" placeholder="destination" path="destination"
                                                        class="form-control col-8"/>
                                            <div class="secondary-text text-center text-danger">
                                                <div class="font-italic">
                                                    <form:errors path="destination"/>
                                                </div>
                                            </div>
                                        </spring:bind>
                                    </div>
                                </form:form>

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">cancel</button>
                            <button class="btn btn-success" form="addNewCargo">Add new Cargo
                            </button>
                        </div>
                    </div>
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
