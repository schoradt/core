<%@page import="de.btu.openinfra.backend.db.rbac.rbac.SubjectRbac"%>
<%@page import="de.btu.openinfra.backend.db.daos.rbac.SubjectDao"%>
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

<c:if test="${fn:contains(requestUrl, '/rest/v1/files')}">
<h3><a href="./files/upload">Neue Datei(en) hochladen</a></h3>	
</c:if>
	
	<div class="panel panel-default">

		<table class="table table-hover">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>Name</th>
					<th>Typ</th>
					<th>Original</th>
					<th>Popup</th>
					<th>Mitte</th>
					<th>Vorschau</th>
					<th>Uploaddatum</th>
					<th>Metadata</th>
					<th>UUID</th>
					<c:if test="${fn:contains(requestUrl, '/rest/v1/files')}">
						<th>Zugeordnete Projekte</th>
					</c:if>
					<th>Aktion</th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr id="tr_${pojo.uuid}">    		
					<td><img src="./files/${pojo.uuid}/thumbnail"/></td>
					<td><a href="./files/${pojo.uuid}/origin">${pojo.originFileName}</a></td>
					<td>${pojo.mimeType}</td>
					<td><a href="./files/${pojo.uuid}/origin">${pojo.originDimension}</a></td>
					<td><a href="./files/${pojo.uuid}/popup">${pojo.popupDimension}</a></td>
					<td><a href="./files/${pojo.uuid}/middle">${pojo.middleDimension}</a></td>
					<td><a href="./files/${pojo.uuid}/thumbnail">${pojo.thumbnailDimension}</a></td>
					<td>${pojo.uploadedOn}</td>
					<td title="Metadaten in XML ansehen">
						<c:if test="${pojo.exifData != null}">
							<a href="./files/${pojo.uuid}.xml">vorhanden</a>
						</c:if>
					</td>
					<td>${pojo.uuid}</td>
					<c:if test="${fn:contains(requestUrl, '/rest/v1/files')}">
						<td>
					   		<c:set var="projects" value=""/>
							<%
								pageContext.setAttribute("projects", 
																new SubjectRbac().self().getProjects());
							%>
							${projects}
							<form action="" method="post">
							   		<c:forEach var="pp" items="${projects}">
							   			<input type="checkbox" name="project" value="${pp.uuid}">${pp.uuid}<br>
							   		</c:forEach>
								  <input type="submit" value="Submit">
							</form>
						</td>
					</c:if>
					<td>
						<c:set var="deleteButton" value="${pojo.uuid}" />
						<%@ include file="../../snippets/ButtonBar.jsp" %>
					</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
		
		function deleteItem(uuid) {
			globalUuid = uuid;
			// execute the delete request
 			OPENINFRA_HELPER.Dialogs.confirmDelete(
 					"${contextPath}/rest/v1/files/" + uuid);
		}
		
		// if the ajax request has finished
		$(document).ajaxStop(function () {
		    // try to decrement the badge
		    OPENINFRA_HELPER.Misc.decrementBadge(globalUuid);
			// set the message box with the response
			OPENINFRA_HELPER.MessageBox.setResponse();
		});
		
	</script>

</body>
</html>