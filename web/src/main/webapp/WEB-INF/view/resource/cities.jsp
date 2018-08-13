<!DOCTYPE html>
<%@ page buffer="16kb" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Cities Page
    </title>
    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">

    <%--Heading Tab Panel--%>
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="${contextPath}/rsm-city">Cities</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-driver">Drivers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-truck">Trucks</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-user">Users</a>
        </li>
    </ul>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <c:choose>
                        <c:when test="${err}">
                            <%--Add new City Error Button--%>
                            <button class="btn btn-danger" data-toggle="modal" id="wasntAdded"
                                    data-target="#addNewCityModal">Wasn't added!
                            </button>
                        </c:when>
                        <c:otherwise>
                            <%--Add new City Button--%>
                            <button class="btn btn-primary" data-toggle="modal"
                                    data-target="#addNewCityModal">Add new City
                            </button>
                        </c:otherwise>
                    </c:choose>

                    <%--This will be shown in wrong edit case--%>
                    <c:if test="${editErr}">
                        <button class="btn btn-danger" data-toggle="modal" id="editButton"
                                data-target="#editCityModal">Wasn't saved!
                        </button>
                    </c:if>

                    <%--Logout button--%>
                    <div class="pull-right">
                        <form method="post" action="/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                            <button class="btn btn-danger offset-xs-6">Logout</button>
                        </form>
                    </div>

                </div>
                <%--Cities Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>City name</th>
                            <th>City index</th>
                            <th>Coordinates X</th>
                            <th>Coordinates Y</th>
                            <th>Edit</th>
                            <th>Remove</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.cities}" var="cityUnit" varStatus="index">

                            <tr class="odd gradeX">
                                <td>${cityUnit.name}<span hidden>XXX${cityUnit.id}XXX${cityUnit.name}XXX${index.index}XXX</span>
                                </td>
                                <td>${cityUnit.index}</td>
                                <td>${cityUnit.coordinatesX}</td>
                                <td>${cityUnit.coordinatesY}</td>
                                <td>
                                    <a type="button" class="btn btn-edit btn-warning btn-xs"
                                       data-toggle="modal" data-target="#editCityModal">Edit city data</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs btn-remove"
                                       data-toggle="modal" data-target="#deleteCityModal">Remove city</a>
                                </td>
                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>

</div>

<!-- Modal window add city-->
<div class="modal fade" id="addNewCityModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add new City</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="jumbotron" style="margin-bottom: 2px;">

                        <form:form class="form" id="addNewCityForm" method="post" modelAttribute="city"
                                   action="${contextPath}/rsm-city/addCity">

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="name">
                                        <form:input name="name" placeholder="City" path="name" autofocus="true"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="name"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="index">
                                        <form:input name="index" placeholder="index" path="index"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="index"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="coordinatesX">
                                        <form:input name="x" placeholder="latitude" path="coordinatesX"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="coordinatesX"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="coordinatesY">
                                        <form:input name="y" placeholder="longitude" path="coordinatesY"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="coordinatesY"/>
                                </div>
                            </div>

                        </form:form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="addNewCityForm">Add new City</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal window edit city-->
<div class="modal fade" id="editCityModal" tabindex="-1" role="dialog" aria-labelledby="editdtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="editdtoLabel"></h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="jumbotron" style="margin-bottom: 2px;">

                        <form:form class="form" id="editCityForm" method="post" modelAttribute="editingCity"
                                   action="${contextPath}/rsm-city/editCity">

                            <spring:bind path="id">
                                <form:input type="hidden" name="id" value="" path="id" id="editeddtoId"/>
                            </spring:bind>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="name">
                                        <form:input name="name" placeholder="City name" path="name"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="name"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="index">
                                        <form:input name="index" placeholder="index" path="index"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="index"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="coordinatesX">
                                        <form:input name="x" placeholder="latitude" path="coordinatesX"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="coordinatesX"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-3">
                                    <spring:bind path="coordinatesY">
                                        <form:input name="y" placeholder="longitude" path="coordinatesY"
                                                    class="form-control col-8"/>
                                    </spring:bind>
                                </div>
                                <div class="secondary-text text-center text-danger">
                                    <form:errors path="coordinatesY"/>
                                </div>
                            </div>

                        </form:form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="editCityForm">Accept changes</button>
            </div>
        </div>
    </div>
</div>

<%--Modal window remove city--%>
<div class="modal fade" id="deleteCityModal" tabindex="-1" role="dialog" aria-labelledby="deldtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="deldtoLabel"></h4>
            </div>
            <div class="modal-body">
                <h2 id="deletingdtoQuestion"></h2>
            </div>
            <form action="${contextPath}/rsm-city/remove" method="POST" id="targetForm">
                <input type="hidden" id="targetField" name="index" value="">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button class="btn btn-danger" form="targetForm" id="deldtoButton">Remove city</button>
            </div>
        </div>
    </div>
</div>

<%--Modal window couldn't remove city--%>
<div class="modal fade" id="notSoFast" tabindex="-1" role="dialog" aria-labelledby="nr"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="nr"><b>Not so fast...</b></h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="jumbotron" style="margin-bottom: 1px; margin-top: 1px;">
                        <h3>Couldn't remove this city: it still has active orders, drivers or trucks.</h3>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>

<%--Datatable buttons handler and automatically opened modal window script--%>
<script src="/resources/js/rsm-tables-handler.js"></script>
<script src="/resources/js/wasnt-added.js"></script>

<script>
    <c:if test="${active != null && active == true}">
    $(window).on('load',function(){
        $('#notSoFast').modal('show');
    });
    </c:if>
</script>

</body>
</html>