<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
	
    <title> OpenInfRA
    	<c:forEach items="${it.names.localizedStrings}" var="ls">
    		<c:out value="${ls.characterString}"></c:out>
    	</c:forEach>
    </title>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="content">
		<h2><fmt:message key="project.details.label"/>:
			  <c:set var="localizedStrings" value="${it.names.localizedStrings}"/>
			  <c:set var="projectname">
			  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
			  </c:set>
	    	<c:out value="${projectname}"/>
		</h2>
		
		<form enctype='application/json'>
		  <div class="form-group">
		    <label for="projectid"><fmt:message key="id.label"/>:</label>
		    <input type="text" class="form-control" id="projectid" value="${it.uuid}" readonly/>
		  </div>
		  <div class="form-group">
		    <label for="subprojectof"><fmt:message key="project.subprojectof.label"/>:</label>
		    <input type="text" class="form-control" id="subprojectof" value="${it.subprojectOf}" readonly/>
		  </div>
		  <div class="form-group">
		    <label for="projectname"><fmt:message key="name.label"/>:</label>
		    <input type="text" class="form-control" id="projectname" value="${projectname}" readonly/>
		    <input type="hidden" id="projectnameLang" value="${it.names.uuid}" readonly/>
		  </div>
		  <c:set var="localizedStrings" value="${it.descriptions.localizedStrings}"/>
		  <c:set var="projectdescription">
		  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
		  </c:set>
		  <div class="form-group">
		    <label for="projectdescription"><fmt:message key="description.label"/>:</label>
		    <textarea class="form-control" rows="4" id="projectdescription" readonly>${projectdescription}</textarea>
		    <input type="hidden" id="projectdescriptionLang" value="${it.descriptions.uuid}" readonly/>
		  </div>
		  <!-- Messages -->
		  <div style="display: none;" id="result"></div>
		  <div class="alert alert-success" style="display: none;" role="alert" id="success"></div>
		  <div class="alert alert-danger" style="display: none;" role="alert" id="alert"></div>
		  <a class="btn btn-default view" role="button" id="edit">
		  	<fmt:message key="edit.button.label"/>
		  </a>
		  <button class="btn btn-default input" style="display: none;" type="reset" id="cancel">
		  	<fmt:message key="cancel.button.label"/>
		  </button>
		  <a class="btn btn-default input" style="display: none;" id="save" role="button">
		  	<fmt:message key="save.button.label"/>
		  </a>
		</form>
		
		<hr/>
		
	</div>

	<script type="text/javascript">
		$("#save").click(function() {
		  var projectJson = JSON.stringify({
				  "uuid":$("#projectid").val(),
				  "descriptions":{"uuid":$("#projectdescriptionLang").val(),
					  "localizedStrings":[{"characterString":$("#projectdescription").val(),
					  "locale":{"uuid":"<%= pageContext.getAttribute("languageId") %>"}}]},
				  "names":{"uuid":$("#projectnameLang").val(),
					  "localizedStrings":[{"characterString":$("#projectname").val(),
					  "locale":{"uuid":"<%= pageContext.getAttribute("languageId") %>"}}]},
					  "subprojectOf":$("#subprojectof").val()});
		  
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
					  $('#projectname, #projectdescription').prop('readonly', true);
					  $(".view").show();
				      $(".input").hide();
			      } // end success
			}); // end ajax call

 		}); // end click function
		
		$("#edit").click(function() {
			$('#projectname, #projectdescription').prop('readonly', false);
			$(".view").hide();
			$(".input").save();
		});
		
		$("#cancel").click(function() {
			$('#projectname, #projectdescription').prop('readonly', true);
			$(".view").show();
			$(".input").hide();
		});
	</script>
</body>
</html>