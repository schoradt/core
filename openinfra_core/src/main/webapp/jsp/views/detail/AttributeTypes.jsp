<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title> OpenInfRA <fmt:message key="attributetypes.details.label"/></title>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="content">
		<h2><fmt:message key="attributetypes.details.label"/>:
	    	<c:forEach items="${it.names.localizedStrings}" var="item">
	    		<c:out value="'${item.characterString}'"> </c:out>
	    	</c:forEach>			 
		</h2>
	
	<%@ include file="../../snippets/Name.jsp" %>
	<%@ include file="../../snippets/Description.jsp" %>
	
	<ul>
		<li>Datentyp:</li>
		<c:set var="it" value="${it.dataType}"></c:set>
		<%@ include file="../../snippets/Name.jsp" %>
		<%@ include file="../../snippets/Description.jsp" %>
		
		<li><a href="../valuelists/${it.belongsToValueList}">Werteliste</a></li>
		<li><a href="${model.uuid}/attributetypegroups">Zugeordnete Attributtypgruppen</a></li>
	</ul>
	    	
	</div>
</body>
</html>