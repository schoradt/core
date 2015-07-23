<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../snippets/Head.jsp" %>
	<title>OpenInfRA <fmt:message key="system.label"/></title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/theme/default/style.css">
</head>
<body>
	<%@ include file="../snippets/Menu.jsp" %>
	
	<!-- base container of gxc app -->
	<!-- styles are namespaced to this container -->
	<div id="gxc-container" class="container"></div>
		
    <!-- GeoServer print information -->
    <!-- <script src="http://localhost:8080/geoserver/pdf/info.json?var=printCapabilities"></script> -->
    <!-- gxc configuration file -->
    <script src="${contextPath}/gxc/resources/config.js"></script> 
    <!-- JS Promise support -->   
    <script src="//cdnjs.cloudflare.com/ajax/libs/q.js/1.2.0/q.min.js"></script>
    <!-- Blob shim -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2014-11-29/FileSaver.min.js"></script>
    <!-- vector projection -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/proj4js/1.1.0/proj4js-combined.min.js"></script>
    <!-- mapping framework -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/OpenLayers.js"></script>
	<!-- gxc app styling -->
	<link rel="stylesheet" href="${contextPath}/gxc/resources/GXCFull-all.css"/>
	
	<!-- simplistic approach to map tc params to actual geoserver layers -->
	<script type="text/javascript" src="${contextPath}/gxc/app.js"></script>
	<script>
    var config = GXC_ENV,    	
    	params = Ext.urlDecode(location.search, true),    	
    	layers = [], 
    	p = params.p, 
    	tcs, ti;
    
 	// set proxy host
    config.proxy.host = "${contextPath}/gxc/resources/proxy.jsp?";
    
    // map tc param to array
    if (params.tc) {
    	if (Ext.isArray(params.tc)) {
    		tcs = params.tc;
    	} else {
    		tcs = [params.tc];
    	}
    }
        
 	// Each topic  charactersitic mapped to wms layer of baalbek project.
 	// The namespace "baalbek:" is derieved from the geoserver workspace
 	// the layers reside in.
   	Ext.Array.each(tcs, function(tc, index) {
   		var layerConfig = {
   			type: 'WMS',
   			version: '1.1.1',
   			url: config.geoserver.host + '/wms?',
   			layer: 'baalbek:geom_' + tc
   		};
   		
   		// if topic instance id is given, first layer of
   		// tcs array will have a preselected feature on map init
   		if (index === 0 && params.ti) {
   			Ext.apply(layerConfig, {
   				select: {
   					featureId: params.ti
   				}
   			});
   		}
   		
   		layers.push(layerConfig);
   	});
    
    // unshift layers into config array
    config.layers.unshift.apply(config.layers, layers);
    </script>	
</body>
</html>