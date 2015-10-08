<!-- 
 Snippet for providing a filter box. This filter is currently filtering on the
 NAME column.
 
 TODO: make this more flexible for other columns
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div class="col-lg-2">
		<div class="input-group">
			<form>
				<span class="input-group-btn">
					<input type="text" name="filter" value="<c:out value="${param.filter}"/>" class="form-control" placeholder="Name filtern (%und%)">
					<button class="btn btn-default" type="submit">
						<fmt:message key="searchbutton.label"/>
					</button>
				</span>
				<!-- Additionally, the form control must also include all existing
					 parameters as hidden fields -->
				<c:forEach items="${param}" var="pageParameter">
					<!-- Don't add the language parameter again! -->
					<c:if test="${pageParameter.key != 'filter'}">
						<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
					</c:if>
				</c:forEach>
			</form>
		</div><!-- /input-group -->
	</div><!-- /.col-lg-2 -->
	