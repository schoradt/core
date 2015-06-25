package de.btu.openinfra.backend.db.daos;

/**
 * This enumeration entails the possible schemas and can be used to decide
 * which schema should be used.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraSchemas {
	
	/**
	 * This value refers to the system schema.
	 */
	SYSTEM,
	/**
	 * This value refers to all present project schemas.
	 */
	PROJECTS,
	/**
	 * This value refers to the meta data schema
	 */
	META_DATA

}
