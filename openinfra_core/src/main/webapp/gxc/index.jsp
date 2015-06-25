<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/theme/default/style.css"/>
    <style type="text/css">
    html {
      height: 100%;
      margin: 0;
      padding: 0;
    }

    body {
      margin: 0;
      padding: 0;
      height: 100%;
    }

    #gxc-client-viewport {
      position: relative;
      height: 100%;
      min-height: 500px;
      width: 90%;
      min-width: 860px;
      margin: auto;
    }

    #gxc-client-viewport:-webkit-full-screen {
      width: 100%;
      height: 100%;
    }

    #gxc-client-viewport:-moz-full-screen {
      width: 100%;
      height: 100%;
    }
    </style>
    <title>HTW</title>
</head>
<body>
    <div id="gxc-client-viewport"></div>
    <!-- GeoServer print information -->
    <script src="http://141.56.141.17/geoserver/pdf/info.json?var=printCapabilities"></script>
    <!-- Application configuration file -->
    <script src="./resources/config.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/q.js/1.2.0/q.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2014-11-29/FileSaver.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/proj4js/1.1.0/proj4js-combined.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/OpenLayers.debug.js"></script>
    <link rel="stylesheet" href="resources/HTW-all.css"/>
    <script type="text/javascript" src="app.js"></script>

<!-- Load locale -->
<%
  String lang = request.getParameter("lang");
  if (lang != null) {
    out.println("<script src=\"./resources/GXC/locale/ext-lang-"+lang+".js\"></script>");
    out.println("<script src=\"./resources/GXC/locale/gxc-lang-"+lang+".js\"></script>");
  }
%>

</body>
</html>
