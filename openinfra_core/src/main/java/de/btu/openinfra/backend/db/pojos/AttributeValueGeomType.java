package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This enumeration describes possible output formats for the data type 
 * geometry.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@XmlRootElement
public enum AttributeValueGeomType {
	
	/**
	 * Well-known text (WTK) representation of the geometry type
	 * (@see http://en.wikipedia.org/wiki/Well-known_text).
	 */
	TEXT,
	/**
	 * The Keyhole Markup Language (KML) representation of the geometry type 
	 * (@see http://de.wikipedia.org/wiki/Keyhole_Markup_Language).
	 */
	KML,
	/**
	 * The Geography Markup Language (GML) representation of the geometry type 
	 * (@see http://de.wikipedia.org/wiki/Geography_Markup_Language).
	 */
	GML,
	/**
	 * The GeoJSON representation of the geometry type 
	 * (@see http://geojson.org/).
	 */
	GEOJSON,
	/**
	 * The X3D representation of the geometry type 
	 * (@see http://de.wikipedia.org/wiki/X3D).
	 */
	X3D

}
