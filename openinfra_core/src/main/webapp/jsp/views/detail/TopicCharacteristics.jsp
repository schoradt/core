<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title> OpenInfRA <fmt:message key="topiccharacteristics.details.label"/></title>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

<form enctype='application/json'>
		
	<div class="content">
		<h2><fmt:message key="topiccharacteristics.details.label"/>:
			  <c:set var="localizedStrings" value="${it.descriptions.localizedStrings}"/>
			  <c:set var="topicDescription">
			  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
			  </c:set>
	    	<c:out value="${topicDescription}"/>
		</h2>
		
		<form enctype='application/json'>
		  <div class="form-group">
		    <label for="topicId">ID:</label>
		    <input type="text" class="form-control" id="projectid" value="${it.uuid}" readonly/>
		  </div>

		  <div class="form-group">
		    <label for="topicDescription"><fmt:message key="description.label"/>:</label>
		    <textarea class="form-control" rows="4" id="topicDescription" readonly>${topicDescription}</textarea>
		    <input type="hidden" id="topicdescriptionLang" value="${it.descriptions.uuid}" readonly/>
		  </div>
		  
		  <!-- Messages -->
		  <div style="display: none;" id="result"></div>
		  <div class="alert alert-success" style="display: none;" role="alert" id="success"></div>
		  <div class="alert alert-danger" style="display: none;" role="alert" id="alert"></div>
		  <a class="btn btn-default view" role="button" id="edit">
		  	<fmt:message key="edit.button.label"/>
		  </a>
		  <button class="btn btn-default input" style="display: none;" type="reset" id="cancel">
		  	<fmt:message key="cancel.button.label"/>
		  </button>
		  <a class="btn btn-default input" style="display: none;" id="save" role="button">
		  	<fmt:message key="save.button.label"/>
		  </a>
		</form>
	    
	    <ul>
	    	<li><a href="${it.uuid}/topicinstances"><fmt:message key="topicinstances.label"/></a></li>
	    	<li><a href="${it.uuid}/attributetypegroups"><fmt:message key="attributetypegroups.label"/></a></li>
	    	<li>Thema:</li>
	    	
	    	<c:set var="it" value="${it.topic}"></c:set>
	    	<%@ include file="../../snippets/Name.jsp" %>
			<%@ include file="../../snippets/Description.jsp" %>
			<li>Sichtbarkeit des Themas: ${it.visibility}</li>
	    </ul>


	</div>
</form>
</body>
</html>