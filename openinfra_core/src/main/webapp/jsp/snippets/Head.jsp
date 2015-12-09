<%@page import="de.btu.openinfra.backend.servlet.OpenInfraVersion"%>
<%@page import="de.btu.openinfra.backend.db.daos.project.ProjectDao"%>
<%@page import="de.btu.openinfra.backend.helper.BreadCrumbNavigation"%>
<%@page import="de.btu.openinfra.backend.OpenInfraApplication"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- ######## Define used variables here. This is a central point! ######## -->
<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="homePage" value="${contextPath}/rest/v1/projects" />

<!-- Set the OpenInfRA version for the current session and make sure that 
	 it is set only once -->
<%  if(pageContext.findAttribute("openInfraVersion") == null) {
	pageContext.setAttribute(
			"openInfraVersion", 
			OpenInfraApplication.getOpenInfraVersion(
					request.getServletContext()), 
					PageContext.SESSION_SCOPE);
} %>
<!-- Set the current project -->
<c:set var="currentProject" value=""/>
<% pageContext.setAttribute("currentProject", ProjectDao.getCurrentProject(
		request.getAttribute("javax.servlet.forward.request_uri").toString())); %>

<!-- a global variable that defines if we are in a project or in the system database -->
<c:choose>
	<c:when test="${fn:contains(requestUrl, '/rest/v1/projects')}">
		<c:set var="schema" value="system" />
		<!-- override the project id -->
		<c:set var="currentProject" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="schema" value="projects" />
	</c:otherwise>
</c:choose>

<!-- ######## HTML head tags ######## -->
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script type='text/javascript' src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
  <script type='text/javascript' src="//cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.js"></script>
<![endif]-->

<!-- add the logo as favorite icon -->
<link rel="icon" href="${contextPath}/img/Logo.png" type="image/png" />

<!-- use jquery -->
<script src="${contextPath}/js/jquery-1.11.2.min.js"></script>
<script src="${contextPath}/js/bootstrap-datepicker.min.js"></script>
<script src="${contextPath}/js/dai_services.js"></script>
<script src="${contextPath}/js/jquery.tablesorter.min.js"></script>
<script src="${contextPath}/js/jasny-bootstrap.min.js"></script>

<!-- use openinfra helper class -->
<script src="${contextPath}/js/openinfraHelper.js"></script>

<!-- Use the non minified bootstrap CSS + theme + jscript in order to provide ie8 support-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.css">
<!-- link rel="stylesheet" href="http://zenon.dainst.org/themes/archaeostrap/css/bootstrap.css"-->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<!-- some own styles -->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/style.css">
<!-- Using datepicker from: http://bootstrap-datepicker.readthedocs.org/en/latest/ -->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap-datepicker.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/dai_services.css">
<link href="${contextPath}/html/font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jasny-bootstrap.css">

<!-- ######## language selection ######## -->
<!-- When the language parameter is not set, redirect to the same page including
     the language parameter. The backend returns all languages if the language 
     parameter isn't set.  -->
<c:set var="req" value="${pageContext.request}" />
<c:set var="forward" value="${req.scheme}://${req.serverName}:${req.serverPort}${requestScope['javax.servlet.forward.request_uri']}" scope="page"/>

<!-- TODO set language and geomtype automatically by project preferences -->
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'de-DE'}" scope="session" />
<c:set var="geomType" value="${not empty param.geomType ? param.geomType : not empty geomType ? geomType : 'TEXT'}" scope="session" />

<!-- Get language for datepicker -->
<c:set var="datepickerI18n" value="${fn:substring(language, 0, 2)}"/>
<script src="${contextPath}/js/bootstrap-datepicker-i18n/bootstrap-datepicker.${datepickerI18n}.min.js" charset="UTF-8"></script>

<!-- Set bread crumb -->
<% if(pageContext.findAttribute("breadCrumbs") == null)  {
    // get the bread crumbs
	pageContext.setAttribute(
			"breadCrumbs",
			BreadCrumbNavigation.getBreadCrumb(
					request.getAttribute("javax.servlet.forward.request_uri").toString(),
					session.getAttribute("language").toString(),
					request.getParameterMap()),
			PageContext.REQUEST_SCOPE);
	// get the marker for identifiying non database translated labels 
	pageContext.setAttribute(
			"breadCrumbLabelMarker",
			BreadCrumbNavigation.labelMarker,
			PageContext.REQUEST_SCOPE);
} %>

<!-- Redirect and don't loose existing parameters -->
<c:if test="${(language != null && (param.language == null || param.language == ''))}">
	<c:set var="existingParameters" value=""/>
	<c:forEach items="${param}" var="pageParameter">
		<c:set var="existingParameters" value="${existingParameters}&${pageParameter.key}=${pageParameter.value}"/>
	</c:forEach>
	<c:choose>
		<c:when test="${existingParameters == ''}">
			<c:redirect url="${forward}?language=${language}"/>
		</c:when>
		<c:otherwise>
			<c:redirect url="${forward}?language=${language}${existingParameters}"/>
		</c:otherwise>
	</c:choose>
	
</c:if>

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="de.btu.openinfra.backend.i18n.openinfra" />

