<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<security:authorize access="hasRole('ROLE_ADMIN')">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Noble Banking</title>
<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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
<center>
<br/><br/>
<div style="width: 300px">
 <div class="panel panel-default">
        <div class="panel-heading">
          <div class="panel-title">
            <i class="glyphicon glyphicon-wrench pull-right"></i>
            <h4>Login here</h4>
          </div>
        </div>
        <div class="panel-body">
        
         <form:form method="post" action="addUser.html" class="form form-vertical">
            <div class="control-group">
              <label>First Name</label>
              <div class="controls">
                <form:input path="firstname" class="form-control" />
              </div>
            </div>      
            <div class="control-group">
              <label>LastName</label>
              <div class="controls">
                <form:input path="lastname" class="form-control"/>
              </div>
            </div> 
             <div class="control-group">
              <label>User Id</label>
              <div class="controls">
                <form:input path="username" class="form-control"/>
              </div>
            </div>
             <div class="control-group">
              <label>Password</label>
              <div class="controls">
                <form:input path="password" class="form-control" />
              </div>
            </div>
             <div class="control-group">
              <label>Role</label>
              <div class="controls">
               <form:select path="role">
    			<form:option value="ROLE_USER" label="USER"/>
    			<form:option value="ROLE_MERCHANT" label="Merchant"/>
			  </form:select>
              </div>
            </div>
             <div class="control-group">
              <label>Mobile</label>
              <div class="controls">
                <form:input path="mobile" class="form-control"/>
              </div>
            </div>
             <div class="control-group">
              <label>email</label>
              <div class="controls">
                <form:input path="email" class="form-control"/>
              </div>
            </div>  
             <div class="control-group">
              <label>Address</label>
              <div class="controls">
                <form:input path="address" class="form-control"/>
              </div>
            </div>  
             <div class="control-group">
              <label>Security Question</label>
              <div class="controls">
                <form:input path="secquestion"  class="form-control"/>
              </div>
            </div> 
             <div class="control-group">
              <label>Security Question Answer</label>
              <div class="controls">
                <form:input path="secquesanswer" class="form-control" />
              </div>
            </div>   
            <div class="control-group">
            <label></label>
              <div class="controls">
                <input type="submit" value="Add" class="btn btn-primary"/>
              </div>
            </div>   
          </form:form>
</div><!--/panel content-->
      </div><!--/panel-->
</div>
<a href="register.html">Click to register</a>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>

</center>
</body>
</security:authorize>
</html>