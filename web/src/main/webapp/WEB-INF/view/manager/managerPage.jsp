
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Medrag Logistics Manager Page
    </title>

    <%--Bootstrap--%>
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous">
    </script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous">
    </script>

    <%--Datatadles--%>
    <link rel="stylesheet" type="text/html"
          href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/jqc-1.12.3/dt-1.10.17/b-1.5.2/sl-1.2.6/datatables.min.css"/>
    <link rel="stylesheet" type="text/css" href="Editor-1.7.4/css/editor.dataTables.css">

    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/jqc-1.12.3/dt-1.10.17/b-1.5.2/sl-1.2.6/datatables.min.js"></script>
    <script type="text/javascript" src="Editor-1.7.4/js/dataTables.editor.js"></script>

    <%--DATATABLES--%>
    <base href="/">

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet" type="text/css">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">
        <div class="row">
        <!-- Button trigger add new entities -->
            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addNewOrder">
                Add new order
            </button>
        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addNewCity">
            Add new city
        </button>
            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addNewDriver">
                Add new driver
            </button>
            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addNewTruck">
                Add new truck
            </button>
        </div>

        <!-- Modal window add order-->
        <div class="modal fade" id="addNewOrder" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">

                        <jsp:include page="/mgr-city"/>
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

                        <jsp:include page="/mgr-city"/>
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

                        <jsp:include page="/mgr-driver"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal window add truck-->
        <div class="modal fade" id="addNewTruck" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">

                        <jsp:include page="/mgr-truck"/>
                    </div>
                </div>
            </div>
        </div>

            <table width="100%" class="table table-striped table-bordered table-hover" id="order-Table">
                <thead>
                <tr>
                    <th>Order number</th>
                    <th>Departure</th>
                    <th>Destination</th>
                    <th>Engine version</th>
                    <th>CSS grade</th>
                </tr>
                </thead>
                <tbody>

<c:forEach items="${orders}" var="order">
                <tr class="odd gradeX">
                    <td>${order.orderNumber}</td>
                    <td>${order.owner.name}</td>
                    <td>${order.implemented}</td>
                    <td class="center">${orders.size()}</td>
                    <td class="center">X</td>
                </tr>
</c:forEach>

                </tbody>
            </table>
        <script src="/resources/vendor/jquery/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Metis Menu Plugin JavaScript -->
        <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

        <!-- DataTables JavaScript -->
        <script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
        <script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
        <script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

        <!-- Custom Theme JavaScript -->
        <script src="/resources/dist/js/sb-admin-2.js"></script>

        <!-- Page-Level Demo Scripts - Tables - Use for reference -->
        <script>
            $(document).ready(function() {
                $('#order-Table').DataTable({
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
