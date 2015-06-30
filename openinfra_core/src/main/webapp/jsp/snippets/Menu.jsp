<%@page import="java.util.UUID"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="de.btu.openinfra.backend.db.pojos.AttributeValueGeomType"%>
<%@page import="de.btu.openinfra.backend.db.daos.OpenInfraSchemas"%>
<%@page import="de.btu.openinfra.backend.db.daos.PtLocaleDao"%>
<%@page import="java.awt.event.ItemEvent"%>
<%@page import="de.btu.openinfra.backend.db.pojos.LocalizedString"%>
<%@page import="de.btu.openinfra.backend.db.pojos.NamePojo"%>
<%@page import="de.btu.openinfra.backend.db.pojos.ProjectPojo"%>
<%@page import="de.btu.openinfra.backend.db.daos.ProjectDao"%>
<%@page import="de.btu.openinfra.backend.OpenInfraApplication"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<header class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container" style="width: 100%;">
  <div id="header">
	<table>
		<tr>
			<td>
				<img style="width: 80px;" alt="DAI" src="${contextPath}/img/Logo.png" title="Logo">
			</td>
			<td>
				<h1>penInfRA 
					<span class="small">(${openInfraVersion})</span>
				</h1>
			</td>
		</tr>
	</table>
	<hr/>
	</div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <c:choose>
        	<c:when test="${fn:contains(requestUrl, '/rest/search')}">
		        <li>
		        	<a href="${contextPath}/rest/system">
		        		<fmt:message key="system.label"></fmt:message>
		        	</a>
		        </li>
		        <li>
		        	<a href="${contextPath}/rest/projects">
		        		<fmt:message key="projects.label"></fmt:message>
		        	</a>
		        </li>
        	</c:when>
        	<c:when test="${fn:contains(requestUrl, '/rest/system')}">
		        <li class="active">
		        	<a href="${contextPath}/rest/system">
		        		<fmt:message key="system.label"></fmt:message>
		        	</a>
		        </li>
		        <li>
		        	<a href="${contextPath}/rest/projects">
		        		<fmt:message key="projects.label"></fmt:message>
		        	</a>
		        </li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
		          	<fmt:message key="menu.label"></fmt:message><span class="caret"></span>
		          </a>
		          <ul class="dropdown-menu" role="menu">
					<li><a href="system/countrycodes"><fmt:message key="countrycodes.label"/></a></li>
					<li><a href="system/charactercodes"><fmt:message key="charactercodes.label"/></a></li>
					<li><a href="system/languagecodes"><fmt:message key="languagecodes.label"/></a></li>
					<li><a href="system/attributetypegroups"><fmt:message key="attributetypegroups.label"/></a></li>
					<li><a href="system/attributetypes"><fmt:message key="attributetypes.label"/></a></li>
					<li><a href="system/ptlocales"><fmt:message key="languages.label"/></a></li>
					<li><a href="system/topiccharacteristics"><fmt:message key="topiccharacteristics.label"/></a></li>
					<li><a href="system/valuelists"><fmt:message key="valuelists.label"/></a></li>
		          </ul>
		        </li>
        	</c:when>
        	<c:when test="${fn:contains(requestUrl, '/rest/projects/')}">
		        <li>
		        	<a href="${contextPath}/rest/system">
		        		<fmt:message key="system.label"/>
		        	</a>
		        </li>
		        <li class="active">
		        	<a href="${contextPath}/rest/projects">
		        		<fmt:message key="projects.label"/>
		        	</a>
		        </li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
		          	<fmt:message key="menu.label"/><span class="caret"></span>
		          </a>
		          <c:set var="link" value="${req.scheme}://${req.serverName}:${req.serverPort}${contextPath}/rest/projects/${currentProject}"/>
		          <ul class="dropdown-menu" role="menu">
        			<c:if test="${fn:contains(requestUrl, '/topicinstances/')}">
        				<li><a style="cursor: pointer;"><fmt:message key="new.topicinstance.label"/></a></li>
        				<li class="divider"></li>
        			</c:if>
		          	<!-- not implemented yet -->
		          	<!--  li><fmt:message key="new.subproject.label"/></li -->
		          	<li><a href="${link}"><fmt:message key="edit.project.label"/></a></li>
		          	<!-- http://jsfiddle.net/L3ddq/1/ -->
		          	<li><a id="deleteProject" style="cursor: pointer;"><fmt:message key="delete.project.label"/></a></li>
		          						
		          	<li class="divider"></li>
			        <li><a href="${link}/subprojects"><fmt:message key="subprojects.label"/></a></li>
					<li><a href="${link}/topiccharacteristics"><fmt:message key="topiccharacteristics.label"/></a></li>
					<li><a href="${link}/attributetypes"><fmt:message key="attributetypes.label"/></a></li>
					<li><a href="${link}/attributetypegroups"><fmt:message key="attributetypegroups.label"/></a></li>
					<li><a href="${link}/valuelists"><fmt:message key="valuelists.label"/></a></li>
					<li><a href="${link}/ptlocales"><fmt:message key="locales.label"/></a></li>
					<li><a href="${link}/multiplicities"><fmt:message key="multiplicities.label"/></a></li>
		          </ul>
		        </li>
        	</c:when>
        	<c:when test="${fn:contains(requestUrl, '/rest/projects')}">
		        <li>
		        	<a href="${contextPath}/rest/system">
		        		<fmt:message key="system.label"/>
		        	</a>
		        </li>
		        <li class="active">
		        	<a href="${contextPath}/rest/projects">
		        		<fmt:message key="projects.label"/>
		        	</a>
		        </li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
		          	<fmt:message key="menu.label"></fmt:message><span class="caret"></span>
		          </a>
		          <ul class="dropdown-menu" role="menu">
		          	<li>Neues Projekt</li>
		          </ul>
		        </li>
        	</c:when>
        </c:choose>
        <li>
        	<a href="${pageContext.request.contextPath}/3d/index.html"><fmt:message key="3dwebgis.label"/></a>
        </li>
      </ul>
      <!-- http://stackoverflow.com/questions/4276061/how-to-internationalize-a-java-web-application -->
		<form class="navbar-form navbar-right">
		    <select id="language" name="language" onchange="submit()" class="form-control">
          	<% 	// Get all languages and ensure that this method is only called
          	    // once per session!
          		if(pageContext.findAttribute("lang") == null) {
          			pageContext.setAttribute(
          					"lang", 
          					new PtLocaleDao(
          							null, 
          							OpenInfraSchemas.SYSTEM).read(
          									null, 0, Integer.MAX_VALUE), 
          									PageContext.SESSION_SCOPE);
          		} // end if
          	 %>
	          	<c:forEach items="${lang}" var="item">
	          		<c:set var="currentLanguage">${item.languageCode}-${item.countryCode}</c:set>
	          		<c:if test="${item.languageCode != 'xx'}">
	          			<c:if test="${language == currentLanguage}">
	          				<c:set var="languageId" value="${item.uuid}" scope="page"/>
	          			</c:if>
					    <option value="${currentLanguage}" ${language == currentLanguage ? 'selected' : ''} >
					    	<c:out value="${currentLanguage}"></c:out>
					    </option>
		    		</c:if>
	          	</c:forEach>
		    </select>
		    <!-- Additionally, the form control must also include all existing
		         parameters as hidden fields -->
		    <c:forEach items="${param}" var="pageParameter">
		    	<!-- Don't add the language parameter again! -->
		    	<c:if test="${pageParameter.key != 'language'}">
		    		<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
		    	</c:if>
      		</c:forEach>
		</form>
      <form class="navbar-form navbar-right" method="get" action="/openinfra_backend/rest/search" role="search">
        <div class="form-group">
        <!-- Check the query parameter and create an input field with the query
        	 as value or an input field a placeholder -->
		  <c:choose>
            <c:when test="${fn:length(param.query) > 0}">
              <input type="text" class="form-control" name="query" value="${param.query}">
            </c:when>
            <c:otherwise>
              <input type="text" class="form-control" name="query" placeholder="<fmt:message key="searchplaceholder.label"/>">
            </c:otherwise>
          </c:choose>
        </div>

        <button type="submit" class="btn btn-default"><fmt:message key="searchbutton.label"/></button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->  
