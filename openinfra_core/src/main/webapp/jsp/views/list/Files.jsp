<%@page import="de.btu.openinfra.backend.db.daos.file.FilesProjectDao"%>
<%@page import="de.btu.openinfra.backend.rest.file.FilesProjectResource"%>
<%@page import="de.btu.openinfra.backend.rest.rbac.SubjectResource"%>
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
					<c:if test="${fn:contains(requestUrl, '/rest/v1/files')}">
						<th>Zugeordnete Projekte</th>
						<th>Aktion</th>
					</c:if>
				</tr>
			</thead>
			
			<!-- all projects the current user has access to -->
			<c:set var="projects" value=""/>
			<% pageContext.setAttribute("projects", new SubjectResource().projects("DE-de")); %>
						
			<c:forEach items="${it}" var="pojo">
			
				<c:set var="currentPojo" value="${pojo.uuid}"/>
			
				<!-- all projects the current file is related to -->
				<c:set var="filesProjects" value=""/>
				<% pageContext.setAttribute("filesProjects", new FilesProjectDao().read(null, 
						UUID.fromString(pageContext.getAttribute("currentPojo").toString()), 0, Integer.MAX_VALUE)); %>			
			
				<tr id="tr_${pojo.uuid}">
					<td><img src="${contextPath}/rest/v1/files/${pojo.uuid}/thumbnail"/></td>
					<td><a href="${contextPath}/rest/v1/files/${pojo.uuid}/origin">${pojo.originFileName}</a></td>
					<td>${pojo.mimeType}</td>
					<td><a href="${contextPath}/rest/v1/files/${pojo.uuid}/origin">${pojo.originDimension}</a></td>
					<td><a href="${contextPath}/rest/v1/files/${pojo.uuid}/popup">${pojo.popupDimension}</a></td>
					<td><a href="${contextPath}/rest/v1/files/${pojo.uuid}/middle">${pojo.middleDimension}</a></td>
					<td><a href="${contextPath}/rest/v1/files/${pojo.uuid}/thumbnail">${pojo.thumbnailDimension}</a></td>
					<td>${pojo.uploadedOn}</td>
					<td title="Metadaten in XML ansehen">
						<c:if test="${pojo.exifData != null}">
							<a href="${contextPath}/rest/v1/files/${pojo.uuid}.xml">vorhanden</a>
						</c:if>
					</td>
					<c:if test="${fn:contains(requestUrl, '/rest/v1/files')}">
						<td>
							<form action="" method="post">
						   		<c:forEach var="pp" items="${projects}">
						   		
						   			<c:set var="exists" value=""/>
						   			<c:forEach var="fp" items="${filesProjects}">
						   				<c:if test="${pp.uuid == fp.project}">
						   					<c:set var="exists" value="${fp.uuid}"/>
						   				</c:if>
						   			</c:forEach>
						   			
						   			<c:choose>
							   			<c:when test="${exists != ''}">
							   				<input type="checkbox" id="${exists}" name="${pp.uuid}" value="${pojo.uuid}" checked>
							   			</c:when>
							   			<c:otherwise>
							   				<input type="checkbox" name="${pp.uuid}" value="${pojo.uuid}">
							   			</c:otherwise>
						   			</c:choose>
						   			
						   			<c:set var="name" value=""/>
									<c:forEach items="${pp.names.localizedStrings}" var="item">
							    		<c:set var="name" value="${name} ${item.characterString}"/>
							    	</c:forEach>
							    	${name}
						   			<br>
						   		</c:forEach>
							</form>
						</td>
					<td>
						<c:set var="deleteButton" value="${pojo.uuid}" />
						<%@ include file="../../snippets/ButtonBar.jsp" %>
					</td>
						
					</c:if>
	    		</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
	
		$(document).ready(function () {
		  $('input:checkbox').change(function () {
			  if(this.checked) {
				  //alert($(this).attr('value'));
				  var id = addFileToProject($(this).attr('value'), $(this).attr('name'));
				  $(this).attr('id', id);
			  } else {
				  deleteFileFromProject($(this).attr('id'), $(this).attr('value'));
				  $(this).removeAttr('id');
			  }
		   });
		 });
		
		function deleteFileFromProject(id, currentFile) {
			var path = "${contextPath}/rest/v1/files/" + currentFile + "/filesproject/" + id;
			$.ajax({
				url: path,
				type: 'delete',
				contentType: "application/json; charset=utf-8",
				cache: false,
		 		error: function(xhr){
					  if(xhr.responseText.search('<body>') > -1) {
						  var source = xhr.responseText.split('<body>')[1];
					      $('#alert').html(source.split('</body>')[0]);
						  $('#alert').show();
				  	  } // end if
			  	}, // end error
				success: function(res) {
			          
				}});
		}
		
		function addFileToProject(currentFile, project) {
			var path = "${contextPath}/rest/v1/files/" + currentFile + "/filesproject";
			var returnId = "";
			var filesProject = new Object();
			filesProject.uuid = null;
			filesProject.file = currentFile;
			filesProject.project = project;
			
			$.ajax({
				url: path,
				type: 'post',
				data: JSON.stringify(filesProject),
				contentType: "application/json; charset=utf-8",
				cache: false,
		 		error: function(xhr){
					  if(xhr.responseText.search('<body>') > -1) {
						  var source = xhr.responseText.split('<body>')[1];
					      $('#alert').html(source.split('</body>')[0]);
						  $('#alert').show();
				  	  } // end if
			  	}, // end error
				success: function(res) {
			          returnId = res.uuid;
				}});
			return returnId;
		}
	
		function deleteItem(uuid) {
			globalUuid = uuid;
			// execute the delete request
 			OPENINFRA_HELPER.Dialogs.confirmDelete(
 					"${contextPath}/rest/v1/files/" + uuid);
		}
		
		// if the ajax request has finished
		$(document).ajaxStop(function () {
		    // try to decrement the badge
		    if(!(typeof(globalUuid)  === "undefined")) {
		    	OPENINFRA_HELPER.Misc.decrementBadge(globalUuid);
		    }
			// set the message box with the response
			OPENINFRA_HELPER.MessageBox.setResponse();
		});
		
	</script>

</body>
</html>