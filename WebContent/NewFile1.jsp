<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Geocoding service</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
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
        text-align: left;
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }
    </style>
  </head>
  <body>
    <div id="floating-panel">
		<form id="myform" action="/Testing/AddGeofence" method="post">
			<input id="address" name="addr" type="textbox" placeholder="address" required><br>
			<input id="radius" name="rad" type="number" placeholder="radius in m" required><br> 
			<textarea id="msg" name="msg" rows="3" cols="22" placeholder="message" required></textarea><br>
			<input id="latitude" name="lat" type="hidden" value="-34.397">
			<input id="longitude" name="lng" type="hidden" value="150.644"> 
			<input id="search" type="button" value="show location"> 
			<input id="submit" type="submit" value="create geofence">
		</form>
	</div>
    <div id="map"></div>
    <script>
    var marker;
	var citycircle;
	 var rad;
	 var i=0;
	 var infowindow;
  function initMap() {
	  var map = new google.maps.Map(document.getElementById('map'), {
	      zoom: 8,
	      center: {lat: -34.397, lng: 150.644}
	    });
	  <c:if test="${not empty geoList}">
	 var currlat = ${currgeo.lat};
	 var currlng = ${currgeo.lng};
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 12,
      center: {lat: parseFloat(currlat), lng: parseFloat(currlng)}
    });
    </c:if>
    initGeofences(map);
    google.maps.event.addListener(map, 'click', function( event ){
  	  //alert( "Latitude: "+event.latLng.lat()+" "+", longitude: "+event.latLng.lng() );
  	  if(i!=0)
  	  {
  		marker.setMap(null);
	  	cityCircle.setMap(null);
  	  }
  	  else
  		  i=1;
  	geocodeLatLng(geocoder,event.latLng.lat(),event.latLng.lng(),map)
	 rad = document.getElementById('radius').value;
  	createGeofence(event.latLng.lat(),event.latLng.lng(),rad,map);
  	});
    document.getElementById('submit').style.display='none';
    var geocoder = new google.maps.Geocoder();
    document.getElementById('search').addEventListener('click', function() {
      geocodeAddress(geocoder, map);
    });
  }
   
  function geocodeAddress(geocoder, resultsMap) {
	  if(i!=0)
  	  {
  		marker.setMap(null);
	  	cityCircle.setMap(null);
  	  }
  	  else
  		  i=1;
	  rad = document.getElementById('radius').value;
    var address = document.getElementById('address').value;
    geocoder.geocode({'address': address}, function(results, status) {
      if (status === 'OK') {
        resultsMap.setCenter(results[0].geometry.location);
        var lat=results[0].geometry.location.lat();
        var lon=results[0].geometry.location.lng();
        createGeofence(lat,lon,rad,resultsMap);
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
  }
  
  function geocodeLatLng(geocoder,lat,lng, map) {
	  var latlng = {lat: parseFloat(lat), lng: parseFloat(lng)};
	  map.setCenter(latlng);
      geocoder.geocode({'location': latlng}, function(results, status) {
        if (status === 'OK') {
          if (results[1]) {
            document.getElementById('address').value=results[1].formatted_address;
          } else {
            window.alert('No results found');
          }
        } else {
          window.alert('Geocoder failed due to: ' + status);
        }
      });
    }
  
  function createGeofence(lat,lng,rad,map)
  {
      var latlng = {lat: parseFloat(lat), lng: parseFloat(lng)};
      document.getElementById('latitude').value=lat;
      document.getElementById('longitude').value=lng;
	  marker = new google.maps.Marker({
          map: map,
          position: latlng
        });
        cityCircle = new google.maps.Circle({
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35,
            map: map,
            center: latlng ,
            radius: parseFloat(rad)
          });
        document.getElementById('submit').style.display='block';
  }

	function initGeofences(map)
{
		<c:if test="${not empty geoList}">
	  <c:forEach items="${geoList}" var="geo">
		var lat = ${geo.lat};
		var lng = ${geo.lng};
		var rad = ${geo.rad};
		var latlng = {lat: parseFloat(lat), lng: parseFloat(lng)};
		var content = '<h3>${geo.addr}</h3>'+
			'<form action="/Testing/DeleteGeofence" method="post">'+
				'<input id="latitude" name="lat" type="hidden" value="'+lat+'">'+
				'<input id="longitude" name="lng" type="hidden" value="'+lng+'">'+ 
				'<input type="submit" value="delete">'+
			'</form>';
		 var marker = new google.maps.Marker({
	          map: map,
	          position: latlng,
	          title: '${geo.addr}'
	        });
		 var infowindow = new google.maps.InfoWindow();
		 google.maps.event.addListener(marker,'mouseover', (function(marker,content,infowindow){ 
			    return function() {
			        infowindow.setContent(content);
			        infowindow.open(map,marker);
			    };
			})(marker,content,infowindow)); 
	        cityCircle = new google.maps.Circle({
	            strokeColor: '#FF0000',
	            strokeOpacity: 0.8,
	            strokeWeight: 2,
	            fillColor: '#FF0000',
	            fillOpacity: 0.35,
	            map: map,
	            center: latlng ,
	            radius: parseFloat(rad)
	          });
		</c:forEach>
		</c:if>
}
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDUq0uErNcoj-GjqduEneoHvbECh9BRpjc&callback=initMap">
    </script>
  </body>
</html>