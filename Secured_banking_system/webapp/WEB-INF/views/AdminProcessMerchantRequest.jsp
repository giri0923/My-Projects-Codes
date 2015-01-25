<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.*"%>
<html>
<security:authorize access="hasRole('ROLE_ADMIN')">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Noble Banking</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<link href="${pageContext.servletContext.contextPath}/resources/css/styles.css" rel="stylesheet">
</head>
<body>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-toggle"></span>
				</button>
				<a class="navbar-brand" href="homePageAdmin.html">Home</a> 
				<a class="navbar-brand" href="critical.html">Critical Transactions</a>
				<a class="navbar-brand" href="verifyUserRequests.html">Verify User Requests</a>
				<a class="navbar-brand" href="adminverifymerchantrequest.html">Handle Payment Requests</a>
				<a class="navbar-brand" href="javascript:formSubmit()">Logout</a>
			</div>
			<div class="navbar-collapse collapse">
				
			</div>
		</div>
		<!-- /container -->
	</div>
	<!-- /Header -->

	<!-- Main -->
	<div class="container">

		<!-- upper section -->
		<div class="row">
			<div class="col-sm-3">
				<!-- left -->
				<h3>
					<i class="glyphicon glyphicon-briefcase"></i> Select
				</h3>
				<hr>

				<ul class="nav nav-stacked">
					<li><a href="addExternalUser"><i
							class="glyphicon glyphicon-book"></i> Add External User</a></li>
					<li><a href="deleteExternalUser">
					<i	class="glyphicon glyphicon-time"></i> Delete External User</a></li>
					<li><a href="addInternalUser"><i
							class="glyphicon glyphicon-book"></i> Add Internal User</a></li>
					<li><a href="editInternalUser"><i
							class="glyphicon glyphicon-briefcase"></i> Edit Internal User</a></li>
					<li><a href="deleteInternalUser"><i
							class="glyphicon glyphicon-time"></i> Delete Internal User</a></li>
				</ul>
				<hr>

			</div>
			<div class="col-sm-9">

				<h3>
					<i class="glyphicon glyphicon-dashboard"></i> Payments that needs approval (If transaction amount is greater than user's balance it can't be accepted)
				</h3>
				
				<hr>
				 <table class="table table-striped">
        		<thead>
        		  <tr><th>Transaction Type</th><th>Merchant id</th><th>Transaction Amount</th><th>Transaction Id</th></tr>
        		</thead>
      		    <tbody>
      		    <c:forEach items="${list}" var="userRequest">
          		 <tr><td>${userRequest.tranType}</td>
          		 <td>${userRequest.fromUid}</td>
          		 <td>${userRequest.amount}</td>
          		 <td>${userRequest.tranId}</td>
          		 <form:form method="post" action="adminperformpayment.html" class="form form-vertical">
          		 <td> 
          		 	<select id="acceptstatus" name="acceptstatus" class="form-control">
   					<option value="1">Accept</option>
   					<option value="2">Reject</option>
				</select>
				<form:hidden path="tranId" value="${userRequest.tranId}"/>
				<form:hidden path="fromUid" value="${userRequest.fromUid}"/>
				<form:hidden path="amount" value="${userRequest.amount}"/>
				<form:hidden path="status" value="${userRequest.status}"/>
				</td><td><input type="submit" value="Submit" class="btn btn-primary"/>
				</td></form:form>
          		</tr>
          		</c:forEach>
          		</tbody>
          		</table>
				<center>
			<br />
          <hr>
          </center>
				<div class="row"></div>
				<!--/row-->
			</div>
			<!--/col-span-9-->

		</div>
		<!--/row-->
		<!-- /upper section -->

		<!-- lower section -->
		<div class="row"></div>
		<!--/row-->

	</div>
	<!--/container-->
	<!-- /Main -->


	<!--<footer class="text-center">This Bootstrap 3 dashboard layout is compliments of <a href="http://www.bootply.com/85850"><strong>Bootply.com</strong></a></footer>-->
	<!-- script references -->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		
	
</body>
</security:authorize>
</html>