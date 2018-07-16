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
        Users Page
    </title>

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
            <a class="nav-link" href="${contextPath}/rsm-city">Cities</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-driver">Drivers</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${contextPath}/rsm-truck">Trucks</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="${contextPath}/rsm-user">Users</a>
        </li>
    </ul>

    <%--Data Table--%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">

                    <c:choose>
                        <c:when test="${err}">
                            <%--Add new User Error Button--%>
                            <button class="btn btn-danger" data-toggle="modal"
                                    data-target="#addNewUserModal">Wasn't added!
                            </button>
                        </c:when>
                        <c:otherwise>
                            <%--Add new User Button--%>
                            <button class="btn btn-primary" data-toggle="modal" id="wasntAdded"
                                    data-target="#addNewUserModal">Add new User
                            </button>
                        </c:otherwise>
                    </c:choose>

                    <div class="pull-right">
                        <form method="post" action="logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            You signed in under ${pageContext.request.userPrincipal.name} Personal Number
                            <button class="btn btn-danger offset-xs-6">Logout</button>
                        </form>
                    </div>

                </div>

                <%--User Table Body--%>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" id="dto-Table">
                        <thead>
                        <tr>
                            <th>User Name</th>
                            <th>Email</th>
                            <th>User role</th>
                            <th>Send new password</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.userList}" var="userUnit">

                            <tr class="odd gradeX">
                                <td>${userUnit.username}</td>
                                <td>${userUnit.email}</td>
                                <td>
                                    <c:if test="${userUnit.role.equals('ROLE_DRIVER')}">
                                        Driver
                                    </c:if>
                                    <c:if test="${userUnit.role.equals('ROLE_WAREHOUSEMAN')}">
                                        Warehouseman
                                    </c:if>
                                    <c:if test="${userUnit.role.equals('ROLE_RESOURCE')}">
                                        Resource manager
                                    </c:if>
                                    <c:if test="${userUnit.role.equals('ROLE_MANAGER')}">
                                        Logistican
                                    </c:if>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-newPassword btn-info btn-xs"
                                       id="${userUnit.id}/${userUnit.username}/${userUnit.email}"
                                       data-toggle="modal" data-target="#newPasswordModal">Send new password</a>
                                </td>
                                <td>
                                    <a type="button" class="btn btn-danger btn-xs btn-remove"
                                       id="${userUnit.id}*${userUnit.username}"
                                       data-toggle="modal" data-target="#deleteUserModal">Delete user</a>
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

<!-- Modal window New Password-->
<div class="modal fade" id="newPasswordModal" tabindex="-1" role="dialog" aria-labelledby="editdtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="editdtoLabel">New password</h4>
            </div>
            <div class="modal-body">
                <h2 id="newPasswordQuestion"></h2>

                <form class="form" id="newPasswordForm"
                      action="${contextPath}/rsm-user/generate">

                    <input type="hidden" name="id" value="" id="newPasswordId"/>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="newPasswordForm">Generate</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal window add User-->
<div class="modal fade" id="addNewUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add new user</h4>
            </div>
            <div class="modal-body">

                <form:form class="form" id="addNewUserForm" method="post" modelAttribute="newUser"
                           action="${contextPath}/rsm-user/addUser">

                    <spring:bind path="email">
                        <form:input name="email" placeholder="email" path="email" class="form-control col-8"/>
                    </spring:bind>

                    <spring:bind path="role">
                        <form:select path="role" placeholder="ololo" class="form-control">
                            <option value="rsm" id="rsm">Resource manager</option>
                            <option value="whm" id="whm">Warehouseman</option>
                            <option value="mgr" id="mgr">Logistican</option>
                        </form:select>
                    </spring:bind>

                </form:form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-success" form="addNewUserForm">Add new User</button>
            </div>
        </div>
    </div>
</div>

<%--Modal window remove User--%>
<div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="deldtoLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="deldtoLabel">Removing user</h4>
            </div>
            <div class="modal-body">
                <h2 id="deletingdtoQuestion"></h2>

                <form:form class="form" id="removableUserForm" method="post" modelAttribute="removableUser"
                           action="${contextPath}/rsm-user/remove">

                    <form:input type="hidden" name="id" value="" path="id" id="removableUserId"/>
                    <form:input type="hidden" name="id" value="" path="username" id="removableUserName"/>

                </form:form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button class="btn btn-success" form="removableUserForm">Yes, remove</button>
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

<!--My generic script-->
<script src="/resources/js/users-handler.js"></script>

</body>
</html>