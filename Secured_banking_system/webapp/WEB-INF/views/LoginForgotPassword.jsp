<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Noble Banking</title>
<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.servletContext.contextPath}/resources/css/styles.css" rel="stylesheet">
</head>
<body>
<center>
<br/><br/>
<div style="width: 300px">
 <div class="panel panel-default">
        <div class="panel-heading">
          <div class="panel-title">
            <i class="glyphicon glyphicon-wrench pull-right"></i>
            <h4>Enter User Id here you will OTP to your mail</h4>
          </div>
        </div>
        
        <div class="panel-body">
		<%--   <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> --%>
		<form:form  commandName="iDBean" action="forgot.html" method='POST'>
            <div class="control-group">
              <label>User Id</label>
              <div class="controls">
              	<form:input path="empid" class="form-control" maxlength="20px"/>
              </div>
              <font color="red"><form:errors path="empid"/></font>
            </div> 
            <div class="control-group">
              <div class="controls">
                <input type="submit" value="Submit" class="btn btn-primary"/>
              </div>
            </div> 
             
          </form:form>
          
        </div><!--/panel content-->
      </div><!--/panel-->
</div>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>

</center>
</body>
</html>