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
            height: 640px; /* The height is 400 pixels */
            width: 100%; /* The width is the width of the web page */
            margin-top: 20px;
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
                        <h3>Step 3: Choose the next destination city (departure - ${sessionScope.departureCity.name})</h3>
                    </div>
                </div>
                <div class="panel-body">
                    <div id="map" class="jumbotron"></div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="text-center">

                            <a class="btn btn-danger" href="${contextPath}/mgr-main" role="button">Dismiss</a>
                            <span id="question" style="font-weight: bold;"></span>
                            <button class="btn btn-success" form="targetForm" id="confirm" disabled>Confirm</button>
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

<form action="${contextPath}/mgr-destination" method="POST" id="targetForm">
    <input type="hidden" id="targetField" name="index" value="">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<!-- jQuery -->
<script src="/resources/vendor/jquery/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
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
        var departure = {lat: ${sessionScope.departureCity.coordinatesX}, lng: ${sessionScope.departureCity.coordinatesY}};
        var image = '/resources/vendor/images/marker.png';
        var map = new google.maps.Map(
            document.getElementById('map'), {zoom: 6, center: departure});
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