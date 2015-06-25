<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../snippets/Head.jsp" %>
    <title> OpenInfRA <fmt:message key="topiccharacteristics.details.label"/></title>
</head>
<body>
	<%@ include file="../snippets/Menu.jsp" %>
	<div class="panel-group">
		<c:choose>
		<c:when test="${fn:length(it) > 0}">
			<table class="table">
				<thead>
					<tr>
						<th>
							<fmt:message key="topicinstances.label"/>
						</th>
						<th>
							<fmt:message key="searchresult.label"/>
						</th>
					</tr>
				</thead>
				
				<c:forEach items="${it}" var="results">
					<tr>
						<td>
						    <a href="projects/${results.projectId}/topicinstances/${results.topicInstanceId}/topic">
								<c:set var="localizedStrings" value="${results.topicCharacteristic.descriptions.localizedStrings}"/>
								<%@ include file="../snippets/LocalizedStrings.jsp" %>
								(${results.topicInstanceId})
							</a>
						</td>
						<td>
							<a href="projects/${results.projectId}/topicinstances/${results.topicInstanceId}/topic">
								<c:forEach items="${results.result}" var="result">
									${result}
								</c:forEach>
							</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<fmt:message key="nosearchresult.label"/>
		</c:otherwise>
		</c:choose>
	</div>
	

</body>
</html>