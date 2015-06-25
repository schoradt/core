<%@page import="de.btu.openinfra.backend.db.daos.ValueListDao"%>
<%@page import="java.util.UUID"%>
<%@page import="de.btu.openinfra.backend.db.daos.TopicCharacteristicDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="../../snippets/Head.jsp" %>
	<title>OpenInfRA <fmt:message key="projects.label"/></title>
</head>
<body style="height: 438px; ">

<%@ include file="../../snippets/Menu.jsp" %>

<div class="panel panel-default">
	<c:choose>
		<c:when test="${fn:length(it) == 0}">
			<fmt:message key="nosubprojects.label"/>
		</c:when>
		<c:otherwise>
			<c:forEach items="${it}" var="pojo">
				<div class="well">
					<div class="media">
						<c:set var="name" value=""/>
						<c:forEach items="${pojo.names.localizedStrings}" var="item">
				    		<c:set var="name" value="${name} ${item.characterString}"/>
				    	</c:forEach>
				    	<!-- Hard coded images -- this should be retrieved by metadata in the future -->
						<a class="pull-left">
							<c:choose>
								<c:when test="${fn:contains(pojo.uuid, 'fd27a347-4e33-4ed7-aebc-eeff6dbf1054')}">
									<img alt="${name}" style="width: 250px;" src="${contextPath}/img/Baalbek.jpg">
								</c:when>
								<c:when test="${fn:contains(pojo.uuid, '7d431941-eece-48ac-bce5-3062d8d32e76')}">
									<img alt="${name}" style="width: 250px;" src="${contextPath}/img/Palatin.jpg">
								</c:when>
								<c:when test="${fn:contains(pojo.uuid, 'e7d42bff-4e40-4f43-9d1b-1dc5a190cd75')}">
									<img alt="${name}" style="width: 250px;" src="${contextPath}/img/TestProject.jpg">
								</c:when>
								<c:otherwise>
							    	<span style="font-size:3em;" class="glyphicon glyphicon-picture" aria-hidden="true"></span>
								</c:otherwise>
							</c:choose>
						</a>

				  		<div class="media-body">
				  			<a href="<%= request.getContextPath() %>/rest/projects/${pojo.uuid}/topiccharacteristics">
				    			<h4 class="media-heading">
				    				${name}
				    				<span class="small">(${pojo.uuid})</span>
				    			</h4>
				    		</a>
				 		   <div class="media">
						   		<c:forEach items="${pojo.descriptions.localizedStrings}" var="item">
				 					<c:out value="${item.characterString}"></c:out>
								</c:forEach>&nbsp;
				    		</div>
				    		<br/>
				    		<div class="media-body">
								<div style="padding-bottom: 3px;">
					    			<a href="<%= request.getContextPath() %>/rest/projects/${pojo.uuid}/topiccharacteristics">
					    				<fmt:message key="topiccharacteristics.label"/>
					    			</a>
					    			<span class="badge">
							    		<c:set var="currentProject" value="${pojo.uuid}"/>
										<%=new TopicCharacteristicDao(
										UUID.fromString(pageContext.getAttribute("currentProject").toString()),
										OpenInfraSchemas.PROJECTS).getCount()%>
					    			</span>
					    		</div>
					    		
					    		<div style="padding-bottom: 3px;">
					    			<a href="<%= request.getContextPath() %>/rest/projects/${pojo.uuid}/valuelists">
					    				<fmt:message key="valuelists.label"/>
						    			<span class="badge">
											<%=new ValueListDao(
											UUID.fromString(pageContext.getAttribute("currentProject").toString()),
											OpenInfraSchemas.PROJECTS).getCount()%>
						    			</span>
					    			</a>					    		
					    		</div>
					    		
				    			<a href="<%= request.getContextPath() %>/rest/projects/${pojo.uuid}/subprojects">
				    				<fmt:message key="subprojects.label"/>:
					    			<span class="badge">
										<%=new ProjectDao(
										UUID.fromString(pageContext.getAttribute("currentProject").toString()),
										OpenInfraSchemas.PROJECTS).getSubProjectsCount()%>
					    			</span>
				    			</a>
							   	<ul>
							   		<c:set var="subProjects" value=""/>
									<%
										pageContext.setAttribute("subProjects", 
																		new ProjectDao(
																				UUID.fromString(pageContext.getAttribute("currentProject").toString()),
																				OpenInfraSchemas.PROJECTS).readSubProjects(
																						PtLocaleDao.forLanguageTag(session.getAttribute("language").toString()), 
																								0, 
																								Integer.MAX_VALUE));
									%>
							   		<c:forEach var="subProject" items="${subProjects}">
								   		<li>
								   			<a href="<%= request.getContextPath() %>/rest/projects/${subProject.uuid}/topiccharacteristics">
								   				${subProject.names.localizedStrings[0].characterString}
								   			</a>
								   		</li>
							   		</c:forEach>
							   	</ul>
				    		</div>   		
						</div>
						
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>

</body>
</html>