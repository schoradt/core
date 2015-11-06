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
 	
 	Ihre Suche nach <span id="query"><b></b></span> ergab 
 	<span id="resultCount"><b></b></span> Treffer 
 	und dauerte <span id="elapsedTime"><b></b></span> Sekunden.
 	<br /><br />
 	
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
	
	<div class="urlImageContainer">
		<img id="url" align="middle" src="${contextPath}/img/url.gif"/>
	</div>
		
	<script language="javascript" type="text/javascript">

		$(document).ready(function() {
			// save the value of the search field
		    var searchString = getUrlParameter("query");
			var start = getUrlParameter("start");
			var rows = getUrlParameter("rows");
			
			// this is just a workaround for the GUI
			if (start == null || start < 0) { start = 0; }
			if (rows == null || rows < 0) { rows = 20; }
	    
		    if (searchString != "") {
		     	// create the base path of the application
				var searchPath = "${contextPath}/rest/v1/search?language=de-DE&start="+ start +"&rows="+ rows;
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
					    // write the results
					    for (var key in result["databaseResult"]) {
					        addLine(result["databaseResult"][key]);
					    }
					    // write the query
					    $("#query b").text(searchString);
					    
						// write the result count
						$("#resultCount b").text(result["resultCount"]);
					    
					    // write the elapsed time
					    $("#elapsedTime b").text(result["elapsedTime"]/1000);
					    
					    // hide the loading image
					    $("#url").hide();
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