<%@page import="org.apache.xml.serializer.ToUnknownStream"%>
<%@page import="de.btu.openinfra.backend.db.daos.ValueListValueDao"%>
<%@page import="java.util.UUID"%>
<%@page import="de.btu.openinfra.backend.db.daos.ValueListDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
	<title>OpenInfRA <fmt:message key="valuelists.label"/></title>
</head>
<body>
	<!-- include the menu -->
	<%@ include file="../../snippets/Menu.jsp" %>
	
</body>
</html>