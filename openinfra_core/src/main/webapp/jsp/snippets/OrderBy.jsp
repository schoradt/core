<!-- 
 Snippet for providing sort order of columns. This snippet supports a variable
 named columns that must contain UUIDs. This UUIDs must be primary keys of
 attribute type. If this variable is set the attribute names will be printed in
 the orderBy select box instead of NAME and DESCRIPTION.
 -->

<%@page import="de.btu.openinfra.backend.db.daos.AttributeTypeDao"%>
<%@page import="de.btu.openinfra.backend.db.daos.PtLocaleDao"%>
<%@page import="de.btu.openinfra.backend.db.OpenInfraSchemas"%>
<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<div class="col-lg-2">
		<div class="input-group">
			<form>
				<!-- the select box for the column -->
				<select id="orderBy" name="orderBy" onchange="submit()" class="form-control">
					<option disabled selected><fmt:message key="orderby.label"/></option>
					<c:choose>
						<c:when test="${fn:length(columns) > 0}">
							<!-- get the column names from the columns variable if it is set  -->
							<c:forEach items="${columns}" var="column">
								<%
									pageContext.setAttribute(
										"columnName",
										new AttributeTypeDao(
										UUID.fromString(pageContext.getAttribute("currentProject").toString()),
										OpenInfraSchemas.PROJECTS).read(
												PtLocaleDao.forLanguageTag(session.getAttribute("language").toString()), 
												UUID.fromString(pageContext.getAttribute("column").toString())),
										PageContext.PAGE_SCOPE);
								%>
								<!-- only show the option if it has a translation -->
								<c:if test="${columnName.names.localizedStrings[0] != null}">
									<option ${param.orderBy == column ? 'selected' : ''} value="${column}">
										${columnName.names.localizedStrings[0].characterString}
										<c:if test="${columnName.domain != null}">
											(<fmt:message key="domain.label"/>)
										</c:if>
									</option>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<!-- print the static column names  NAME and DESCRIPTION -->
							<option ${param.orderBy == 'NAME' ? 'selected' : ''} value="NAME">
								<fmt:message key="name.label"/>
							</option>
							<option ${param.orderBy == 'DESCRIPTION' ? 'selected' : ''} value="DESCRIPTION">
								<fmt:message key="description.label"/>
							</option>
						</c:otherwise>
					</c:choose>
				</select>
				<!-- Additionally, the form control must also include all existing
					 parameters as hidden fields -->
				<c:forEach items="${param}" var="pageParameter">
					<!-- Don't add the orderBy parameter again! -->
					<c:if test="${pageParameter.key != ''}">
						<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
					</c:if>
				</c:forEach>
				<c:if test="${empty param.sortOrder}">
					<input type="hidden" name="sortOrder" value="ASC"/>
				</c:if>
			</form>
		</div><!-- /input-group -->
	</div><!-- /.col-lg-2 -->
	
	<div class="col-lg-2">
		<div class="input-group">
			<form>
				<!-- the select box for the sort order -->
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
	</div>
	