<ol class="breadcrumb">
	<c:choose>
		<c:when test="${fn:contains(requestUrl, '/rest/projects/')}">
			<!-- Iterate over all bread crumbs --> 
			<c:forEach items="${breadCrumbs}" var="crumb">
				<li>
					<!-- define the URL -->
					<a href="${contextPath}/rest/${crumb.value}">
					<!-- either print the label that can be translated via
						 locale files or a already translated dynamic string
						 from the database -->
					<c:choose>
						<c:when test="${fn:contains(crumb.key, breadCrumbLabelMarker)}">
							<c:set var="label" value="${fn:substringAfter(crumb.key, '.#label#.')}"></c:set>
							<fmt:message key="${label}.label"/>
						</c:when>
						<c:otherwise>
							${crumb.key}
						</c:otherwise>
					</c:choose>
					</a>
					
				</li>
			</c:forEach>
		</c:when>
		<c:when test="${fn:contains(requestUrl, '/rest/system')}">
			<li class="active">
				<a href="system">
					<fmt:message key="system.label"/>
				</a>
			</li>
		</c:when>
		<c:when test="${fn:contains(requestUrl, '/rest/projects')}">
			<li class="active">
				<a href="projects">
					<fmt:message key="projects.label"/>
				</a>
			</li>
		</c:when>
		<c:otherwise>
			&nbsp;
		</c:otherwise>
	</c:choose>
</ol>
</header>

<%@ include file="ConfirmDialog.jsp" %>

<script>
$(window).scroll(function() {
	if($(window).scrollTop() > 30) {
		$('#header').slideUp();
	} else {
		$('#header').slideDown(150);
	}
});

$("#xml").click(function() {
	getMediaType("xml");
});

$("#json").click(function() {
	getMediaType("json");
});

function getMediaType(type) {
	$.ajax({
		url: window.location.href,
        dataType: type,
		type: "GET",
		cache: false
	}).done(function(data,status,jqXHR) {
		alert(jqXHR.responseText);
	})}; 
</script>
