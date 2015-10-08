<!-- 
This snippet will generate the control buttons for the creation or edit mode.
Set the jstl variables as shown to get the fitting buttons. If the variable is
not set the buttons will not created.

<%--
  <c:set var="edit" value="true" />   generate: Save, Cancel, Edit
  <c:set var="edit" value="false" />  generate: Save, Cancel
--%>

Warning: The jstl variables will be removed after the button is printed.
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${edit != null && false}">
	<div id="controlButtons">
		<!-- Define the safe mode. Initial it will always be create. -->
		<c:set var="saveMode" value="create" />
		<c:if test="${edit}">
			<!-- Create the Edit button. -->
			<a class="btn btn-default view" role="button" id="edit">
				<fmt:message key="edit.button.label"/>
			</a>
			
			<!-- Set the display style for the Cancel and Save button to none. -->
			<c:set var="display" value="style=\"display: none;\""></c:set>
			
			<!-- Overwrite the safe mode. -->
			<c:set var="saveMode" value="save" />
			
			<!-- Create the Cancel button. -->
			<button class="btn btn-default input" ${display} type="reset" id="cancel">
				<fmt:message key="cancel.button.label"/>
			</button>
		</c:if>
		
		
		
		<!-- Always create the Save button. -->
		<a class="btn btn-default input" ${display} id="${saveMode}" role="button">
			<fmt:message key="save.button.label"/>
		</a>
		<c:remove var="edit"/>
	</div>
</c:if>
