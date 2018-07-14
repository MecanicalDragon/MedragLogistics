<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>
        Add cargoes
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">


</head>
<body>
<br>
<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="text-center">

                        <h3>Step 2: add more cargoes to the truck.</h3>
                    </div>
                </div>
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover"
                           id="addedCargoTable">
                        <thead>
                        <tr>
                            <th>Cargo index</th>
                            <th>Cargo weight (total weight now
                                is ${sessionScope.currentWeight}/${sessionScope.chosenTruck.capacity} kgs.)
                            </th>
                            <th>Current city</th>
                            <th>Destination</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.truckLoad}" var="cargoItem">
                            <tr class="odd gradeX">
                                <td>${cargoItem.index}</td>
                                <td>${cargoItem.weight}</td>
                                <td>${cargoItem.currentCityName}</td>
                                <td>${cargoItem.destinationName}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

                </div>
                <div class="panel-footer">
                    <div class="text-center">
                        <div class="row">
                            <a class="btn btn-danger" href="${contextPath}/mgr-main" role="button">Dismiss</a>


                            <a class="btn btn-success" href="${contextPath}/mgr-wp/chooseCity" role="button">Next
                                step</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">

                    <div class="panel-body">

                        <table width="100%" class="table table-striped table-bordered table-hover"
                               id="orderr-Table">
                            <thead>
                            <tr>
                                <th>Add cargo</th>
                                <th>Cargo index</th>
                                <th>Cargo weight (current truck
                                    load ${sessionScope.currentWeight}/${sessionScope.chosenTruck.capacity}
                                    kgs.)
                                </th>
                                <th>Destination point</th>
                                <th>Cargo state</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${sessionScope.cityCargoes}" var="cargo"
                                       varStatus="index">
                                <tr class="odd gradeX">
                                    <td>
                                        <a type="button" class="btn btn-success btn-xs start-order"
                                           href="${contextPath}/mgr-wp/addCargo/${index.index}"
                                           id="addThis:${cargo.weight}">
                                            Add this
                                        </a>
                                    </td>
                                    <td>${cargo.index}</td>
                                    <td>${cargo.weight}</td>
                                    <td>${cargo.destinationName}</td>
                                    <td>${cargo.state}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div class="footer">
    <p><a href="dbfs">&copy; DBFS 20!8</a></p>
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

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function () {
        $('#orderr-Table').DataTable({
            responsive: true
        });
        $('#addedCargoTable').DataTable({
            responsive: true
        });
    });
</script>
<script>
    $(document).ready(function () {
        $(".start-order").click(function () {
            var addWeight = $(this).attr("id").split(':')[1];
            var curWeight = parseInt("${sessionScope.currentWeight}", 10);
            var maxWeight = parseInt("${sessionScope.chosenTruck.capacity}", 10);
            var sumWeight = parseInt(addWeight, 10) + curWeight;
            if (sumWeight > maxWeight) {
                $(this).attr("href", "#");
                $(this).attr("class", "btn btn-danger btn-xs");
                $(this).text("too heavy");
            }
        });
    });
</script>

</body>
</html>
