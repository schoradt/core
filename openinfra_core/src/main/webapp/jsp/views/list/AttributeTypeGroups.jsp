<%@page import="de.btu.openinfra.backend.db.daos.AttributeTypeDao"%>
<%@page import="de.btu.openinfra.backend.db.daos.AttributeTypeGroupDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>   
	<title>OpenInfRA <fmt:message key="attributetypegroups.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<fmt:message key="attributetypegroups.label"/>
			<span class="badge">
				<%=new AttributeTypeGroupDao(
								UUID.fromString(pageContext.getAttribute("currentProject").toString()),
								OpenInfraSchemas.PROJECTS).getCount()%>
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
						<fmt:message key="count.label"/>
					</th>
					<th>
						UUID
					</th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr> 
					<td>
		    			<a href="attributetypegroups/${pojo.uuid}/attributetypes">
							<c:set var="localizedStrings" value="${pojo.names.localizedStrings}"/>
							<%@ include file="../../snippets/LocalizedStrings.jsp" %>
		    			</a>
					</td>
					<td>
						<c:set var="localizedStrings" value="${pojo.descriptions.localizedStrings}"/>
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</td>
					<td>
						<c:set var="currentAttributeType" value="${pojo.uuid}"/>
						<%=new AttributeTypeDao(
								UUID.fromString(pageContext.getAttribute("currentProject").toString()),
								OpenInfraSchemas.PROJECTS).getCount(
										UUID.fromString(pageContext.getAttribute("currentAttributeType").toString()))%>
					</td>
					<td>
			    		<a href="attributetypegroups/${pojo.uuid}/attributetype">
			    			${pojo.uuid}
		    			</a>
					</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>