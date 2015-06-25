<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
    <c:forEach items="${localizedStrings}" var="item" varStatus="loop">
 	    ${item.characterString}
 	    <c:if test="${!loop.last}">&nbsp;</c:if>
    </c:forEach>
	<c:if test="${fn:length(localizedStrings) == 0}">
		-
	</c:if>