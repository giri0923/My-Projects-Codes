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
	var userId = document.getElementById('userId');
	var pwd = document.getElementById('pwd');
	
	if(isNumeric(userId, "Invalid userId (only digits) length should be 1 to 6"))
          {
		if(isNumeric2(pwd, "Invalid passwd(only digits/chars of length between 4 to 8)"))
        	{
			return true;
			}
		else return false;
          }
	else return false;	
}
function isNumeric(elem, helperMsg)
{
	var numericExpression = /^[0-9]{1,6}$/;
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

function isNumeric2(elem, helperMsg)
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
            <h4>Login here</h4>
          </div>
        </div>
        <c:if test="${not empty error}">
			<div class="error"><font color="red">${error}</font></div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
        <div class="panel-body">
		<%--   <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> --%>
		<form name='loginForm'
		  action="<c:url value='/j_spring_security_check'/>" method='POST' onSubmit="return formValidator()">
            <div class="control-group">
              <label>User Id</label>
              <div class="controls">
              	<input type='text' name='username' id='userId'>
              </div>
            </div>      
            <div class="control-group">
              <label>Password</label>
              <div class="controls">
                <input type='password' name='password' id='pwd'/>
              </div>
            </div>   
             <div class="controls">
               <a href="forgot.html" >Forgot Password/Change Password(For External User only)</a>
               <br/>
               <a href="OTPLogin.html" >Login with OTP(For External User only)</a>
              </div>
            <div class="control-group">
              <div class="controls">
                <input name="submit" type="submit"  value="submit" />
              </div>
            </div>   
          </form>
        </div><!--/panel content-->
      </div><!--/panel-->
</div>

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		
	<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>

</center>
</body>
</html>