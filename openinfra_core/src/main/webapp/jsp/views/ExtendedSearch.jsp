<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="de.btu.openinfra.backend.db.daos.AttributeTypeDao"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../snippets/Head.jsp" %>
    <title> OpenInfRA <fmt:message key="search.extended.label"/></title>
</head>
<body>
 	<%@ include file="../snippets/Menu.jsp" %>
 	
 	
	<a id="newLineButton" href="#" onclick="addLine()"><i style="margin-top: 6px; margin-left: 20px; font-size: 1.5em;"class="fa fa-plus-square"></i></a>
 	
 	<form method="GET" action="${contextPath}/rest/v1/search/result">
		<div class="form-group form-left">
			<input id="hiddenCounter" name="hc" type="hidden" value="0"></input>
			<input name="start" type="hidden" value="0"></input>
			<input name="rows" type="hidden" value="20"></input>
			<table class="table">
				<tbody id="fields">
					
				</tbody>
			</table>
			<button type="submit" class="btn btn-default"><fmt:message key="searchbutton.label"/></button>
		</div>
	</form>
	
	<script language="javascript" type="text/javascript">
	
		var attributeTypes = [];
	
		$(document).ready(function() {
		    //loadAttributeTypes();
		    addLine();
		});
		
		function loadAttributeTypes() {
		    // TODO retrieve types from selected projects!
		    var url = "${contextPath}/rest/v1/system/attributetypes.json?language=de-DE";
		    $.ajax({
		        url: url,
		    	type: "GET",
		    	cache: false,
		    	error: function(xhr){
		 			alert("fail");
			  	}, // end error
				success: function(result) {
				    // save all attribute types in an array
				    for (var key in result) {
				        attributeTypes.push(result[key]["names"]["localizedStrings"][0]["characterString"]);
				    }
				    addLine();
				}
		    });
		};
		
		function addLine() {
		    // check the count of the lines
		    var lines = $("#fields tr").length+1;
		    
		    // add the row
	    	$("#fields").append("<tr id=line_"+ lines +"></tr>");
		    
		    
			 // only add the logical operator if a line already exists
		    if (lines > 1) {
		        $("#line_"+lines).append(
		                "<td><select name=lo"+lines+" size=1>"+
			                "<option>AND</option>"+
			                "<option>OR</option>"+
	            		"</select></td>");
		    } else {
		        $("#line_"+lines).append(
		                "<td></td>");
		    }
			/*
			// add the attribute type
			var attributeTypeOptions = "<td><select name=attributeType size=1>"+
				"<option></option>";
				
			// add the attribute type options
			for (var key in attributeTypes) {
			    attributeTypeOptions += "<option>"+ attributeTypes[key] +"</option>";
			}
			attributeTypeOptions += "</select></td>";
		    $("#line_"+lines).append(attributeTypeOptions);
		    */
		    $("#line_"+lines).append("<td><input class=form-control name=at"+lines+" /></td>");
			
			// add the relational operator
		    $("#line_"+lines).append(
		            "<td><select onchange=\"show(this)\" class=form-control name=ro"+lines+" size=1>"+
			            "<option value=\"EQUAL\">=</option>"+
			            "<option value=\"GREATER_THAN\">></option>"+
			            "<option value=\"SMALLER_THAN\"><</option>"+
			            "<option value=\"BETWEEN\">[...]</option>"+
		            "</select></td>");
			
			// add the mandatory option
		    $("#line_"+lines).append(
		            "<td><select class=form-control name=m"+lines+" size=1>"+
			            "<option value=\"NEVER_MIND\"></option>"+
			            "<option value=\"MUST\">+</option>"+
			            "<option value=\"MUST_NOT\">-</option>"+
		            "</select></td>");
			
			// add the attribute value
		    $("#line_"+lines).append("<td><input class=form-control name=av"+lines+" type=text/></td>");
			
			// add the till attribute value
		    $("#line_"+lines).append("<td><input class=form-control name=tav"+lines+" id=tav"+lines+"_field /></td>");
		    $("#tav"+lines+"_field").hide();
		    
			// add the relevance
		    $("#line_"+lines).append("<td><input class=form-control name=r"+lines+" type=number min=0 max=100 step=1 value=100 /></td>");
			
			// add the fuzzy button
			$("#line_"+lines).append("<td><input class=form-control name=f"+lines+" type=checkbox /></td>");
			
		 	// add button for deleting line
			$("#line_"+lines).append("<td><a id=\"deleteLineButton\" href=\"#\" onclick=\"deleteLine(this)\"><i style=\"padding-top:6px; font-size: 1.5em;\"class=\"fa fa-trash-o\"></i></a></td>");
		 	
		 	// set the number of lines to a hidden element
		 	$("#hiddenCounter").val(lines);
		};
		
		function deleteLine(element) {
		    if (element != null) {
		        var elem = element.parentNode;
		        elem.parentNode.remove();
		        $("#hiddenCounter").val($("#fields tr").length-1);
			}
		}
		
		function show(element) {
		    if (element != null) {
		        var elem = element.parentNode;
		        var id = elem.parentNode.id;
		        
		        if (element.options[element.selectedIndex].value == "BETWEEN") {
		            $("#tav"+id.split("_")[1]+"_field").show();
		        } else {
		            $("#tav"+id.split("_")[1]+"_field").hide();
		        }
		        
		    }
		}
	</script>
	
</body>
</html>