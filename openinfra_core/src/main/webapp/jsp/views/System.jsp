<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../snippets/Head.jsp" %>
	<title>OpenInfRA <fmt:message key="system.label"/></title>
</head>
<body>
	<%@ include file="../snippets/Menu.jsp" %>
	
	<h2><fmt:message key="system.label"/>:</h2>
	<div class="content">
	    <ul>
			<li><a href="system/countrycodes"><fmt:message key="countrycodes.label"/></a></li>
			<li><a href="system/charactercodes"><fmt:message key="charactercodes.label"/></a></li>
			<li><a href="system/languagecodes"><fmt:message key="languagecodes.label"/></a></li>
			<li><a href="system/attributetypegroups"><fmt:message key="attributetypegroups.label"/></a></li>
			<li><a href="system/attributetypes"><fmt:message key="attributetypes.label"/></a></li>
			<li><a href="system/ptlocales"><fmt:message key="languages.label"/></a></li>
			<li><a href="system/topiccharacteristics"><fmt:message key="topiccharacteristics.label"/></a></li>
			<li><a href="system/valuelists"><fmt:message key="valuelists.label"/></a></li>
	    </ul>
    </div>
</body>
</html>