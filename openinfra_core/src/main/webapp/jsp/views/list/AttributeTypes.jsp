<%@page import="de.btu.openinfra.backend.db.daos.AttributeTypeDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>  
	<title>OpenInfRA <fmt:message key="attributetypes.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="panel panel-default">
		<div class="panel-heading">
			<fmt:message key="attributetypes.label"></fmt:message>
			<span class="badge">
				<%=new AttributeTypeDao(
								UUID.fromString(request.getAttribute("currentProject").toString()),
								OpenInfraSchemas.PROJECTS).getCount()%>
			</span>
		</div>
		
		<table class="table table-hover rowlink" data-link="row">
			<thead>
				<tr>
					<th>
						<fmt:message key="name.label"/>
					</th>
					<th>
						<fmt:message key="description.label"/>
					</th>
					<th>
						<fmt:message key="datatype.label"/>
					</th>
					<th>
						<fmt:message key="unit.label"/>
					</th>
					<th>
						<fmt:message key="domain.label"/>
					</th>
					<th>
						UUID
					</th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr>    		
					<td>
						<!-- ghost href for rowlink -->
			  			<a href="attributetypes/${pojo.uuid}"></a>
						<c:set var="localizedStrings" value="${pojo.names.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
			    		
					</td>
					<td>
						<c:set var="localizedStrings" value="${pojo.descriptions.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
						<c:set var="localizedStrings" value="${pojo.dataType.names.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
			    		<c:set var="localizedStrings" value="${pojo.unit.names.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
						<c:set var="localizedStrings" value="${pojo.domain.names.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
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