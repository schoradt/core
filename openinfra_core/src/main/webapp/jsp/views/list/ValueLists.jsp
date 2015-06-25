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
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div>
				<fmt:message key="valuelists.label"/>
				<span class="badge">
					<%=new ValueListDao(
									UUID.fromString(request.getAttribute("currentProject").toString()),
									OpenInfraSchemas.PROJECTS).getCount()%>
				</span>
				<c:set var="createButton" value="valuelists/new" />
				<%@ include file="../../snippets/ButtonBar.jsp" %>
			</div>
		</div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>
						<fmt:message key="name.label"/>
					</th>
					<th>
						<fmt:message key="description.label"/>
					</th>
					<th>
						<fmt:message key="count.label"/>
					</th>
					<th>
						UUID
					</th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr>    		
					<td>
			  			<c:choose>
			  				<c:when test="${fn:contains(url, '/rest/system/valuelists/${pojo.uuid}')}">
			  					<c:set var="link">../valuelists/${pojo.uuid}</c:set>
			  				</c:when>
			  				<c:otherwise>
			  					<c:set var="link">valuelists/${pojo.uuid}/valuelistvalues</c:set>
			  				</c:otherwise>
			  			</c:choose>
			  			<a href="${link}">
							<c:set var="localizedStrings" value="${pojo.names.localizedStrings}"/>
							<%@ include file="../../snippets/LocalizedStrings.jsp" %>
			    		</a>
					</td>
					<td>
						<c:set var="localizedStrings" value="${pojo.descriptions.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
						<c:set var="currentValueList" value="${pojo.uuid}"></c:set>
						<%=new ValueListValueDao(
										UUID.fromString(request.getAttribute("currentProject").toString()),
										OpenInfraSchemas.PROJECTS).getCount(
												UUID.fromString(pageContext.getAttribute("currentValueList").toString()))%>
					</td>
					<td>
			    		${pojo.uuid}
					</td>
					<td>
						<c:set var="detailButton" value="valuelists/${pojo.uuid}" />
						<%@ include file="../../snippets/ButtonBar.jsp" %>
					</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>
	

</body>
</html>