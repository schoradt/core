<!-- 
This snippet will generate different buttons. To make use of them the following
jstl variables must be set with a value that leads to the appropriated page.
<%--
  <c:set var="detailButton" value="" />
  <c:set var="createButton" value="" />
  <c:set var="editButton" value="" />
  <c:set var="deleteButton" value="" />
--%>
Before a button is printed, the variable will be checked for existence. If for
example you do not need the create button, just do not set the corresponding
jstl variable.

Warning: The jstl variables will be removed after the button is printed.
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${fn:length(detailButton) > 0}">
	<a href="${detailButton}">
		<i class="fa fa-info-circle" title="<fmt:message key="detail.button.label"/>"></i>
	</a>
	<c:remove var="detailButton"/>
</c:if>

<c:if test="${fn:length(createButton) > 0}">
	<a href="${createButton}">
		<i class="fa fa-plus-square" title="<fmt:message key="create.button.label"/>"></i>
	</a>
	<c:remove var="createButton"/>
</c:if>
	
<c:if test="${fn:length(editButton) > 0}">
	<a href="${editButton}">
		<i class="fa fa-pencil-square-o" title="<fmt:message key="edit.button.label"/>"></i>
	</a>
	<c:remove var="editButton"/>
</c:if>

<c:if test="${fn:length(deleteButton) > 0}">
	<a href="${deleteButton}">
		<i class="fa fa-trash-o" title="<fmt:message key="delete.button.label"/>"></i>
	</a>
	<c:remove var="deleteButton"/>
</c:if>