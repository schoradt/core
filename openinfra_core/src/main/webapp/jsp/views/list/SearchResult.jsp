<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../../snippets/Head.jsp" %>
    <title> OpenInfRA <fmt:message key="topiccharacteristics.details.label"/></title>
</head>
<body>
 	<%@ include file="../../snippets/Menu.jsp" %>
 	
 	<div class="panel-group">
		<table class="table">
			<thead>
				<tr>
					<th>
						<fmt:message key="topicinstances.label"/>
					</th>
					<th>
						<fmt:message key="topiccharacteristics.label"/>
					</th>
					<th>
						<fmt:message key="projects.label"/>
					</th>
					<th>
						<fmt:message key="searchresult.label"/>
					</th>
				</tr>
			</thead>
			<tbody id="results">
				
			</tbody>
		</table>
	</div>
 	
	<script language="javascript" type="text/javascript">

		$(document).ready(function() {
			// save the value of the search field
		    var searchString = getUrlParameter("query");
	    
		    if (searchString != "") {
		     	// create the base path of the application
				var searchPath = "${contextPath}/rest/v1/search?language=de-DE";
				var data = new Object();
				
				// build the data object with the information from the input field
				data["rawSolrQuery"] = searchString;
				
				// set the new data
				$.ajax({
					url: searchPath,
					type: "POST",
					data: JSON.stringify(data),
					contentType: "application/json; charset=utf-8",
					cache: false,
			 		error: function(xhr){
			 			alert("fail");
				  	}, // end error
					success: function(result) {
					    for (var key in result) {
					        addLine(result[key]);
					    }
					   	
					} // end success
			  	}); // end ajax call
			}
		});
		
		function addLine(result) {
		    var tr = document.createElement("tr");
		    var tiPath = "${contextPath}/rest/v1/projects/";
		    
		    for (var key in result) {
		        var td = document.createElement("td");
		        
		        if (key == "highlight") {
		            for (var hl in result[key]) {
		                var t = document.createTextNode(hl + ": " + result[key][hl]);
		                td.appendChild(t);
		            }
		        } else {
		    		var t = document.createTextNode(result[key]);
		    		if(key =="topicInstanceId") {
		    		    var a = document.createElement("a");
		    		    a.setAttribute('href', tiPath + result["projectId"] + "/topicinstances/" + result[key] + "/topic");
		    		    a.appendChild(t);
		    		    t = a;
		    		}
		    		td.appendChild(t);
		    	}
			    tr.appendChild(td);
		    }
		    
		    tr.appendChild(td);
		    document.getElementById("results").appendChild(tr);
		    
		};
		
		function getUrlParameter(sParam) {
		    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
		        sURLVariables = sPageURL.split('&'),
		        sParameterName,
		        i;

		    for (i = 0; i < sURLVariables.length; i++) {
		        sParameterName = sURLVariables[i].split('=');

		        if (sParameterName[0] === sParam) {
		            return sParameterName[1] === undefined ? true : sParameterName[1];
		        }
		    }
		};
	</script>
	
</body>
</html>