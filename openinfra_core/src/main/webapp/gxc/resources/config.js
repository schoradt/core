var GXC_ENV = {
  proxy: {
    host: ''
  },
  geoserver: {
    host: 'http://localhost:8081/geoserver'
  },
  "targetId": "gxc-container",
  "viewportItems": [{
    "region": "west",
    "layout": {
      "type": "vbox",
      "pack": "start",
      "align": "stretch"
    },
    "defaults": {
        "autoScroll": true,
        "flex": 1
    }
  }, {
    "region": "center",
    "xtype": "gxc_panel_map",
    "dockedItems": [{
      "xtype": "toolbar",
      "dock": "top",
        "items": ["", {
          "xtype": "gxc_button_viewdelegator",
          "iconCls": "gxc-icon-add",
          "tooltip": "Add Layer",
          "dtype": {
            "title": "Add Layer..",
            "width": 600,
            "height": 500,
            "minWidth": 300,
            "minHeight": 400,
            "layout": 'fit',
            "items": [{
              "xtype": "tabpanel",
                "items": [{
                "xtype": "gxc_panel_add",
                "title": "Service"
              }, {
                "xtype": "gxc_panel_layerfiledrop",
                "title": "File"
              }]
            }]
          }
        }, "-", {
          "xtype": "gxc_button_zoomin"
        }, {
          "xtype": "gxc_button_zoomout"
        }, {
          "xtype": "gxc_button_zoombox"
        }, {
          "xtype": "gxc_button_zoomtomaxextent"
        }, "-", {
          "xtype": "gxc_button_naventry"
        }, {
          "xtype": "gxc_button_navprevious"
        }, {
          "xtype": "gxc_button_navnext"
        }, "-", {
          "xtype": "gxc_button_graticule"
        }, {
          "xtype": "gxc_button_geolocate"
        }, {
          "xtype": "gxc_button_fullscreen"
        }, {
          "xtype": "gxc_button_overviewmap"
        }, "-", {
          "xtype": "gxc_button_featureinfo"
        }, {
          "xtype": "gxc_button_selectfeature"
        }, "-", {
          "xtype": "gxc_button_measurepath"
        }, {
          "xtype": "gxc_button_measurepolygon"
        // }, "-", {
        //   "xtype": "gxc_button_viewdelegator",
        //   "iconCls": "gxc-icon-print",
        //   "tooltip": "Print",
        //   "dtype": {
        //     "xtype": "window",
        //     "layout": "fit",
        //     "width": 300,
        //     "items": [{
        //       "xtype": "gxc_panel_print",
        //       "capabilities": printCapabilities
        //     }]
        //   }
        }, {
          'xtype': 'gxc_button_wmcexport'
        }, {
          'xtype': 'gxc_button_wmcimport'
        }, "->", {
          "xtype": "gxc_form_geocodercombobox",
          "width": 200
        }, ""]
    }, {
      "xtype": "toolbar",
      "dock": "bottom",
      "items": ["", {
        "xtype": "gxc_form_zoomchooser",
        "width": 150
      }, "->", {
        "xtype": "gxc_component_scaleline",
        "width": 150
      }]
    }, {
      "xtype": "gxc_toolbar_notificationbar",
      "itemId": "notificationBar",
      "dock": "bottom"
    }]
  }, {
    "region": "east",
    "title": "Layer",
    "collapsible": true,
    "collapsed": false,
    "width": 250,
    "split": 3,
    "layout": {
      "type": "vbox",
      "pack": "start",
      "align": "stretch"
    },
    "defaults": {
        "autoScroll": true,
        "border": 0,
        "flex": 1
    },
    "items": [{
      "xtype": "gxc_panel_layer"
    }, {
      "xtype": "gxc_panel_legend",
      "title": "Legend",
      "bodyPadding": "5px",
      "collapsible": true
    }]
  }],
  "projections": {
    "EPSG:25832": {
      "def": "+proj=utm +zone=32 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs"
    },
    "EPSG:3857": {
      "def": "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs",
      "maxExtent": "-20026376.39, -20048966.10, 20026376.39, 20048966.10",
      "units": "m"
    },
    "EPSG:31469": {
      "def": "+proj=tmerc +lat_0=0 +lon_0=15 +k=1 +x_0=5500000 +y_0=0 +ellps=bessel +towgs84=598.1,73.7,418.2,0.202,0.045,-2.455,6.7 +units=m +no_defs",
      // fiktive Grenzen -> wird in nächster Version je nach Unit der Projection
      // automatisch gesetzt. Einschränken des Sichtfelds via restictedExtent in mapOptions
      // Sollte für alle "m"-Projektionen funktionieren
      "maxExtent": [-40075016.6784,-40075016.6784,40075016.6784,40075016.6784],
      // WMS 1.3 -> Achsen drehen
      "yx": true
    }
  },
  "mapOptions": {
    // Projection der angefragten Karte
    "projection": "EPSG:4326",
    // Projection der angezeigten Koordinaten
    "displayProjection": "EPSG:4326",
    // Eingeschränkter Kartenbereich für Navigation
    "restrictedExtent": [-180, -90, 180, 90],
    // initiale Position
    // "center": [-100, 40],
    "center": [0, 0],
    // Skalen für Berechnung der Zoomlevel (Slider, Dropdown)
    // Freie Eingabe ist über Benutzeroberfläche ebenfalls möglich
    // -> 1:1
    "minScale": 10,
    // -> 1:100000
    "maxScale": 150000000,
    // Anzahl der Zoomlevel im durch minScale/maxScale bzw. minResolution/maxResolution
    // vorgegebenen Bereich
    "numZoomLevels": 20,
    // Initiales Zoomlevel
    "zoom": 2
    // Alternativ via Resolutions
    //   "minResolution": 300,
    //   "maxResolution": 0.0004,
    //   "numZoomLevels": 20,
    //   "zoom": 2
  },
  "layers": [{
    "url": "http://ows.terrestris.de/osm/service",
    "type": "WMS",
    "version": "1.1.1",
    "layer": "OSM-WMS"
  }],
  "services": [{
    "type": "WMS",
    "title": "OSM",
    "url": "http://ows.terrestris.de/osm/service"
  }, {
    "type": "WMS",
    "title": "Projekt Baalbek",
    "url": "http://141.56.141.17/geoserver/baalbek/wms"
  }, {
      "type": "WMS",
      "title": "Projekt Baalbek 3D",
      "url": "http://141.56.141.17/geoserver/ddd/wms"
  }, {
    "type": "WMS",
    "title": "localhost (dev)",
    "url": "http://localhost:8080/geoserver/wms"
  }, {
    "type": "WFS",
    "title": "localhost (dev) WFS",
    "version": "1.3.0",
    "url": "http://localhost:8080/geoserver/ows"
  }, {
    "type": "WMS",
    "title": "Landesvermessung Sachsen",
    "version": "1.1.1",
    "url": "http://www.landesvermessung.sachsen.de/ias/basiskarte4/service/SRV4TOPSN/WMSFREE_TK/wmsservice"
  }, {
    "type": "WMS",
    "title": "Landesvermessung Sachsen Festpunkte",
    "version": "1.1.1",
    "url": "http://www.landesvermessung.sachsen.de/ias/basiskarte4/service/SRXFESTPKT/WMSFREE_/wmsservice"
  }, {
    "type": "WMS",
    "title": "Regionaler Planungsverband Oberlausitz-Niederschlesien",
    "version": "1.1.1",
    "url": "http://web1.extranet.sachsen.de/geoservice/rp-on"
  }, {
    "type": "WMS",
    "title": "WMS Digitaler Orthofotos Sachsen (RGB)",
    "version": "1.3.0",
    "url": "https://geodienste.sachsen.de/wms_geosn_dop-rgb/guest"
  }, {
    "type": "WMS",
    "title": "WMS Digitaler Orthofotos Sachsen (CIR)",
    "version": "1.3.0",
    "url": "https://geodienste.sachsen.de/wms_geosn_dop-cir/guest"
  }, {
    "type": "WMS",
    "title": "WMS Digitaler Orthofotos Sachsen (PAN)",
    "version": "1.3.0",
    "url": "https://geodienste.sachsen.de/wms_geosn_dop-pan/guest"
  }, {
    "type": "WFS",
    "title": "Naturschutz Rheinland Pfalz",
    "version": "1.0.0",
    "url": "http://map1.naturschutz.rlp.de/service_lanis/mod_wfs/wfs_getmap.php?mapfile=naturpark"
  }]
};
