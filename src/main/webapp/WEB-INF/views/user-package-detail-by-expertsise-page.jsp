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
				<h1>Find Specialists by Specialization</h1>
				<div class="container">
					<form:form action="specialistsByExpertsise" method="GET"
						modelAttribute="FormInputsGetByExpertsise">
						<div class="form-group">
							<form:label path="expertsise">Select Ailment:</form:label>
							<form:select path="expertsise" class="form-control"
								id="expertsise" items="${expertsise}" required="required" />
						</div>
						<form:button class="btn">Search</form:button>
					</form:form>
				</div>
				<div class="container result-container center border">
					<h3>Search Result</h3>
					<c:choose>
						<c:when test="${not empty error}">
							<div class="error">${error}</div>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${not empty specialists}">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Area of Expertise</th>
												<th>experience (year)</th>
												<th>Contact Number</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${specialists}" var="specialist"
												varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<td>${specialist.getName()}</td>
													<td>${specialist.getAreaOfExpertise()}</td>
													<td>${specialist.getExperienceInYears()}</td>
													<td>${specialist.getContactNumber()}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
</body>
</html>