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
				<h1>Update Service Package</h1>
				<div class="container">
					<form:form action="/portal/updatePackage" method="post"
						modelAttribute="IpTreatmentPackage">
						<div class="form-group" style="display:none">
							<form:label path="treatmentPackageId">IP Treatment Package Id:</form:label>
							<form:input path="treatmentPackageId" class="form-control" id="treatmentpackageid"
								type="number" required="required" min="1"
								data-error="Please enter a valid Treatment package Id." />
						</div>
						<div class="form-group">
							<form:label path="ailmentCategory">Ailment Category:</form:label>
							<form:input path="ailmentCategory" class="form-control" id="name"
								type="text" required="required" />
						</div>
						<div class="form-group" style="display:none">
							<form:label path="packageDetail.pid">Package Id:</form:label>
							<form:input path="packageDetail.pid" class="form-control" id="packageid"
								type="number" required="required" />
						</div>
						<div class="form-group">
							<form:label path="packageDetail.treatmentPackageName">Package Name:</form:label>
							<form:input path="packageDetail.treatmentPackageName" class="form-control" id="treatmentPackageName"
								type="text" required="required" />
						</div>
						
						<div class="form-group">
							<form:label path="packageDetail.testDetails">Test Details:</form:label>
							<form:input path="packageDetail.testDetails" class="form-control" id="testDetails"
								type="text" required="required" />
						</div>
						
						<div class="form-group">
							<form:label path="packageDetail.treatmentDuration">Test Duration:</form:label>
							<form:input path="packageDetail.treatmentDuration" class="form-control" id="testduration"
								type="number" min="0" required="required" />
						</div>
						<div class="form-group">
							<form:label path="packageDetail.cost">Test Cost:</form:label>
							<form:input path="packageDetail.cost" class="form-control" id="cost"
								type="number" min="500" required="required" />
						</div>
						
						<input type="submit" class="btn btn-reset" value="Update"/>
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