<%@page import="de.btu.openinfra.backend.db.daos.ValueListValueDao"%>
<%@page import="de.btu.openinfra.backend.helper.ImgUrlHelper"%>
<%@page import="de.btu.openinfra.backend.helper.ImgSize"%>
<%@page import="de.btu.openinfra.backend.db.daos.TopicInstanceAssociationDao"%>
<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../snippets/Head.jsp" %>
    <title> OpenInfRA <fmt:message key="topic.label"/></title>
<body>
	<%@ include file="../snippets/Menu.jsp" %>
	
	<c:forEach items="${it.topicInstance.topicCharacteristic.descriptions.localizedStrings}" var="item">
		<c:set var="instanceDesc" value="${instanceDesc} ${item.characterString}"/>
	</c:forEach>
	
	<div>
		<span style="color:#A9A9A9;"><fmt:message key="export.label"/>:</span>
		<a href="${requestUrl}.pdf?language=${language}" target="_blank">
			<i style="cursor: pointer;" class="fa fa-file-pdf-o fa-lg" title="PDF"></i>
		</a>
		<a href="${requestUrl}.csv?language=${language}">
			<i style="cursor: pointer;" class="fa fa-file-text-o fa-lg" title="CSV"></i>
		</a>
	</div>
	<div><h2><fmt:message key="topic.label"/> ${instanceDesc} <span class="small">(${it.topicInstance.uuid})</span>:</h2></div>
	
	
	<div class="row">
		<div class="col-md-8">
