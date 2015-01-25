<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="java.util.*"%>
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
					href="viewstatements.html">View Statements</a> <a
					class="navbar-brand" href="initiatepayment.html">Request
					Payment</a> <a class="navbar-brand" href="viewstatus.html">View
					Status</a> <a class="navbar-brand" href="javascript:formSubmit()">Logout</a>
			</div>
		</div>
			<!-- /span-3 -->
			<div class="col-sm-9">

			
				<div class="row"></div>
				<!--/row-->
			</div>
			<!--/col-span-9-->
	<h3>
					
		</div>
		<!--/row-->
		<!-- /upper section -->

		<!-- lower section -->
		<div class="row"></div>
		<!--/row-->
<i class="glyphicon glyphicon-dashboard"></i> View status of payment requests and Forward payments to bank
				</h3>
				
				<hr>
				 <table class="table table-striped">
        		<thead>
        		  <tr><th>Transaction Type</th><th>From User</th><th>Transaction Amount</th><th>Your Balance</th><th>Status(Accept/Reject)</th><th>Forward payment</th></tr>
        		</thead>
      		    <tbody>
      		    <c:forEach items="${list}" var="criticalTransaction">
          		 <tr><td>${criticalTransaction.tranType}${criticalTransaction.status}</td>
          		 <td>${criticalTransaction.fromUid}</td>
          		 <td>${criticalTransaction.amount}</td>
          		 <td>${criticalTransaction.balance}</td>
          		 <c:if test="${criticalTransaction.status=='0'}">
          		 <td>Payment Request placed</td>
          		 </c:if>
          		 <c:if test="${criticalTransaction.status=='8'}">
          		 <td>Needs bank approval</td>
          		 </c:if>
          		  <c:if test="${criticalTransaction.status=='1'}">
          		 <td>Payment approved by bank</td>
          		 </c:if>
          		  <c:if test="${criticalTransaction.status=='9'}">
          		 <td>Payment rejected by bank</td>
          		 </c:if>
          		 <c:if test="${criticalTransaction.status=='2'}">
          		 <td>Approved by user</td>
          		 <form:form method="post" action="merchantforwardtobank.html" class="form form-vertical">
          		 <td> 
				<form:hidden path="tranId" value="${criticalTransaction.tranId}"/>
				<form:hidden path="fromUid" value="${criticalTransaction.fromUid}"/>
				<form:hidden path="amount" value="${criticalTransaction.amount}"/>
				<form:hidden path="status" value="${criticalTransaction.status}"/>
				<input type="submit" value="Forward to Admin" class="btn btn-primary"/>
				</td></form:form>
          		 </c:if>
          		 <c:if test="${criticalTransaction.status=='3'}">
          		 <td>Rejected by user</td>
          		 </c:if>
          		 </tr>
          		</c:forEach>
          		</tbody>
          		</table>
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