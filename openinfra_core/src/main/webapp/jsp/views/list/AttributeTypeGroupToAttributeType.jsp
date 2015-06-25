<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>  
	<title>OpenInfRA <fmt:message key="attributetypegroups.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="content">
		<h2><fmt:message key="attributetypegroups.label"/>: </h2>
		<ul>
			<c:forEach items="${it}" var="pojo">    		
				<c:set var="desc" value="" />
	    		<c:forEach items="${pojo.attributeTypeGroup.descriptions.localizedStrings}" var="item">
	  				<c:set var="desc" value="${desc} ${item.characterString}" />
				</c:forEach>
	    					
	    		<li title="Beschreibung: ${desc}">
	    			<a href="../../attributetypegroups/${pojo.attributeTypeGroup.uuid}">
		    			<c:forEach items="${pojo.attributeTypeGroup.names.localizedStrings}" var="item">
		    				${item.characterString}
		    			</c:forEach>
		    			<span class="uuid">(${pojo.attributeTypeGroup.uuid})</span>
	    			</a>
	    			<c:set var="it" value="${pojo.attributeTypeGroup}" />
	    			<%@ include file="../../snippets/Name.jsp" %>
					<%@ include file="../../snippets/Description.jsp" %>
	    		</li>
	    		
	   			<li>
	   				<a href="../../multiplicities/${pojo.multiplicity.uuid}">Multiplizit&auml;t</a>: 
	    			(max: ${pojo.multiplicity.max}; min: ${pojo.multiplicity.min}) </li>
	    		<li>Order: ${pojo.order}</li>
	    		<br/>
			</c:forEach>
		</ul>
	</div>

</body>
</html>