<!-- Iterate over attribute type groups and create a well for each -->
<form id="editForm" enctype='application/json'>
<c:forEach items="${it.attributeTypeGroupsToValues}" var="atg">
	<div class="well">
		<div class="media">
	  		<div class="media-body">
	  			<a href="../../attributetypegroups/${atg.attributeTypeGroup.uuid}">
	    			<h3 class="media-heading">
	    				<c:forEach items="${atg.attributeTypeGroup.names.localizedStrings}" var="item">
	    					${item.characterString}
	    				</c:forEach>
	    			</h3>
	    		</a>
	 		   <div class="media">
			   		<c:forEach items="${atg.attributeTypeGroup.descriptions.localizedStrings}" var="item">
	 					<c:out value="${item.characterString}"/>
					</c:forEach>&nbsp;
	    		</div>
	    		
	    		<!-- Display all attribute types assigned to the current attribute type group -->
	    		
	    		<table class="table">
		    		<c:forEach items="${atg.attributeTypesToValues}" var="atv"  varStatus="atv_loop">
		    		
		    			<!-- Set the variables for the current loop run -->
	    				<c:set var="typeId" value="${atv.attributeType.uuid}"/>
						<c:set var="expliciteType" value="${atv.attributeType.dataType.names.localizedStrings[0].characterString}"/>
						<!-- Store the current value id if present -->
						<c:set var="valueId" value=""/>
						<c:if test="${atv.attributeValue != null}">
							<c:set var="valueId" value="${atv.attributeValue.uuid}"/>
						</c:if>
						
		    			<tr>
		    				<td style="width: 20%;">
					  			<a href="../../attributetypes/${atv.attributeType.uuid}">
				    				<c:forEach items="${atv.attributeType.names.localizedStrings}" var="item">
				    					${item.characterString}
				    				</c:forEach>:
					    		</a>
							</td>
							
							<!-- Create the fields twice: view fields and input fields -->
							<td class="view">
								
								<c:set var="currentValue" value=""/>
								
								<c:choose>
									<c:when test="${atv.attributeValue != null}">
			   							<c:choose>
			   								<c:when test="${atv.attributeValue.attributeValueGeom != null || atv.attributeValue.attributeValueGeomz != null}">
			   								
			   									<div id="map"></div>
			   								
			   									<span id="span_${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}">
			   										<small>
			   											<c:choose>
		   													<c:when test="${atv.attributeValue.attributeValueGeom != null}">
		   														${fn:escapeXml(atv.attributeValue.attributeValueGeom.geom)}
		   														<c:set var="currentValue" value="${atv.attributeValue.attributeValueGeom.geom}"/>   														   			   													
		   													</c:when>
		   													<c:otherwise>
		   														${fn:escapeXml(atv.attributeValue.attributeValueGeomz.geom)}
		   														<c:set var="currentValue" value="${atv.attributeValue.attributeValueGeomz.geom}"/>
		   													</c:otherwise>
		   												</c:choose>
			   										</small>			   										
			   									</span>
			   									<c:set var="selectGeomType" value="true"/>
			   								</c:when>
			   								
			   								<c:when test="${atv.attributeValue.attributeValueDomain != null}">
			   									<c:set var="domDesc" value=""/>
										   		<c:forEach items="${atv.attributeValue.attributeValueDomain.domain.descriptions.localizedStrings}" var="item">
								 					<c:set var="domDesc" value="${domDesc} ${item.characterString}"/>
												</c:forEach>			   									
									  			<a title="${domDesc}" href="../../attributevalues/${atv.attributeValue.uuid}">
								    				<c:forEach items="${atv.attributeValue.attributeValueDomain.domain.names.localizedStrings}" var="item" varStatus="loop">
														<c:set var="currentValue" value="${item.characterString}"/>
													 	<c:if test="${!loop.last}">
													 		<c:set var="currentValue" value="${currentValue}"/>
													 	</c:if>
													</c:forEach>
													<span id="span_${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}">${currentValue}</span>
									    		</a>
			   								</c:when>
			   								
			   								<c:when test="${atv.attributeValue.attributeValueValue != null}">
							    				<c:forEach items="${atv.attributeValue.attributeValueValue.value.localizedStrings}" var="item">
													<c:set var="currentValue" value="${item.characterString}"/>
												 	<c:if test="${!loop.last}">
												 		<c:set var="currentValue" value="${currentValue}"/>
												 	</c:if>
							    					<c:if test="${fn:endsWith(item.characterString, '.JPG') || fn:endsWith(item.characterString, '.jpg')}">
							    						<c:set var="currentImage" value="${item.characterString}"/>
							    							<img alt="<fmt:message key="noimage.label"/>: ${item.characterString}" style="width: 30%;"
							    									src="<%= ImgUrlHelper.getImgUrl(pageContext.getAttribute("currentImage").toString(), ImgSize.BIG) %>"/>													
													</c:if>
							    				</c:forEach>
							    				<span id="span_${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}">${currentValue}</span>
			   								</c:when>

			   							</c:choose>
									</c:when>
									<c:otherwise>
										<span id="span_${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" style="color: grey;"><fmt:message key="novalue.label"/></span>
			   						</c:otherwise>
								</c:choose>
							</td>
							
							<td style="display: none;" class="input">
								<c:choose>
									<c:when test="${atv.attributeType.domain != null}">
										<c:set var="currentDomain" value="${atv.attributeType.domain.uuid}"/>
										<%
											pageContext.setAttribute("values", new ValueListValueDao(
																				UUID.fromString(ProjectDao.getCurrentProject(
																						request.getAttribute("javax.servlet.forward.request_uri").toString())),
																				OpenInfraSchemas.PROJECTS).read(
																						PtLocaleDao.forLanguageTag(session.getAttribute("language").toString()), 
																						UUID.fromString(pageContext.getAttribute("currentDomain").toString()), 
																						0, 
																						Integer.MAX_VALUE));
										%>
										<select id="${typeId}_${valueId}_${atv_loop.index}_${expliciteType}" name="${atv.attributeType.type}" class="form-control">
											<c:forEach items="${values}" var="option">
												<option value="${option.names.localizedStrings[0].characterString}" 
															   ${currentValue == option.names.localizedStrings[0].characterString ? 'selected' : ''}>
													${option.names.localizedStrings[0].characterString}
												</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${expliciteType == 'text' || expliciteType == 'geometry(Geometry)'}">
												<textarea class="form-control" rows="4" id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" 
													name="${atv.attributeType.type}">${currentValue}</textarea>
											</c:when>
											<c:when test="${expliciteType == 'varchar'}">
												<input type="text" class="form-control" id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" 
													name="${atv.attributeType.type}" value="${currentValue}"/>
											</c:when>
											<c:when test="${expliciteType == 'integer'}">
												<input type="number" step="any" class="form-control" id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" 
													name="${atv.attributeType.type}" value="${currentValue}"/>
											</c:when>
											<c:when test="${expliciteType == 'numeric'}">
												<input type="number" step="any" class="form-control" id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" 
													name="${atv.attributeType.type}" value="${currentValue}"/>
											</c:when>
											<c:when test="${expliciteType == 'url'}">
												<input type="url" class="form-control" id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" 
													name="${atv.attributeType.type}" value="${currentValue}"/>
											</c:when>
											<c:when test="${expliciteType == 'date'}">
											    <div class="input-group date">
											      <input type="date" class="form-control" id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}"
											      	  name="${atv.attributeType.type}">
											      <span class="input-group-addon" style="cursor:pointer;">
											      	<i class="glyphicon glyphicon-calendar"></i>
											      </span>
											    </div>
											</c:when>
											<c:when test="${expliciteType == 'boolean'}">
												<select id="${typeId}_${valueId}_${atv_loop.index}_${it.topicInstance.uuid}" name="${atv.attributeType.type}" class="form-control">
													<option value="true" ${currentValue == 'true' ? 'selected' : ''}>
														<fmt:message key="boolean.label.true"/>
													</option>
													<option value="false" ${currentValue == 'false' ? 'selected' : ''}>
														<fmt:message key="boolean.label.false"/>
													</option>
												</select>
											</c:when>
											<c:otherwise>
												<fmt:message key="datatype.unsupported"/> (${expliciteType})
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
    			</table>
			</div>			
		</div>
	</div>
	
	
