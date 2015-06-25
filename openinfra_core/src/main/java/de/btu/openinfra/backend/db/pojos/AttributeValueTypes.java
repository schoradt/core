package de.btu.openinfra.backend.db.pojos;

/**
 * A set of attribute value types defined by the database schema definition.
 * Upper case letters are use since these types are constants. 
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum AttributeValueTypes {
	
	/**
	 * This type defines a value value.
	 */
	ATTRIBUTE_VALUE_VALUE,
	/**
	 * The type defines a domain value.
	 */
	ATTRIBUTE_VALUE_DOMAIN,
	/**
	 * This type defines a geometry value.
	 */
	ATTRIBUTE_VALUE_GEOM,
	/**
	 * This type defines a 3D (including the z-axis) geometry value.
	 */
	ATTRIBUTE_VALUE_GEOMZ

}
