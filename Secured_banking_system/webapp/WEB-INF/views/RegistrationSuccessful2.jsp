<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<security:authorize access="hasRole('ROLE_ADMIN')">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Go back</title>
</head>
<body>
<a href="homePageAdmin.html">Click here to Home</a><br/>
<c:if test="${not empty message}">
        			<b>${message}</b>
        			<hr>
          		</c:if>
</body>
</security:authorize>
</html>