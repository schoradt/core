package de.btu.openinfra.backend.db.daos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This enumeration describes possible input formats for the data type
 * geometry.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@XmlRootElement
public enum AttributeValueGeomWriteType {

	/**
     * The geometry representation of the GeoJSON type
     * (@see http://geojson.org/).
     *
     * Signature to get GeoJSON String with precision of up to 15 decimals and
     * with designated projection identifier in long CRS notation (option 4).
     */
	GEOJSON ("ST_GeomFromGeoJSON");

	private String psqlFnSignature;

	private AttributeValueGeomWriteType(String psqlFnSignature) {
		this.psqlFnSignature = psqlFnSignature;
	}

	public String getPsqlFnSignature() {
		return this.psqlFnSignature;
	}

}
