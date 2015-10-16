package de.btu.openinfra.backend;

/**
 * This enumeration defines the specific database variables. Keys are defined
 * in another enumeration. {@see OpenInfraPropertyKeys}
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraPropertyValues {
	
	/**
	 * This variable defines the specific JDBC driver.
	 */
	JDBC_DRIVER("org.postgresql.Driver"),

	/**
	 * This variable defines the connection URL which should be formatted via
	 * String.format()
	 */
	URL("jdbc:postgresql://%s:%s/%s?"),
	
	/**
	 * The definition of the search path for the PostgreSQL data base.
	 */
	SEARCH_PATH("public,constraints"),
	
	/**
	 * The static search path for the system schema.
	 */
	SYSTEM_SEARCH_PATH("system"),
	
	/**
	 * The static search path for the meta data schema
	 */
	META_DATA_SEARCH_PATH("meta_data"),
	
	/**
	 * The static search path for the role-based access control
	 */
	RBAC_SEARCH_PATH("rbac"),
	
	/**
	 * The static search path for the file service
	 */
	FILE_SEARCH_PATH("file"),	
	
	/**
	 * The path for the project data.
	 */
	PROJECTDATA_PATH(OpenInfraProperties.FILE_PATH + "projectdata/"),
	
	/**
	 * The path to the upload folder.
	 */
	UPLOAD_PATH(OpenInfraPropertyValues.PROJECTDATA_PATH.getValue()  
			+ "upload/"),
			
	/**
	 * The path to the image folder.
	 */
	IMAGE_PATH(OpenInfraPropertyValues.UPLOAD_PATH.getValue() + "images/"),
	
	/**
	 * The path to the small images folder.
	 */
	IMAGE_SMALL_PATH(OpenInfraPropertyValues.IMAGE_PATH.getValue() + "small/"),

	/**
	 * The path to the middle images folder.
	 */
	IMAGE_MIDDLE_PATH(
			OpenInfraPropertyValues.IMAGE_PATH.getValue() + "middle/"),
	
	/**
	 * The path to the file folder.
	 */
	FILE_PATH(OpenInfraPropertyValues.UPLOAD_PATH.getValue() + "files/");

	
	private String value;
	private OpenInfraPropertyValues(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
