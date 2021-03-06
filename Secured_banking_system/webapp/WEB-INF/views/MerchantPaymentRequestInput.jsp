<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<html>
<security:authorize access="hasRole('ROLE_MER')">
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
				<a class="navbar-brand" href="homePagemer.html">Home</a> <a
					class="navbar-brand" href="transfer.html">Transfers</a> <a
					class="navbar-brand" href="credit.html">Credit Money</a> <a
					class="navbar-brand" href="debit.html">Debit Money</a> <a
					class="navbar-brand" href="editprofile.html">Edit Profile</a> <a
					class="navbar-brand" href="edittransaction.html">Edit
					Transaction</a> <a class="navbar-brand"
					href="viewstatementsmerchant.html">View Statements</a> <a
					class="navbar-brand" href="initiatepayment.html">Request
					Payment</a> <a class="navbar-brand" href="viewstatus.html">View
					Status</a> <a class="navbar-brand" href="javascript:formSubmit()">Logout</a>
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

			</div>
			<!-- /span-3 -->
			<div class="col-sm-9">

				<h3>
					<i class="glyphicon glyphicon-dashboard"></i> Enter user id and amount to intiate payment request
				</h3>

				<hr>
				<center>
 			<form:form method="post" action="PaymentRequestSubmit.html" commandName="transferBean" class="form form-vertical" style="width:300px">
 			<div class="control-group">
              <label>Enter User Id</label>
              <div class="controls">
                <form:input path="toAccount" class="form-control" placeholder="user id to pay"/>
              </div>
              <font color="red"><form:errors path="toAccount"/></font>
            </div>
            <div class="control-group">
              <label>Enter amount to be requested from user</label>
              <div class="controls">
                <form:input path="balance" class="form-control" placeholder="balance"/>
              </div>
              <font color="red"><form:errors path="balance"/></font>
            </div>   
            <div class="control-group">
            <label></label>
              <div class="controls">
                <input type="submit" value="Request" class="btn btn-primary"/>
              </div>
            </div>   
          </form:form>
          <hr>
          <c:if test="${not empty message}">
          <b>${message}</b>
          </c:if>
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
		
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</security:authorize>
</html>