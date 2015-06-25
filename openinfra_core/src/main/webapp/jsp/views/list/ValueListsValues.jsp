<%@page import="de.btu.openinfra.backend.db.daos.ValueListValueDao"%>
<%@page import="java.util.UUID"%>
<%@page import="de.btu.openinfra.backend.db.daos.ValueListDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
	<title>OpenInfRA <fmt:message key="valuelists.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="panel panel-default">
		<div class="panel-heading">
			<fmt:message key="valuelistvalues.label"/>
			<span class="badge">
				<c:set var="currentValueList" value="${it[0].belongsToValueList}"/>
				<%=new ValueListValueDao(
						UUID.fromString(request.getAttribute("currentProject").toString()),
						OpenInfraSchemas.PROJECTS).getCount(
								UUID.fromString(pageContext.getAttribute("currentValueList").toString()))%>
			</span>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>
						<fmt:message key="name.label"/>
					</th>
					<th>
						<fmt:message key="description.label"/>
					</th>
					<th>
						<fmt:message key="visibility.label"/>
					</th>
					<th>
						UUID
					</th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr>    		
					<td>
						<c:set var="localizedStrings" value="${pojo.names.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
						<c:set var="localizedStrings" value="${pojo.descriptions.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
						${pojo.visibility}
					</td>
					<td>
			    		${pojo.uuid}
					</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>
	

</body>
</html>