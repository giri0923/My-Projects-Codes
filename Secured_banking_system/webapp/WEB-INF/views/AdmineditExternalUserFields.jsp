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
<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
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
					<li><a href="editExternalUser"><i
							class="glyphicon glyphicon-briefcase"></i> Modify External User(Only autherized profiles can be modified)</a></li>							
					<li><a href="deleteExternalUser"><i
							class="glyphicon glyphicon-time"></i> Delete External User</a></li>
					<li><a href="addInternalUser"><i
							class="glyphicon glyphicon-book"></i> Add Internal User</a></li>
					<li><a href="editInternalUser"><i
							class="glyphicon glyphicon-briefcase"></i> Edit Internal User</a></li>
					<li><a href="deleteInternalUser"><i
							class="glyphicon glyphicon-time"></i> Delete Internal User</a></li>
				</ul>

				<hr>

			</div>			<!-- /span-3 -->
			<div class="col-sm-9">

				<h3>
					<i class="glyphicon glyphicon-dashboard"></i> Edit External User
				</h3>
				
				<hr>
				<c:if test="${not empty message}">
        			<b>${message}</b>
        			<hr>
          		</c:if>
          		<c:if test="${empty message}">
			<form:form method="post" action="editExternalUserF.html"  commandName="userBean" class="form form-vertical">
            <div class="control-group">
              <label>First Name</label>
              <div class="controls">
                <form:input path="firstname" class="form-control" value="${uBean.firstname}" maxlength="20px"/>
              </div>
              <font color="red"><form:errors path="firstname"/></font>
            </div>      
            <div class="control-group">
              <label>Last Name</label>
              <div class="controls">
                <form:input path="lastname" class="form-control" value="${uBean.lastname}" maxlength="20px"/>
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
             <div class="control-group">
              <label>email</label>
              <div class="controls">
                <form:input path="email"  value="${uBean.email}" class="form-control"/>
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
            <form:hidden path="empid" value="${uBean.empid}"/>
            <div class="control-group">
            <label> </label>
              <div class="controls">
                <input type="submit" value="Edit User" class="btn btn-primary"/>
              </div>
            </div>   
          </form:form>
		  </c:if>	
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