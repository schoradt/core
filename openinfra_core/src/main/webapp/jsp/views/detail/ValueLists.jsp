<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title>OpenInfRA <fmt:message key="valuelists.details.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>
	
	<!--
		Force an exception to test if "it" is an object or not. This will
		determine if the creation mode is called.
	 -->
	<c:catch var="createException">
		<c:if test="${it.uuid != null}"></c:if>
	</c:catch>
	
	<!-- If an exception occured set the create variable to true. -->
	<c:choose>
		<c:when test="${createException == null}">
			<c:set var="create" value="false" />
		</c:when>
		<c:otherwise>
			<c:set var="create" value="true" />
		</c:otherwise>
	</c:choose>
	
	<div class="content">
		<h2>
			<!-- Create the header. -->
			<c:choose>
			    <c:when test="${!create}">
					<fmt:message key="valuelists.details.label"/>:
					<c:set var="localizedStrings" value="${it.names.localizedStrings}"/>
					<c:set var="valuelistname">
						<%@ include file="../../snippets/LocalizedStrings.jsp" %>
					</c:set>
					<c:out value="${valuelistname}"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="valuelists.create.label"/>:
				</c:otherwise>
			</c:choose>
		</h2>
		
		<form enctype='application/json'>
			<!-- Create the id field. This is only necessary in the edit mode. -->
			<div class="form-group">
				<c:if test="${!create}">
					<label for="valuelistid"><fmt:message key="id.label"/>:</label>
					<input type="text" class="form-control" id="valuelistid" value="${it.uuid}" readonly/>
				</c:if>
			</div>

			<!-- Create the name field. -->
			<div class="form-group">
				<label for="valuelistname"><fmt:message key="name.label"/>:</label>
				<c:choose>
					<c:when test="${!create}">
						<input type="text" class="form-control" id="valuelistname" value="${valuelistname}" readonly/>
						<input type="hidden" id="valuelistnameLang" value="${it.names.uuid}" readonly/>
					</c:when>
					<c:otherwise>
						<input type="text" class="form-control" id="valuelistname" value="" />
					</c:otherwise>
		    	</c:choose>
			</div>
			
			<!-- Create the description field. -->
			<div class="form-group">
			    <label for="valuelistdescription"><fmt:message key="description.label"/>:</label>
			    <c:choose>
				    <c:when test="${!create}">
					    <c:set var="localizedStrings" value="${it.descriptions.localizedStrings}"/>
						  <c:set var="valuelistdescription">
						  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
						  </c:set>
				    	<textarea class="form-control" rows="4" id="valuelistdescription" readonly>${valuelistdescription}</textarea>
				    	<input type="hidden" id="valuelistdescriptionLang" value="${it.descriptions.uuid}" readonly/>
			    	</c:when>
			    	<c:otherwise>
			    		<textarea class="form-control" rows="4" id="valuelistdescription"></textarea>
			    	</c:otherwise>
		    	</c:choose>
			</div>

			<!-- Add message container. -->
			<%@ include file="../../snippets/MessageBox.jsp" %>
			
			<!-- Add control buttons. -->
			<c:set var="edit" value="${!create}" />
			<%@ include file="../../snippets/ControlButtons.jsp" %>
			
		</form>
		
		<hr/>
	</div>
	
	<script type="text/javascript">
		$("#save").click(function() {
		  var projectJson = JSON.stringify({
				  "uuid":$("#valuelistid").val(),
				  "descriptions":{"uuid":$("#valuelistdescriptionLang").val(),
					  "localizedStrings":[{"characterString":$("#valuelistdescription").val(),
					  "locale":{"uuid":"<%= pageContext.getAttribute("languageId") %>"}}]},
				  "names":{"uuid":$("#valuelistnameLang").val(),
					  "localizedStrings":[{"characterString":$("#valuelistname").val(),
					  "locale":{"uuid":"<%= pageContext.getAttribute("languageId") %>"}}]}});
		  
			$.ajax({
				  type: "PUT",
				  url: "<%= pageContext.getAttribute("forward") %>",
				  data: projectJson,
				  contentType: "application/json; charset=utf-8",
		 		  error: function(xhr){
				  	  if(xhr.responseText.search('<body>') > -1) {
						  var source = xhr.responseText.split('<body>')[1];
					      $('#alert').html(source.split('</body>')[0]);
						  $('#alert').show();				  		  
				  	  } // end if
			  	  }, // end error
			      success: function(res) {
			    	  $('#success').text(res.message + ": ");
			          $('#success').append(res.uuid);
			          $('#success').show();
					  $('#valuelistname, #valuelistdescription').prop('readonly', true);
					  $(".view").show();
					  $(".input").hide();
			      } // end success
			}); // end ajax call

 		}); // end click function
 		
 		$("#create").click(function() {
 			alert("not implemented yet");
 			return null;
 			  var projectJson = JSON.stringify({
 					  "uuid":$("#valuelistid").val(),
 					  "descriptions":{"uuid":$("#valuelistdescriptionLang").val(),
 						  "localizedStrings":[{"characterString":$("#valuelistdescription").val(),
 						  "locale":{"uuid":"<%= pageContext.getAttribute("languageId") %>"}}]},
 					  "names":{"uuid":$("#valuelistnameLang").val(),
 						  "localizedStrings":[{"characterString":$("#valuelistname").val(),
 						  "locale":{"uuid":"<%= pageContext.getAttribute("languageId") %>"}}]}});
 			  
 				$.ajax({
 					  type: "POST",
 					  url: "<%= pageContext.getAttribute("forward") %>",
 					  data: projectJson,
 					  contentType: "application/json; charset=utf-8",
 			 		  error: function(xhr){
 					  	  if(xhr.responseText.search('<body>') > -1) {
 							  var source = xhr.responseText.split('<body>')[1];
 						      $('#alert').html(source.split('</body>')[0]);
 							  $('#alert').show();				  		  
 					  	  } // end if
 				  	  }, // end error
 				      success: function(res) {
 				    	  $('#success').text(res.message + ": ");
 				          $('#success').append(res.uuid);
 				          $('#success').show();
 						  $('#valuelistname, #valuelistdescription').prop('readonly', true);
 						  $(".view").show();
 						  $(".input").hide();
 				      } // end success
 				}); // end ajax call

 	 		}); // end click function
		
		$("#edit").click(function() {
			$('#valuelistname, #valuelistdescription').prop('readonly', false);
			$(".view").hide();
			$(".input").show();
		});
		
		$("#cancel").click(function() {
			$('#valuelistname, #valuelistdescription').prop('readonly', true);
			$(".view").show();
			$(".input").hide();
		});
	</script>
</body>
</html>