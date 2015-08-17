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

	public void setId(UUID id);
	public UUID getId();
	public Integer getXmin();

}