</c:forEach>

	<div class="input" style="display: none;">
			<p>
					<button type="button" class="btn btn-default" data-toggle="modal" 
							data-target="#modal" data-destination="zenon">
								iDAI.Zenon Modal &ouml;ffnen
					</button> 
					<button type="button" class="btn btn-default" data-toggle="modal" 
							data-target="#modal" data-destination="gazett">
								iDAI.Gazetteer Modal &ouml;ffnen
					</button> 
					<button type="button" class="btn btn-default" data-toggle="modal" 
							data-target="#modal" data-destination="arachne">
								iDAI.Arachne Modal &ouml;ffnen
					</button>
			</p> 					
	</div>
	<hr/>

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
<br/>
</div>

<!-- Accordion: http://bootsnipp.com/snippets/featured/accordion-menu -->
<div class="col-md-4">
		<div id="parents">
			<fmt:message key="please.wait.label"/>
			<img style="width: 40%;" src="${contextPath}/img/url.gif"/>
		</div>
		<br/>
	<div id="sidebar">
		<p>
			<c:set var="currentInstance" value="${it.topicInstance.uuid}"/>
			<fmt:message key="please.wait.label"/>:
			<%=new TopicInstanceAssociationDao(
					UUID.fromString(ProjectDao.getCurrentProject(
							request.getAttribute("javax.servlet.forward.request_uri").toString())),
					OpenInfraSchemas.PROJECTS).getCount(
							UUID.fromString(pageContext.getAttribute("currentInstance").toString()))%>
		</p>
		<img style="width: 40%;" src="${contextPath}/img/url.gif"/>
	</div><!-- end row -->
	<c:if test="${selectGeomType == 'true'}">
		<div>
			<div class="panel panel-default">
				<div class="panel-heading">
			    	<h4 class="panel-title">		                    	
			        	<fmt:message key="setup.geometry.label"/>
			        </h4>
			   	</div>
			   	<div class="panel-collapse collapse in">
			   		<div class="panel-body">
						<form id="test" class="navbar-form navbar-left">
						    <select id="geomType" name="geomType" onchange="submit()" class="form-control">
				          	<% pageContext.setAttribute("geomTypes", AttributeValueGeomType.values(), PageContext.SESSION_SCOPE); %>
					          	<c:forEach items="${geomTypes}" var="item">
									<option value="${item}" ${geomType == item ? 'selected' : ''}>
										<c:out value="${item}"></c:out>
									</option>
					          	</c:forEach>
						    </select>
						    <!-- Additionally, the form control must also include all 
						    	existing parameters as hidden fields -->
						    <c:forEach items="${param}" var="pageParameter">
						    	<!-- Don't add the geomType parameter again! -->
						    	<c:if test="${pageParameter.key != 'geomType'}">
						    		<input type="hidden" name="${pageParameter.key}" value="${pageParameter.value}"/>
						    	</c:if>
				      		</c:forEach>
						</form>
			   		</div>
			   	</div>
			</div>
		</div>	
	</c:if>
</div>
</div>

<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">
			<a href="#" title="Link im neuen Fenster &ouml;ffnen" target="_blank">
				Destination
			</a>
        </h4>
      </div>
      <div class="modal-body">
      <!-- load content dynamically -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Abbrechen</button>
        <button type="button" class="btn btn-primary">Speichern</button>
      </div>
    </div>
  </div>
</div>

