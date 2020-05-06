<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="model.Appointment"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/main.js"></script>
<meta charset="ISO-8859-1">


</head>
<body>


	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Appointment Management</h1>

				<form id="formApp" name="formApp">
							
  					Appointment Id :
					<input id="aId" name="aId" type="text" class="form-control form-control-sm"> 
					
					<br> Patient Name :
					<input id="pname" name="pname" type="text" class="form-control form-control-sm">
					
					<br> Patient Id : 
					<input id="pId" name="pId" type="text" class="form-control form-control-sm"> 
					
					<br> Doctor Name : 
					<input id="dName" name="dName" type="text" class="form-control form-control-sm"> 
					
					<br> Doctor Mobile : 
					<input id="dMobile" name="dMobile" type="text" class="form-control form-control-sm"> 
					
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> <input type="hidden"id="hidItemIDSave" name="hidItemIDSave" value="">
					
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>

				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divItemsGrid">
					<%
						Appointment itemobj = new Appointment();
						out.print(itemobj.viewAppointments());
					%>
				</div>

			</div>
		</div>
	</div>


</body>
</html>