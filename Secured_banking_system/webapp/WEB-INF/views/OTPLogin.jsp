<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Noble Banking</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.plugin.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.keypad.js"></script>
<script>
$(function () {	 
$('#keyPad').keypad({target: $('.inlineTarget:first'), 
    layout: $.keypad.qwertyLayout}); 
	//$('#inlineKeypad').keypad({onClose: function() {
		//alert($(this).val());	
	//}});
});
</script>
<script>
var keypadTarget = null; 
$('.inlineTarget').focus(function() { 
    if (keypadTarget != this) { 
        keypadTarget = this; 
        $('#keyPad').keypad('option', {target: this}); 
    } 
});
</script>

</head>
<body>

		<form:form  commandName="otp" action="OTPLogin.html" method='POST'>
            <div class="control-group">
              <label>User Id</label>
              <div class="controls">
              	<form:input path="userId"  maxlength="20px"/>
              </div>
              <font color="red"><form:errors path="userId"/></font>
            </div> 
             <div class="control-group">
              <label>OTP</label>
              <div class="controls">
              	<form:input path="password" id="keyPad"  maxlength="20px"/><br/>
              </div>
              <font color="red"><form:errors path="password"/></font>
            </div> 
            <div class="control-group">
              <div class="controls">
                <input type="submit" value="Submit" class="btn btn-primary"/>
              </div>
            </div> 
          </form:form>

</body>
</html>