<!-- Used for projecting spatial features -->
<script src="//cdnjs.cloudflare.com/ajax/libs/proj4js/1.1.0/proj4js-combined.min.js"></script>
<!-- custom projection definitions that should be supported -->
<script src="${contextPath}/proj/defs.js"></script>
<!-- Mapping library to draw preview map -->
<script src="//cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/OpenLayers.light.js"></script>
<script>
$(document).ready(function() {
	// Get the parameters and load the accordion
	var params = window.location.href.substr(window.location.href.lastIndexOf('?'));
	var url = window.location.href.substr(0,window.location.href.lastIndexOf('/'));
	var ass = url + "/associations" + params;
	var pp = url + "/parents" + params;

	$("#sidebar").load(ass + '#accordion');
	$("#parents").load(pp + '#parents');
	
	// ***** Datepicker *****
	$('.input-group.date').datepicker({
		format: "dd/mm/yyyy",
		language: '<%= pageContext.getAttribute("datepickerI18n").toString() %>'
    });
	
	// set Proj4js lib path to provide custom projections
	Proj4js.libPath = "${contextPath}/proj/";

	// init map preview
	$.ajax({
		url: url + "/topic?geomType=GEOJSON",
        dataType: "json",
		type: "GET",
		cache: false,
	}).done(function(data,status,jqXHR) {		
		data.attributeTypeGroupsToValues.forEach(function(group) {
			group.attributeTypesToValues.forEach(function(entry) {
				var attributeValue = entry.attributeValue,
					uuid, geojson;
					
				if (attributeValue) {
					uuid = attributeValue.uuid;	
				} else {
					return;
				}		
				
				if (attributeValue.attributeValueType == "ATTRIBUTE_VALUE_GEOM") {
					geojson = attributeValue.attributeValueGeom.geom;
				} else if (attributeValue.attributeValueType == "ATTRIBUTE_VALUE_GEOMZ") {
					geojson = attributeValue.attributeValueGeomz.geomz;
				}
				
				if (geojson) {
					initFeaturePreview(data.topicInstance, geojson);
				}
			});
		});
	});
	
	function initFeaturePreview(instance, geojsonStr) {
		var map = new OpenLayers.Map("map", {	
				theme: null,
				projection: "EPSG:900913",							// sperical mercator
				layers: [new OpenLayers.Layer.OSM()], 				// OSM base layer
				controls: [new OpenLayers.Control.Attribution()]
			}), 
			geoJson = JSON.parse(geojsonStr),
			format = new OpenLayers.Format.GeoJSON(),
			layer = new OpenLayers.Layer.Vector('Preview'),
			features = format.read(geoJson),
			crs = 'EPSG:4326';
		
		map.addLayer(layer);
		
		// extract crs from geojson if provided otherwise EPSG:4326 is assumed
		// crs is assumed to be in short notation
		// Important: CRS/SRID needs to be defined as OL projection for transform
		if (geoJson.crs && geoJson.crs.type === 'name') {
			crs = geoJson.crs.properties.name;
		}

		// transfrom to Sperical Mercator (OSM base layer projection)
		features.forEach(function(f) {
			f.geometry.transform(crs, 'EPSG:900913');
		});

        layer.addFeatures(features);
        map.zoomToExtent(layer.getDataExtent());   
        
        // bind click handler to redirect to map
        $('#map').click(function() {
        	var qParams = "?tc=" + instance.topicCharacteristic.uuid + "&ti=" + instance.uuid
        	window.open("${contextPath}/rest/v1/projects/maps" + qParams, "_blank");
        });
	};
	
}); // document ready

// **** Input buttons **** //
$("#edit").click(function() {
	$(".view").hide();
	$(".input").show();
	$('html, body').animate({scrollTop : 0},200);
});

$("#cancel").click(function() {
	$(".input").hide();
	$(".view").show();
});

