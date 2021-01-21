<%--
  Created by IntelliJ IDEA.
  User: Diaa
  Date: 1/19/2021
  Time: 11:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Removing Markers</title>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAOKLvcXGg9zCD2zSR6gjqsszAc1l017Tc&callback=initMap&libraries=visualization&v=weekly" defer></script>
    <!-- jsFiddle will insert css and js -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
    </style>
</head>
<body>
<div id="floating-panel">
    <input onclick="clearMarkers();" type="button" value="Hide Markers" />
    <input onclick="deleteMarker();" type="button" value="delete Marker" />
    <input onclick="showMarkers();" type="button" value="Show All Markers" />
    <input onclick="deleteMarkers();" type="button" value="Delete All" />
    <form action="/addTest" id="form">
        <input type="number" name="id">
        <button type="submit">Submit</button>
    </form>

</div>
<div id="map"></div>
<p>Click on the map to add markers.</p>
<script>
    // In the following example, markers appear when the user clicks on the map.
    // The markers are stored in an array.
    // The user can then click an option to hide, show or delete the markers.
    let map;
    let markers = [];

    function initMap() {
        const haightAshbury = { lat: 31.90493919635812, lng: 35.20443782875959  };
        map = new google.maps.Map(document.getElementById("map"), {
            zoom: 12,
            center: haightAshbury,
            // mapTypeId: "terrain",
        });
        // This event listener will call addMarker() when the map is clicked.
        map.addListener("click", (event) => {
            addMarker(event.latLng);
        });
        // Adds a marker at the center of the map.
        addMarker(haightAshbury);
    }

    // Adds a marker to the map and push to the array.
    function addMarker(location) {
        const marker = new google.maps.Marker({
            position: location,
            map: map,
        });
        markers.push(marker);
    }

    // Sets the map on all markers in the array.
    function setMapOnAll(map) {
        for (let i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }

    // Removes the markers from the map, but keeps them in the array.
    function clearMarkers() {
        setMapOnAll(null);
    }

    // Shows any markers currently in the array.
    function showMarkers() {
        setMapOnAll(map);
    }

    // Deletes all markers in the array by removing references to them.
    function deleteMarkers() {
        clearMarkers();
        markers = [];
        console.log(markers);
    }

    // Delete last added marker
    function deleteMarker() {
        markers[markers.length - 1].setMap(null);
        markers.pop();
    }

    $("#form").submit( function(eventObj) {

        // let locations = [];
        for (let i = 0; i < markers.length; i++) {
            let arr = [];
            arr.push(markers[i].position.lat());
            arr.push(markers[i].position.lng());
            $(this).append('<input name="locations[]" type="hidden" value="'+arr+'" />');
            // locations.push(arr);
        }
        return true;
    });
</script>
</body>
</html>
