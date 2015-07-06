<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title>OpenInfRA <fmt:message key="valuelists.details.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>
	
	<!-- Check if an object was returned and set a respective variable. -->
	<c:choose>
		<c:when test="${it.uuid == null}">
			<c:set var="create" value="true" />
		</c:when>
		<c:otherwise>
			<c:set var="create" value="false" />
		</c:otherwise>
 	</c:choose>
	
	<div class="content">
		<h2>
			<!-- Create the header. -->
			<c:choose>
			    <c:when test="${!create}">
					<fmt:message key="valuelists.details.label"/>:
					<c:set var="localizedStrings" value="${it.names.localizedStrings}"/>
					<c:set var="name">
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</c:set>
					<c:out value="${name}"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="valuelists.create.label"/>:
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
		var basePath = "${contextPath}/rest/" + OPENINFRA_HELPER.Misc.getRootPath();
		var data = new Object();
	
		$("#save").click(function() {
			// save the uuid of the value list
			var uuid = $("#id").val();
			
			// build the data object with the information from the input fields
			data["names"] = $("#name").val();
			data["descriptions"] = $("#description").val();
			
			// build the URI to POST the data
			var setUri = basePath + "/valuelists/" + uuid;
			var getUri = setUri;
			var localeId = "<%= pageContext.getAttribute("languageId") %>";
			// call the putOrPost method and persist the data
			OPENINFRA_HELPER.Ajax.execPutOrPostQuery("PUT", getUri, setUri, data, localeId);
		}); // end click function
 		
 		$("#create").click(function() {
 			
 			// build the data object with the information from the input fields
			data["names"] = $("#name").val();
			data["descriptions"] = $("#description").val();
			
			// build the URI to POST the data
			var setUri = basePath + "/valuelists";
			
			// build the URI to retrieve the hull object
			var getUri = setUri + "/new";
			
			// call the putOrPost method and persist the data
			OPENINFRA_HELPER.Ajax.execPutOrPostQuery("POST", getUri, setUri, data);
			
			// TODO return to the overview page?
			// TODO change to edit mode?
		}); // end click function
		
		$("#edit").click(function() {
			$('#name, #description').prop('readonly', false);
			$(".view").hide();
			$(".input").show();
		});
		
		$("#cancel").click(function() {
			$('#name, #description').prop('readonly', true);
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