package de.btu.openinfra.backend.db.jpa.model;

import java.util.UUID;

/**
 * This interface is manually added to the set of automatically generated model
 * classes by JPA/EclipseLink. This interface intends to provide a simple
 * solution for providing setter methods for model classes without invoking side
 * effects to the generated classes.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public interface OpenInfraModelObject {

	/**
	 * This method represents the UUID of the current object.
	 * @param id UUID of the current project
	 */
	public void setId(UUID id);
	
	/**
	 * This method represents the UUID of the current object.
	 * @return UUID of the current project
	 */
	public UUID getId();
	
	/**
	 * The xmin value is part of a PostgreSQL system column and defines a
	 * transaction id as integer. The integer value represents the last 
	 * transaction id and might be used to detect database changes. 
	 *  
	 * @return transaction id as integer
	 */
	public Integer getXmin();

}
