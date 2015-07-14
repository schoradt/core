package de.btu.openinfra.backend.db.daos;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

/**
 * This class extends the OpenInfraDao class in order to provide another
 * generic method which reads a list of JPA model objects by a given value.
 * Using this class prerequisites a named query of the TypeModel object.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 * @param <TypePojo>
 * @param <TypeModel>
 */
public abstract class OpenInfraValueDao<
	TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject, TypeModelValue> extends
	OpenInfraDao<TypePojo, TypeModel> {

	protected Class<TypeModelValue> valueClass;

	/**
	 * Mandatory constructor method which calls the super constructor.
	 *
	 * @param currentProjectId the current project id
	 * @param schema           the required schema
	 * @param modelClass       a specific class of the JPA model class object
	 * @param valueClass       a specific class of the JPA model value class
	 *                         object
	 */
	protected OpenInfraValueDao(
			UUID currentProjectId,
			OpenInfraSchemas schema,
			Class<TypeModel> modelClass,
			Class<TypeModelValue> valueClass) {
		super(currentProjectId, schema, modelClass);
		this.valueClass = valueClass;
	}

	/**
	 * This is a generic method which reads a list of TypePojos defined by
	 * a specific UUID value (e.g. get a list of objects which belong to a
	 * specific object). This method presumes a corresponding named query of
	 * the TypeModel object.
	 *
	 * @param locale     the locale defined by request
	 * @param valueId    the specific id of the required value object
	 * @param offset     the number where to start
	 * @param size       the size of items to provide
	 * @return           a list of objects of type POJO class which directly
	 *                   belong to the TypeModelValue class
	 */
	public List<TypePojo> read(
			Locale locales,
			UUID valueId,
			int offset,
			int size) {
		// 1. Get the specific value object from JPA layer
		TypeModelValue tmv = em.find(valueClass, valueId);
		// 2. Define a list which holds the POJO objects
		List<TypePojo> pojos = new LinkedList<TypePojo>();
		// 3. Construct the name of the named query
		String namedQuery = modelClass.getSimpleName()
				+ ".findBy"
				+ valueClass.getSimpleName();
		// 4. Retrieve the requested model objects from database
		List<TypeModel> models = em.createNamedQuery(
				namedQuery,
				modelClass)
				.setParameter("value", tmv)
				.setFirstResult(offset)
				.setMaxResults(size)
				.getResultList();
		// 5. Map the JPA model objects to POJO objects
		for(TypeModel modelItem : models) {
			pojos.add(mapToPojo(locales, modelItem));
		} // end for
		return pojos;
	}

	/**
	 * This method returns the count of objects referring a specific object.
	 *
	 * @param valueId the specific object
	 * @return        the count of objects
	 */
	public Long getCount(UUID valueId) {
		// 1. Get the specific value object from JPA layer
		TypeModelValue tmv = em.find(valueClass, valueId);
		// 2. Construct the name of the named query
		String namedQuery = modelClass.getSimpleName()
				+ ".countBy"
				+ valueClass.getSimpleName();
		return em.createNamedQuery(
				namedQuery,
				Long.class)
				.setParameter("value", tmv)
				.getSingleResult()
				.longValue();
	}

}
