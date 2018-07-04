<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Medrag Logistics Warehouse Page
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="../../../resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- MetisMenu CSS -->
    <link href="../../../resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="../../../resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

    <!-- DataTables Responsive CSS -->
    <link href="../../../resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet" type="text/css">

    <!-- Custom CSS -->
    <link href="../../../resources/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css">

    <!-- Custom Fonts -->
    <link href="../../../resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <%--This button starts a process of adding new orderr.--%>
                        <%--First step is adding new customer or choosing one of added earlier.--%>
                        <%--goto .../warehouse/CustomerController, GetMethod--%>
                        <a class="btn btn-primary" href="/whm-newCustomer" role="button">Add new orderr</a>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <table width="100%" class="table table-striped table-bordered table-hover" id="orderr-Table">
                            <thead>
                            <tr>
                                <th>Cargo number</th>
                                <th>Cargo name</th>
                                <th>Cargo state</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${cargos}" var="cargo">
                                <tr class="odd gradeX">
                                    <td>${cargo.cargoIndex}</td>
                                    <td>${cargo.name}</td>
                                    <td>${cargo.state}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>


        <!-- Modal window add city-->
        <div class="modal fade" id="addNewCity" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="jumbotron" style="margin-top: 20px;">
                            <div class="text-center">

                                <form:form class="form" method="post" modelAttribute="city"
                                           action="/mgr-city/addCity">

                                    <div class="row justify-content-sm-center">
                                        <form:input name="name" placeholder="City" path="name" autofocus="true"
                                                    class="form-control col-8"/>
                                    </div>

                                    <div class="row justify-content-center">
                                        <form:input name="x" placeholder="X" path="coordinates_X"
                                                    class="form-control col-8"/>
                                    </div>

                                    <div class="row justify-content-sm-center">
                                        <form:input name="y" placeholder="Y" path="coordinates_Y"
                                                    class="form-control col-8"/>
                                    </div>

                                    <br>
                                    <button class="btn btn-success">Add city</button>
                                </form:form>
                                <div class="text-primary text-right">
                                    <a href="/mgr-city/printCity">print City</a>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal window add driver-->
        <div class="modal fade" id="addNewDriver" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="jumbotron" style="margin-top: 20px;">
                            <div class="text-center">
                                <h1>Add new driver</h1>

                                <form:form class="form" method="post" modelAttribute="driver"
                                           action="/mgr-driver/addDriver">

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
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal window add truck-->
        <div class="modal fade" id="addNewTruck" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="jumbotron" style="margin-top: 20px;">
                        <div class="text-center">
                            <h1>Add new truck</h1>

                            <form:form class="form" method="post" modelAttribute="truck"
                                       action="/mgr-truck/addTruck">

                                <div class="row justify-content-sm-center">
                                    <form:input name="number" placeholder="number" path="regNumber"
                                                class="form-control col-8"/>
                                </div>

                                <div class="row justify-content-center">
                                    <form:input name="brigade" placeholder="brigade" path="brigadeStr"
                                                class="form-control col-8"/>
                                </div>

                                <div class="row justify-content-sm-center">
                                    <form:input name="capacity" placeholder="capacity" path="capacity"
                                                class="form-control col-8"/>
                                </div>

                                <div class="row justify-content-sm-center">
                                    <form:input name="state" placeholder="state" path="state"
                                                class="form-control col-8"/>
                                </div>

                                <div class="row justify-content-sm-center">
                                    <form:input name="currentCity" placeholder="currentCity" path="currentCity"
                                                class="form-control col-8"/>
                                </div>

                                <br>
                                <button class="btn btn-success">Add truck</button>

                            </form:form>

                            <div class="text-primary text-right">
                                <a href="${contextPath}/mgr-truck/printTruck">print Truck</a>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="../../../resources/vendor/jquery/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="../../../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="../../../resources/vendor/metisMenu/metisMenu.min.js"></script>

        <!-- DataTables JavaScript -->
        <script src="../../../resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
        <script src="../../../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
        <script src="../../../resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="../../../resources/dist/js/sb-admin-2.js"></script>

        <!-- Page-Level Demo Scripts - Tables - Use for reference -->
        <script>
            $(document).ready(function () {
                $('#orderr-Table').DataTable({
                    responsive: true
                });
            });
        </script>
    </div>
    <div class="footer">
        <p><a href="dbfs">&copy; DBFS 20!8</a></p>
    </div>
</div>
</body>
</html>
