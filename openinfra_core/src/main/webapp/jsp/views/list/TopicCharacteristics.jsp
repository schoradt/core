<%@page import="de.btu.openinfra.backend.db.daos.TopicCharacteristicDao"%>
<%@page import="java.util.UUID"%>
<%@page import="de.btu.openinfra.backend.db.daos.TopicInstanceDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>        
	<title>OpenInfRA <fmt:message key="topiccharacteristics.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>
	
	<div class="row">
	  <div class="col-lg-6">
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
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<fmt:message key="topiccharacteristics.label"/>
			<span class="badge">
				<%=new TopicCharacteristicDao(
								UUID.fromString(pageContext.getAttribute("currentProject").toString()),
								OpenInfraSchemas.PROJECTS).getCount()%>
			</span>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>
						<fmt:message key="name.label"/>
					</th>
					<th>
						<fmt:message key="count.label"/>
					</th>
					<th>
						UUID
					</th>
				</tr>
			</thead>
			<c:forEach items="${it}" var="pojo">
				<tr> 
					<td>
		    			<a href="topiccharacteristics/${pojo.uuid}/topicinstances?language=${language}&geomType=${geomType}&offset=0&size=20">
							<c:set var="localizedStrings" value="${pojo.descriptions.localizedStrings}"/>
							<%@ include file="../../snippets/LocalizedStrings.jsp" %>
		    			</a>
					</td>
					<td>
						<c:set var="currentCharacteristic" value="${pojo.uuid}"/>
						<%=new TopicInstanceDao(
								UUID.fromString(pageContext.getAttribute("currentProject").toString()),
								OpenInfraSchemas.PROJECTS).getCount(
										UUID.fromString(pageContext.getAttribute("currentCharacteristic").toString()))%>
					</td>
					<td>
			    		<a href="topiccharacteristics/${pojo.uuid}">
			    			${pojo.uuid}
		    			</a>
					</td>
	    		</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>