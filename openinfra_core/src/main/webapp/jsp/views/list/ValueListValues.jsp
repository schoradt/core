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
			<div>
				<fmt:message key="valuelistvalues.label"/>
				<span class="badge">
					<c:set var="currentValueList" value="${it[0].belongsToValueList}"/>
					<%=new ValueListValueDao(
							UUID.fromString(request.getAttribute("currentProject").toString()),
							OpenInfraSchemas.PROJECTS).getCount(
									UUID.fromString(pageContext.getAttribute("currentValueList").toString()))%>
				</span>
				<!-- add the id of the value list to the create button -->
				<c:set var="createButton" value="../../valuelistvalues/new?vl=${currentValueList}" />
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
						<fmt:message key="visibility.label"/>
					</th>
					<th>
						UUID
					</th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr id="tr_${pojo.uuid}">
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
					<td>
						<c:set var="detailButton" value="../../valuelistvalues/${pojo.uuid}" />
						<c:set var="deleteButton" value="${pojo.uuid}" />
						<%@ include file="../../snippets/ButtonBar.jsp" %>
					</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- include the area for the result messages -->
	<%@ include file="../../snippets/MessageBox.jsp" %>
	
	<script type="text/javascript">
		var globalUuid = "";
		
		function deleteItem(uuid) {
			globalUuid = uuid;
			// execute the delete request
 			OPENINFRA_HELPER.Ajax.execDeleteQuery(
 					"${contextPath}/rest/projects/${currentProject}/valuelistvalues/" 
 					+ uuid);
		}
		
		// if the ajax request has finished
		$(document).ajaxStop(function () {
			// if the request was successful
			if (OPENINFRA_HELPER.Ajax.result != null) {
				// remove the deleted item from the list
				$('#tr_' + globalUuid).remove();
				// decrement the badge count
				$("#badge").text($("#badge").text()-1);
			}
				
			// set the message box with the response
			OPENINFRA_HELPER.MessageBox.setResponse();
				
		});
	</script>
</body>
</html>