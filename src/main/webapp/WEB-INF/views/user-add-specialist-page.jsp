<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>International Patients Management System</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Pinyon+Script&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/style-table.css">
<link rel="stylesheet" href="/css/style-admin.css">
</head>
<body>
	<div class="main-container-register">
		<%@ include file="fragments/header.jsp"%>
		<div class="section grid">
			<%@ include file="admin-fragments/admin-sidebar.jsp"%>
			<div class="content list-container">
				<h1>Add New Specialist</h1>
				<div class="container">
					<form:form action="/portal/addSpecialist" method="post"
						modelAttribute="SpecialistDetail">
						<div class="form-group" style="display:none">
							<form:label path="specialistId">Specialist Id:</form:label>
							<form:input path="specialistId" class="form-control" id="specialistid"
								type="number" required="required" min="1"
								data-error="Please enter a valid Specialist Id." />
						</div>
						<div class="form-group">
							<form:label path="name">Specialist Name:</form:label>
							<form:input path="name" class="form-control" id="name"
								type="text" pattern="^[a-zA-z ]*$" required="required" />
						</div>
						<div class="form-group">
							<form:label path="areaOfExpertise">Select Expertise:</form:label>
							<form:select path="areaOfExpertise" class="form-control" id="expertise"
								items="${expertsise}" required="required" />
						</div>
						<div class="form-group">
							<form:label path="experienceInYears">Add Experience in Years:</form:label>
							<form:input path="experienceInYears" class="form-control"
								type="number" id="experience" min="0" max="50" 
								required="required" />
						</div>
						<div class="form-group">
							<form:label path="contactNumber">Contact Number:</form:label>
							<form:input path="contactNumber" class="form-control"
								type="text" id="contact" required="required"
								pattern="[0-9]{10}"/>
						</div>
						<input type="submit" class="btn btn-reset" value="Register"/>
						<input type="reset" class="btn btn-reset" value="Reset"/>
					</form:form>
				</div>
			</div>
		</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/js/script.js"></script>
</body>
</html>