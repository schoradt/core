<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title>OpenInfRA <fmt:message key="valuelists.details.label"/></title>
</head>
<body>
	<%@ include file="../../snippets/Menu.jsp" %>

	<div class="content">
		<h2><fmt:message key="valuelists.details.label"/>:
			  <c:set var="localizedStrings" value="${it.names.localizedStrings}"/>
			  <c:set var="valuelistname">
			  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
			  </c:set>
	    	<c:out value="${valuelistname}"/>
		</h2>
		
		<form enctype='application/json'>
		  <div class="form-group">
		    <label for="valuelistid"><fmt:message key="id.label"/>:</label>
		    <input type="text" class="form-control" id="valuelistid" value="${it.uuid}" readonly/>
		  </div>
		  <div class="form-group">
		    <label for="valuelistname"><fmt:message key="name.label"/>:</label>
		    <input type="text" class="form-control" id="valuelistname" value="${valuelistname}" readonly/>
		    <input type="hidden" id="valuelistnameLang" value="${it.names.uuid}" readonly/>
		  </div>
		  <c:set var="localizedStrings" value="${it.descriptions.localizedStrings}"/>
		  <c:set var="valuelistdescription">
		  	<%@ include file="../../snippets/LocalizedStrings.jsp" %>
		  </c:set>
		  <div class="form-group">
		    <label for="valuelistdescription"><fmt:message key="description.label"/>:</label>
		    <textarea class="form-control" rows="4" id="valuelistdescription" readonly>${valuelistdescription}</textarea>
		    <input type="hidden" id="valuelistdescriptionLang" value="${it.descriptions.uuid}" readonly/>
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