// Submit the changes
$("#save").click(function() {
	$('#editForm input, #editForm textarea').each(function(){
		
		var id = $(this).attr("id");
		var spanId = "#span_" + id;
		var name = $(this).attr("name");
		var method = "";
		var newId = "";
		var newSpanId = "";
		
		var splitId = $(this).attr("id").split("_");
		
		// Only submit changed values omit unchanged values 
		if($("#" + id).val() != $(spanId).text()) {
			if(splitId[1] != '' && (name == 'ATTRIBUTE_VALUE_VALUE' ||
					name == 'ATTRIBUTE_VALUE_GEOM')) {
				method = "PUT";
			} else if(splitId[1] == '' && (name == 'ATTRIBUTE_VALUE_VALUE' ||
					name == 'ATTRIBUTE_VALUE_GEOM')) {
				method = "POST";
			}
			newId = putOrPostAttributeValue(
					splitId[1], $("#" + id).val(), name, 
					splitId[3], splitId[0], method);
		} // end if
		
		// set the new values
		//if(newId != "") {
			if($(this).attr("id") != null) {
				// set the new value
				$(spanId).text($(this).val());
				// remove grey style if there was no value associated previously
				$(spanId).removeAttr("style");
				
				// two successive underlines signal a new entry (missing attribute value id)
				if(spanId.indexOf("__") != -1) {
					// build the new id: #span_typeId_valueId_loopIndex_topicInstanceId
					newSpanId = "#span_" + splitId[0] + "_" + newId + "_" + splitId[1] + "_" + splitId[2];
				} else {
					newSpanId = "#span_" + splitId[0] + "_" + newId + "_" + splitId[2] + "_" + splitId[3];
				}
				
				// set the new id (TODO currently not working)
				$("#span_" + splitId.join("_")).attr("id", newSpanId);
			} // end if	
			// reset the new id for the next input field
			newId = "";
		//}
	}); // end for each
	
	$(".input").hide();
	$(".view").show();
	
});

function putOrPostAttributeValue(valueId, value, type, topicInstanceId, typeId, method) {
	// build the basic URI
	var basePath = "${contextPath}/rest/projects/${currentProject}/";
	var getUri = "";
	var setUri = "";
	var returnId = "";
	// retrieve the URL parameter
	var parameter = window.location.search.substring(1);
	
	// extend the URI depending on the passed mehtod
	switch (method) {
	case "PUT":
		// create the uri to retrieve the attribute value object that should be updated
		getUri = basePath + "attributevalues/" + valueId;
		// the read and update uri is the same
		setUri = getUri;
		break;
	case "POST":
		// create the uri to write the new attribute value object
		setUri = basePath + "attributevalues";
		// create the uri to retrieve an empty shell of the attribute value object
		getUri = setUri + "/topicinstances/" + topicInstanceId + "/attributetypes/" + typeId + "/hull?" + parameter;
		break;
	default:
		alert(method + " is no supported HTTP method for this function.");
		return "";
	}
	
	$.ajax({
		url: getUri,
	    dataType: "json",
		type: "GET",
		cache: false,
	}).done(function(data,status,jqXHR) {
		// set the data of the retrieved object, depending on the type
		switch (type) {
		case "ATTRIBUTE_VALUE_VALUE":
			data.attributeValueValue.value.localizedStrings[0].characterString = value;
			break;
		case "ATTRIBUTE_VALUE_GEOM":
			data.attributeValueGeom.geom = value;
			break;
		default:
			break;
		}
		
		$.ajax({
			url: setUri,
			type: method,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			cache: false,
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
		          returnId = res.uuid;
			}});

	});
	// TODO: this will be executed before the returnId is set, find a way to insert a delay
	return returnId;
}

// Dai Services
			$('#modal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget); // Button that triggered the modal
			  var link = $(this).find('.modal-title a');

			  switch (button.data('destination')) {
			  case 'gazett':
				  link.text("iDAI.Gazetteer");
				  link.attr("href", "https://gazetteer.dainst.org/");
				  //$(this).find('#iframe').attr("src","html/dai_gazetteer.html");
				  $(this).find('.modal-body').load("${contextPath}/html/dai_gazetteer.html #content",
				 			 function() {
				 				init();
							 });
				break;
			  case 'zenon':
				  link.text("iDAI.Zenon");
				  link.attr("href", "http://zenon.dainst.org/");
				  //$(this).find('#iframe').attr("src","html/dai_zenon.html");
				  $(this).find('.modal-body').load("${contextPath}/html/dai_zenon.html #content",
				   			 function() {
				 				init();
							 });
				break;	
			  case 'arachne':
				  link.text("iDAI.Arachne");
				  link.attr("href", "http://arachne.dainst.org/");
				  //$(this).find('#iframe').attr("src","html/dai_arachne.html");
				  $(this).find('.modal-body').load("${contextPath}/html/dai_arachne.html #content",
				 			 function() {
				 				init();
							 });
				break;
			  }// end switch
			}); // end function 


</script>
</body>
</html>