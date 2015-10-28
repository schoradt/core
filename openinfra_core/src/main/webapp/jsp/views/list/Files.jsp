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

<h3><a href="./files/upload">Neue Datei(en) hochladen</a></h3>	
	
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
					<th>UUID</th>
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
					<td>${pojo.uuid}</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>