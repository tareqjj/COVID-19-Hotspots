<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Heatmaps</title>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAOKLvcXGg9zCD2zSR6gjqsszAc1l017Tc&callback=initMap&libraries=visualization&v=weekly" defer></script>
    <style>
        #map {
            height: 100%;
        }

        /* Optional: Makes the sample page fill the window. */
        html,
        body {
            height: 100%;
            margin: 0;
            padding: 0;
        }

        #floating-panel {
            position: absolute;
            top: 10px;
            left: 25%;
            z-index: 5;
            background-color: #fff;
            padding: 5px;
            border: 1px solid #999;
            text-align: center;
            font-family: "Roboto", "sans-serif";
            line-height: 30px;
            padding-left: 10px;
        }

        #floating-panel {
            background-color: #fff;
            border: 1px solid #999;
            left: 25%;
            padding: 5px;
            position: absolute;
            top: 10px;
            z-index: 5;
        }
    </style>
</head>
<body>
<div id="map"></div>
<script>
    let map, heatmap;
    function initMap() {
        const myLatLng = { lat: 31.90493919635812, lng: 35.20443782875959  };
        const map = new google.maps.Map(document.getElementById("map"), {
            zoom: 9,
            center: myLatLng,
        });
        <c:forEach items="${tests}" var="test">
            <c:forEach items="${test.locations}" var="locat">
            const x${locat.id} = { lat: ${locat.lang}, lng: ${locat.lat} };
            new google.maps.Marker({
                position: x${locat.id},
                map,
                title: "Hello World!",
            });
            </c:forEach>
        </c:forEach>
    }
</script>





<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Heatmaps</title>--%>
<%--    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>--%>
<%--    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAOKLvcXGg9zCD2zSR6gjqsszAc1l017Tc&callback=initMap&libraries=visualization&v=weekly" defer></script>--%>
<%--    <style>--%>
<%--        #map {--%>
<%--            height: 100%;--%>
<%--        }--%>

<%--        /* Optional: Makes the sample page fill the window. */--%>
<%--        html,--%>
<%--        body {--%>
<%--            height: 100%;--%>
<%--            margin: 0;--%>
<%--            padding: 0;--%>
<%--        }--%>

<%--        #floating-panel {--%>
<%--            position: absolute;--%>
<%--            top: 10px;--%>
<%--            left: 25%;--%>
<%--            z-index: 5;--%>
<%--            background-color: #fff;--%>
<%--            padding: 5px;--%>
<%--            border: 1px solid #999;--%>
<%--            text-align: center;--%>
<%--            font-family: "Roboto", "sans-serif";--%>
<%--            line-height: 30px;--%>
<%--            padding-left: 10px;--%>
<%--        }--%>

<%--        #floating-panel {--%>
<%--            background-color: #fff;--%>
<%--            border: 1px solid #999;--%>
<%--            left: 25%;--%>
<%--            padding: 5px;--%>
<%--            position: absolute;--%>
<%--            top: 10px;--%>
<%--            z-index: 5;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div id="floating-panel">--%>
<%--    <button onclick="toggleHeatmap()">Toggle Heatmap</button>--%>
<%--    <button onclick="changeGradient()">Change gradient</button>--%>
<%--    <button onclick="changeRadius()">Change radius</button>--%>
<%--    <button onclick="changeOpacity()">Change opacity</button>--%>
<%--</div>--%>
<%--<div id="map"></div>--%>
<%--<script>--%>
<%--    let map, heatmap;--%>

<%--    function initMap() {--%>
<%--        map = new google.maps.Map(document.getElementById("map"), {--%>
<%--            zoom: 9,--%>
<%--            center: { lat: 31.90497,  lng: 35.20443 },--%>
<%--            // mapTypeId: "satellite",--%>
<%--        });--%>
<%--        heatmap = new google.maps.visualization.HeatmapLayer({--%>
<%--            data: getPoints(),--%>
<%--            map: map,--%>
<%--        });--%>
<%--    }--%>

<%--    function toggleHeatmap() {--%>
<%--        heatmap.setMap(heatmap.getMap() ? null : map);--%>
<%--    }--%>

<%--    function changeGradient() {--%>
<%--        const gradient = [--%>
<%--            "rgba(0, 255, 255, 0)",--%>
<%--            "rgba(0, 255, 255, 1)",--%>
<%--            "rgba(0, 191, 255, 1)",--%>
<%--            "rgba(0, 127, 255, 1)",--%>
<%--            "rgba(0, 63, 255, 1)",--%>
<%--            "rgba(0, 0, 255, 1)",--%>
<%--            "rgba(0, 0, 223, 1)",--%>
<%--            "rgba(0, 0, 191, 1)",--%>
<%--            "rgba(0, 0, 159, 1)",--%>
<%--            "rgba(0, 0, 127, 1)",--%>
<%--            "rgba(63, 0, 91, 1)",--%>
<%--            "rgba(127, 0, 63, 1)",--%>
<%--            "rgba(191, 0, 31, 1)",--%>
<%--            "rgba(255, 0, 0, 1)",--%>
<%--        ];--%>
<%--        heatmap.set("gradient", heatmap.get("gradient") ? null : gradient);--%>
<%--    }--%>

<%--    function changeRadius() {--%>
<%--        heatmap.set("radius", heatmap.get("radius") ? null : 20);--%>
<%--    }--%>

<%--    function changeOpacity() {--%>
<%--        heatmap.set("opacity", heatmap.get("opacity") ? null : 0.2);--%>
<%--    }--%>

<%--    // Heatmap data: 500 Points--%>
<%--    function getPoints() {--%>
<%--        return [--%>
<%--            <c:forEach items="${arr}" var="locat">--%>
<%--            new google.maps.LatLng(${locat.lat}, ${locat.lang}),--%>
<%--            </c:forEach>--%>
<%--        ]--%>
<%--    }--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>
