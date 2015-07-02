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
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="panel panel-default">
		<div class="panel-heading">
			<fmt:message key="valuelists.label"/>
			<span id="badge" class="badge">
				<%=new ValueListDao(
			        pageContext.getAttribute("currentProject").toString() == "" ? null :
						UUID.fromString(pageContext.getAttribute("currentProject").toString()),
							OpenInfraSchemas.valueOf(pageContext.getAttribute("schema").toString().toUpperCase())).getCount()%>
			</span>
			<c:set var="createButton" value="valuelists/new" />
			<%@ include file="../../snippets/ButtonBar.jsp" %>
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
				<tr id="tr_${pojo.uuid}">    		
					<td>
			  			<a href="valuelists/${pojo.uuid}/valuelistvalues">
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
						        pageContext.getAttribute("currentProject").toString() == "" ? null :
									UUID.fromString(pageContext.getAttribute("currentProject").toString()),
										OpenInfraSchemas.valueOf(pageContext.getAttribute("schema").toString().toUpperCase())).getCount(
												UUID.fromString(pageContext.getAttribute("currentValueList").toString()))%>
					</td>
					<td>
			    		${pojo.uuid}
					</td>
					<td>
						<c:set var="detailButton" value="valuelists/${pojo.uuid}" />
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
 					"${contextPath}/rest/" + OPENINFRA_HELPER.Misc.getRootPath()
 					+ "/valuelists/" + uuid);
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