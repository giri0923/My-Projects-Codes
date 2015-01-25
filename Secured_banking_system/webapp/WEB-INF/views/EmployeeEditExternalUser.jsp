<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<security:authorize access="hasRole('ROLE_EMP')">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Noble Banking</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link
	href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<link
	href="${pageContext.servletContext.contextPath}/resources/css/styles.css"
	rel="stylesheet">
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
				<a class="navbar-brand" href="HomePageemp.html">Home</a> <a
					class="navbar-brand" href="EmployeeUserRequests.html">User
					Requests</a> <a class="navbar-brand" href="EmployeeHandleRequests.html">Handle
					Profile Requests</a> <a class="navbar-brand"
					href="EmployeeHandleTransactionRequests.html">Handle
					Transaction Requests</a> <a class="navbar-brand"
					href="javascript:formSubmit()">Logout</a>
			</div>
			<div class="navbar-collapse collapse"></div>
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
				
				<hr>

				

				<hr>

			</div>
			<!-- /span-3 -->
			<div class="col-sm-9">

				<h3>
					<i class="glyphicon glyphicon-dashboard"></i> Edit User
				</h3>
				
				<hr>
			
			<form:form method="post" action="editExternalUserSubmit.html" commandName="userBean" class="form form-vertical">
               <div class="control-group">
              <div class="controls">
                <form:hidden path="firstname" class="form-control" value="${uBean.firstname}" maxlength="20px"/>
              </div>
              <font color="red"><form:errors path="firstname"/></font>
            </div>      
            <div class="control-group">
              <div class="controls">
                <form:hidden path="lastname" class="form-control" value="${uBean.lastname}" maxlength="20px"/>
              </div>
              <font color="red"><form:errors path="lastname"/></font>
            </div>
             <div class="control-group">
              <label>Mobile</label>
              <div class="controls">
                <form:input path="mobile" value="${uBean.mobile}" class="form-control"/>
              </div>
              <font color="red"><form:errors path="mobile"/></font>
            </div>
             <form:hidden path="username" value="${uBean.username}" class="form-control"/>
             <div class="control-group">
              <div class="controls">
                <form:hidden path="email"  value="${uBean.email}" class="form-control"/>
              </div>
              <font color="red"><form:errors path="email"/></font>
            </div> 
             <div class="control-group">
              <label>Address</label>
              <div class="controls">
                <form:input path="address" value="${uBean.address}" class="form-control"/>
              </div>
              <font color="red"><form:errors path="address"/></font>
            </div>   
            <div class="control-group">
            <label> </label>
              <div class="controls">
                <input type="submit" value="Edit External User" class="btn btn-primary"/>
              </div>
            </div>   
          </form:form>
		
			<br />
          <hr>
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