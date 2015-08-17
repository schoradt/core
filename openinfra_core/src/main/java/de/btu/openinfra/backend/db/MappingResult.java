package de.btu.openinfra.backend.db;

import java.util.UUID;

/**
 * This class represents a result which is created when an POJO object is
 * mapped to a JPA model object. 
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 * @param <TypeModel> the JPA model type
 */
public class MappingResult<TypeModel> {

	/**
	 * This variable represents the id which was newly created or was taken
	 * from an existing object.
	 */
	private UUID id;
	/**
	 * This variable holds the resulting JPA model object. 
	 */
	private TypeModel modelObject;
	
	/**
	 * Default non-argument constructor
	 */
	public MappingResult() {}
	
	/**
	 * Argument based constructor
	 * 
	 * @param id          id of the created or replaced object
	 * @param modelObject the specific object
	 */
	public MappingResult(UUID id, TypeModel modelObject) {
		this.id = id;
		this.modelObject = modelObject;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public TypeModel getModelObject() {
		return modelObject;
	}
	
	public void setModelObject(TypeModel modelObject) {
		this.modelObject = modelObject;
	}
}
