<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
	
	<title>OpenInfRA <fmt:message key="multiplicities.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="content">
		<h2><fmt:message key="multiplicities.label"/>: </h2>
		<ul>
			<c:forEach items="${it}" var="pojo">    		
	    		<li>
	    			<!--<a href="multiplicities/${pojo.uuid}">-->
		    			max: ${pojo.max}; min: ${pojo.min}
		    			<!--<span class="uuid">(${pojo.uuid})</span>-->
	    			<!--</a>-->
	    		</li>
			</c:forEach>
		</ul>
	</div>

</body>
</html>