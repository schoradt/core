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
	TEXT	("ST_asText(geom)"),
	/**
	 * The Keyhole Markup Language (KML) representation of the geometry type 
	 * (@see http://de.wikipedia.org/wiki/Keyhole_Markup_Language).
	 */
	KML		("ST_asKML(geom)"),
	/**
	 * The Geography Markup Language (GML) representation of the geometry type 
	 * (@see http://de.wikipedia.org/wiki/Geography_Markup_Language).
	 */
	GML		("ST_asGML(geom)"),
	/**
	 * The GeoJSON representation of the geometry type 
	 * (@see http://geojson.org/).
	 *
	 * Signature to get GeoJSON String with precision of up to 15 decimals and
	 * with designated projection identifier in long CRS notation (option 4).
	 */
	GEOJSON	("ST_asGeoJSON(geom, 15, 3)"),
	/**
	 * The X3D representation of the geometry type 
	 * (@see http://de.wikipedia.org/wiki/X3D).
	 */
	X3D		("ST_asX3D(geom)");

	private String psqlFnSignature;

	private AttributeValueGeomType(String psqlFnSignature) {
		this.psqlFnSignature = psqlFnSignature;
	}

	public String getPsqlFnSignature() {
		return this.psqlFnSignature;
	}

}
