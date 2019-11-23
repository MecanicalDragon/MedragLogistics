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
        Choose city
    </title>

    <link href="/resources/vendor/images/favicon.ico" rel="shortcut icon">
    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <style>
        #map {
            height: 500px; /* The height is 400 pixels */
            width: 100%; /* The width is the width of the web page */
            margin-top: 1px;
            margin-bottom: 1px;
        }
    </style>
</head>
<body>
<br>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="text-center">
                        <h3>${reroute == null ? 'Step 3: Choose the next destination city (departure - '.concat(sessionScope.departureCity.name)
                                .concat(').') : 'Step 2: Choose the next destination.'}</h3>
                    </div>
                </div>
                <div class="panel-body">
                    <div id="map" class="jumbotron"></div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="text-center">
                            <c:if test="${reroute == null}">
                            <a class="btn btn-warning" href="${contextPath}/mgr-addCargoes" role="button">< Back</a>
                            </c:if>
                            <a class="btn btn-danger"
                               href="${contextPath}/${reroute == null ? 'mgr-main' : 'mgr-route'}"
                               role="button">Dismiss</a>
                            <span id="question" style="font-weight: bold;"></span>
                            <c:choose>
                                <c:when test="${reroute == null}">
                                    <button class="btn btn-success" form="targetForm" id="confirm" disabled>Next >
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-success" data-toggle="modal" data-target="#oldBrigadeModal"
                                            id="confirm" disabled>Next >
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        <p><a href="${contextPath}/dbfs">&copy; Medrag Logistics 20!8</a></p>
    </div>

</div>

<div class="modal fade" id="oldBrigadeModal" tabindex="-1" role="dialog" aria-labelledby="oldBrigadeModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Try to assign to the route current brigade?</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="jumbotron">
                        <c:forEach items="${sessionScope.chosenTruck.brigade}" var="driver">
                            <c:if test="${driver.destinationId == sessionScope.chosenTruck.destinationId}">
                            <div class='row'>
                                <div class='col-xs-6 text-left'>
                                    <h4>${driver.personalNumber}</h4>
                                </div>
                                <div class='col-xs-6 text-right'>
                                    <h4>${driver.name} ${driver.surname}</h4>
                                </div>
                            </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-danger" form="targetForm" id="no" style="width:100px;">No
                </button>
                <button class="btn btn-success" form="targetForm" id="yes" style="width:100px;">Yes
                </button>
            </div>
        </div>
    </div>
</div>

<form action="${contextPath}/${reroute == null ? 'mgr-assignDrivers' : 'mgr-compileRoute/uncompleted'}" method="POST"
      id="targetForm">
    <input type="hidden" id="targetField" name="index" value="">
    <c:if test="${reroute != null}">
        <input type="hidden" id="currentBrigadeBoolean" name="currentBrigade" value="">
    </c:if>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function () {
        $("#no").click(function () {
            $("#currentBrigadeBoolean").val(false);
        });
        $("#yes").click(function () {
            $("#currentBrigadeBoolean").val(true);
        });
    });
</script>

<!-- Map Initializing Script -->
<script>
    function initMap() {
//        Initializing style
        var style = [
            {
                featureType: "administrative.city",
                elementType: "labels",
                stylers: [
                    {visibility: "off"}
                ]
            }
        ];

//        Initializing map and departure point.
        var departure = {
            lat: ${sessionScope.departureCity.coordinatesX},
            lng: ${sessionScope.departureCity.coordinatesY}
        };
        var image = '/resources/vendor/images/marker.png';
        var map = new google.maps.Map(
            document.getElementById('map'), {zoom: 6, center: departure, disableDefaultUI: true});
        map.setOptions({styles: style});
        var departureMarker = new google.maps.Marker({
            position: departure,
            map: map,
            icon: image,
            label: {
                text: "${sessionScope.departureCity.name}",
                fontSize: "20px",
                fontWeight: "700"
            }
        });

//        Adding other city markers
        <c:forEach items="${sessionScope.cities}" var="city" varStatus="index">
        var position = {lat: ${city.coordinatesX}, lng: ${city.coordinatesY}};
        if (${!sessionScope.departureCity.name.equals(city.name)}) {
            var marker = new google.maps.Marker({
                position: position,
                map: map,
                label: {
                    text: "${city.name}",
                    fontSize: "20px",
                    fontWeight: "700"
                },
                icon: image,
                id: "${index.index}"
            });

//            Adding listener for open modal window
            google.maps.event.addListener(marker, 'click', function () {
                $("#question").text("Route truck to the city ${city.name}?");
                $("#targetField").val(${index.index});
                $('#confirm').prop('disabled', false);
            });
        }
        </c:forEach>
    }
</script>

<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDGfoBub9yxLqFtIVYk_bwSE7Kn8SSvkdI&callback=initMap">
</script>

</body>

</html>