package de.btu.openinfra.backend.db.rbac;

/**
 * An enumeration which holds ordinary HTTP methods and the corresponding access
 * string.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraHttpMethod {
	
	GET("r"),
	PUT("r,w"),
	POST("r,w"),
	DELETE("r,w");
	
	private String access;
	
	private OpenInfraHttpMethod(String access) {
		this.access = access;
	}
	
	public String getAccess() {
		return this.access;
	}

}
