<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript">
function formValidator()
{
	var pwd = document.getElementById('pwd');
	
	if(isNumeric(pwd, "Invalid passwd(only digits/chars of length between 4 to 8)"))
          {
		  return true;
			}
		else
			return false;
}
function isNumeric(elem, helperMsg)
{
	var numericExpression = /^[0-9a-zA-Z]{4,8}$/;
	if(elem.value.match(numericExpression))
       {
		return true;
	}
        else
        {
		alert(helperMsg);
		elem.focus();
		return false;
	}
}
</script>
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
            <h4>Change Password</h4>
          </div>
        </div>
        
        <div class="panel-body">
		<%--   <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> --%>
		<form:form  commandName="changePassword" action="changePassword.html" method='POST' onSubmit="return formValidator()">
            <div class="control-group">
              <label>New Password</label>
              <div class="controls">
              	<form:input path="password" class="form-control" id="pwd" maxlength="20px"/>
              	<form:hidden path="userId" class="form-control" value="${id}"  maxlength="20px"/>
              </div>
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