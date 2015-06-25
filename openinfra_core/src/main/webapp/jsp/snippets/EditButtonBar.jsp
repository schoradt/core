<!-- 
This snippet will generate three buttons: create, edit and delete. To use them
the following three jstl variables must be set with a value that leads to the
appropriated page.

  <c:set var="create" value="" />
  <c:set var="edit" value="" />
  <c:set var="delete" value="" />
  
Before a button is printed, the variable will be checked for existence. If for
example you do not need the create button, just do not set the corresponding
jstl variable.
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${fn:length(create) > 0}">
	<a href="${create}">
		<i class="fa fa-plus-square" title="<fmt:message key="create.button.label"/>"></i>
	</a>
</c:if>
	
<c:if test="${fn:length(edit) > 0}">
	<a href="${edit}">
		<i class="fa fa-pencil-square-o" title="<fmt:message key="edit.button.label"/>"></i>
	</a>
</c:if>

<c:if test="${fn:length(delete) > 0}">
	<a href="${delete}">
		<i class="fa fa-trash-o" title="<fmt:message key="delete.button.label"/>"></i>
	</a>
</c:if>