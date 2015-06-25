<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
	<title>OpenInfRA <fmt:message key="attributetypegroups.details.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="content">
		<h2><fmt:message key="attributetypegroups.details.label"/>:
	    	<c:forEach items="${it.names.localizedStrings}" var="item">
	    		<c:out value="'${item.characterString}'"> </c:out>
	    	</c:forEach>			 
		</h2>
		
		<%@ include file="../../snippets/Name.jsp" %>
		<%@ include file="../../snippets/Description.jsp" %>
		
		<ul>
			<li><a href="${it.uuid}/subgroups">Untergruppen</a></li>
		    <li><a href="${it.uuid}/attributetypes">Zugeordnete Attributetypen</a></li>
		</ul>
	</div>

</body>
</html>