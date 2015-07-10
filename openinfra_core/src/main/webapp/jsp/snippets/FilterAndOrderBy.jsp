<!-- 
 Snippet for providing sort order of columns.
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div class="row">
	  <div class="col-lg-2">
	    <div class="input-group">
			<form>
			    <select id="orderBy" name="orderBy" onchange="submit()" class="form-control">
			    	<option disabled selected><fmt:message key="orderby.label"/></option>
				    <option ${param.orderBy == 'NAME' ? 'selected' : ''} value="NAME">
				    	<fmt:message key="name.label"/>
				    </option>
				    <option ${param.orderBy == 'DESCRIPTION' ? 'selected' : ''} value="DESCRIPTION">
				    	<fmt:message key="description.label"/>
				    </option>
			    </select>
			    <!-- Additionally, the form control must also include all existing
			         parameters as hidden fields -->
			    <c:forEach items="${param}" var="pageParameter">
			    	<!-- Don't add the orderBy parameter again! -->
			    	<c:if test="${pageParameter.key != ''}">
			    		<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
			    	</c:if>
			    </c:forEach>
			    <if test="${empty param.sortOrder}">
			    	<input type="hidden" name="sortOrder" value="ASC"/>
			    </if>
			</form>
	    </div><!-- /input-group -->
	  </div><!-- /.col-lg-6 -->
	  <div class="col-lg-2">
	    <div class="input-group">
			<form>
			    <select id="sortOrder" name="sortOrder" onchange="submit()" class="form-control">
				    <option disabled selected><fmt:message key="sortingorder.label"/></option>
				    <option value="ASC" ${param.sortOrder == 'ASC' ? 'selected' : ''} 
				                        ${(not empty param.orderBy && empty param.sortOrder) ? 'selected' : ''}>
				    	<fmt:message key="ascending.label"/>
				    </option>
				    <option value="DESC" ${param.sortOrder == 'DESC' ? 'selected' : ''}>
				    	<fmt:message key="descending.label"/>
				    </option>
			    </select>
			    <!-- Additionally, the form control must also include all existing
			         parameters as hidden fields -->
			    <c:forEach items="${param}" var="pageParameter">
			    	<!-- Don't add the sortOrder parameter again! -->
			    	<c:if test="${pageParameter.key != 'sortOrder'}">
			    		<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
			    	</c:if>
			    </c:forEach>
			</form>
	    </div><!-- /input-group -->
	  </div><!-- /.col-lg -->
	  <div class="col-lg-2">
	    <div class="input-group">
	     <form>
		      <input type="text" name="filter" value="<c:out value="${param.filter}"/>" class="form-control" placeholder="Name filtern (%und%)">
		      <span class="input-group-btn">
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
	  </div><!-- /.col-lg-6 -->
	</div><!-- /.row -->
	
	<br/>