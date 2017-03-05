<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="formstyle.css">
<script type="text/javascript">
	function openForm() {
		document.getElementById("contact").style.display = 'block';
		document.getElementById("open").style.display = 'none';
	}
</script>
</head>
<body>
	<div class="container">
		<div id="contact1">
			<h3>Welcome ${sessionScope.uname}</h3>
			<h4>APP NAME : APP KEY</h4>
			<c:forEach var="app" items="${requestScope.appList}">
				<div id="contact2">${app.appName} : ${app.appKey}</div>
				<a href="/Testing/AddGeofence?appKey=${app.appKey}"><button
						id="geofences" value="${app.appKey}" onclick="viewGeofences()">Geofences</button></a>
				<a href="/Testing/DeleteApp?appKey=${app.appKey}"><button id="delete" value="${app.appKey}" onclick="deleteApp()">Delete</button></a>
			</c:forEach>

			<fieldset>
				<button id="open" onclick="openForm()">Create new app</button>
			</fieldset>

			<form id="contact" action="/Testing/AddApp" method="post"
				style="display: none;">
				<input name="appName" type="text" placeholder="application name"
					required><br>
				<fieldset>
					<button name="submit" type="submit" id="contact-submit">Create</button>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>