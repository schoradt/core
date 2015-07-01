<%@page import="de.btu.openinfra.backend.db.daos.ValueListDao"%>
<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title>OpenInfRA <fmt:message key="valuelistvalues.details.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>
	
	<!--
		Force an exception to test if "it" is an object or not. This will
		determine if the creation mode is called.
	 -->
	<c:catch var="createException">
		<c:if test="${it.uuid != null}"></c:if>
	</c:catch>
	
	<!-- If an exception occurred set the create variable to true. -->
	<c:choose>
		<c:when test="${createException == null}">
			<c:set var="create" value="false" />
		</c:when>
		<c:otherwise>
			<c:set var="create" value="true" />
		</c:otherwise>
	</c:choose>
	
	<div class="content">
		<h2>
			<!-- Create the header. -->
			<c:choose>
			    <c:when test="${!create}">
					<fmt:message key="valuelistvalues.create.label"/>:
					<c:set var="localizedStrings" value="${it.names.localizedStrings}"/>
					<c:set var="name">
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</c:set>
					<c:out value="${name}"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="valuelistvalues.create.label"/>:
				</c:otherwise>
			</c:choose>
		</h2>
		
		<form enctype='application/json'>
			<!-- Create the id field. This is only necessary in the edit mode. -->
			<div class="form-group">
				<c:if test="${!create}">
					<label for="id"><fmt:message key="id.label"/>:</label>
					<input type="text" class="form-control" id="id" value="${it.uuid}" readonly/>
				</c:if>
			</div>

			<!-- Create the name field. -->
			<div class="form-group">
				<label for="name"><fmt:message key="name.label"/>:</label>
				<c:choose>
					<c:when test="${!create}">
						<input type="text" class="form-control" id="name" value="${name}" readonly/>
						<input type="hidden" id="language" value="${it.names.uuid}" readonly/>
					</c:when>
					<c:otherwise>
						<input type="text" class="form-control" id="name" value="" />
					</c:otherwise>
		    	</c:choose>
			</div>
			
			<!-- Create the description field. -->
			<div class="form-group">
			    <label for="description"><fmt:message key="description.label"/>:</label>
			    <c:choose>
				    <c:when test="${!create}">
					    <c:set var="localizedStrings" value="${it.descriptions.localizedStrings}"/>
						  <c:set var="description">
						  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
						  </c:set>
				    	<textarea class="form-control" rows="4" id="description" readonly>${description}</textarea>
				    	<input type="hidden" id="description" value="${it.descriptions.uuid}" readonly/>
			    	</c:when>
			    	<c:otherwise>
			    		<textarea class="form-control" rows="4" id="description"></textarea>
			    	</c:otherwise>
		    	</c:choose>
			</div>
			
			<!-- Create the visibility check box. -->
			<div class="form-group">
				<label for="visibility"><fmt:message key="visibility.label"/></label>
				<c:choose>
					<c:when test="${!create}">
						<c:set var="checked" value=""></c:set>
						<c:if test="${it.visibility}">
							<c:set var="checked" value="checked"></c:set>
						</c:if>
						<input type="checkbox" id="visibility" ${checked} disabled/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" id="visibility" />
					</c:otherwise>
		    	</c:choose>
			</div>
			
			<!-- Create the association field to value lists. -->
			<div class="form-group">
				<label for="belongsToValueList"><fmt:message key="association.valuelist.label"/>:</label>
				<!-- get a list of all available value lists -->
				<%
					pageContext.setAttribute("valueLists", new ValueListDao(
						UUID.fromString(request.getAttribute("currentProject").toString()),
						OpenInfraSchemas.PROJECTS).read(
					        PtLocaleDao.forLanguageTag(session.getAttribute("language").toString()),
							0, 
							Integer.MAX_VALUE));
				%>
				<!-- decide which entry is selected -->
				<c:choose>
					<c:when test="${!create}">
						<c:set var="selection" value="${it.belongsToValueList}" />
						<c:set var="disabled" value="disabled" />
					</c:when>
					<c:otherwise>
						<c:set var="selection" value="${param.vl}" />
						<c:set var="disabled" value="" />
					</c:otherwise>
		    	</c:choose>
				
				<select ${disabled} class="form-control" id="belongsToValueList">
					<c:forEach items="${valueLists}" var="valueList">
						<c:choose>
							<c:when test="${valueList.uuid == selection}">
								<option selected value="${valueList.uuid}">${valueList.names.localizedStrings[0].characterString}</option>
							</c:when>
							<c:otherwise>
								<option value="${valueList.uuid}">${valueList.names.localizedStrings[0].characterString}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				
			</div>
			
			<!-- Add control buttons. -->
			<c:set var="edit" value="${!create}" />
			<%@ include file="../../snippets/ControlButtons.jsp" %>
			
		</form>
		
		<hr/>
	</div>
	
	<!-- Add message container. -->
	<%@ include file="../../snippets/MessageBox.jsp" %>
	
	<script type="text/javascript">
	
		// create the base path of the application
		// TODO this path should react dynamicly to the URI (project or system)
		var basePath = "${contextPath}/rest/projects/${currentProject}";
		var data = new Object();
	
		$("#save").click(function() {
			// save the uuid of the value list
			var uuid = $("#id").val();
			
			// build the data object with the information from the input fields
			data["names"] = $("#name").val();
			data["descriptions"] = $("#description").val();
			data["visibility"] = $("#visibility").prop("checked");
			data["belongsToValueList"] = $("#belongsToValueList option:selected").val();
			
			
			
			// build the URI to POST the data
			var setUri = basePath + "/valuelistvalues/" + uuid;
			var getUri = setUri;
			var localeId = "<%= pageContext.getAttribute("languageId") %>";
			// call the putOrPost method and persist the data
			OPENINFRA_HELPER.Ajax.execPutOrPostQuery("PUT", getUri, setUri, data, localeId);
		}); // end click function
 		
 		$("#create").click(function() {
 			
 			// build the data object with the information from the input fields
			data["names"] = $("#name").val();
			data["descriptions"] = $("#description").val();
			data["visibility"] = $("#visibility").prop("checked");
			data["belongsToValueList"] = $("#belongsToValueList option:selected").val();
			
			// build the URI to POST the data
			var setUri = basePath + "/valuelists/" + $("#belongsToValueList").val() + "/valuelistvalues";
			
			// build the URI to retrieve the hull object
			var getUri = basePath + "/valuelists/hull";
			
			// call the putOrPost method and persist the data
			OPENINFRA_HELPER.Ajax.execPutOrPostQuery("POST", getUri, setUri, data);
			
			// TODO return to the overview page?
			// TODO change to edit mode?
		}); // end click function
		
		$("#edit").click(function() {
			$('#name, #description').prop('readonly', false);
			$('#visibility, #belongsToValueList').prop('disabled', false);
			$(".view").hide();
			$(".input").show();
		});
		
		$("#cancel").click(function() {
			$('#name, #description').prop('readonly', true);
			$('#visibility, #belongsToValueList').prop('disabled', true);
			$(".view").show();
			$(".input").hide();
		});
		
		// if the ajax request has finished
		$(document).ajaxStop(function () {
			// set the message box with the response
			OPENINFRA_HELPER.MessageBox.setResponse();
			
			// check if we are in the edit mode
			if ($("id") != null) {
				// TODO set the new values
				// update the value for the name in the heading
				// update the value for the name
				// update the value for the description
				// update the value for the name in the bread crumb
			}
			
		});
		
	</script>
</body>
</html>