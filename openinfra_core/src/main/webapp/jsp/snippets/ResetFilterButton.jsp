<!-- 
 Snippet for providing a button that resets all filter settings. 
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div class="col-lg">
		<div class="input-group">
			<form>
				<!-- the reset button for the order by filter -->
				<input class="btn btn-default" type="submit" value="<fmt:message key="resetfilter.button.label" />">
				<!-- Additionally, the form control must also include all existing
					 parameters as hidden fields -->
				<c:forEach items="${param}" var="pageParameter">
					<!-- Don't add the sortOrder parameter again! -->
					<c:if test="${pageParameter.key != 'sortOrder' && pageParameter.key != 'orderBy' && pageParameter.key != 'filter'}">
						<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
					</c:if>
				</c:forEach>
			</form>
		</div>
	</div>
	