package de.btu.openinfra.backend.db.daos;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraValueValueDao<
	TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject, TypeModelValue, TypeModelValue2>
	extends OpenInfraValueDao<TypePojo, TypeModel, TypeModelValue> {
	
	protected Class<TypeModelValue2> valueClass2; 
	
	protected OpenInfraValueValueDao(
			UUID currentProjectId,
			OpenInfraSchemas schema,
			Class<TypeModel> modelClass,
			Class<TypeModelValue> valueClass,
			Class<TypeModelValue2> valueClass2) {
		super(currentProjectId, schema, modelClass, valueClass);
		
		this.valueClass2 = valueClass2;
	}
	
	public List<TypePojo> read(
			Locale locales,
			UUID valueId,
			UUID valueId2,
			int offset,
			int size) {
		// 1. Get the specific value objects from JPA layer
		TypeModelValue tmv = em.find(valueClass, valueId);
		TypeModelValue2 tmv2 = em.find(valueClass2, valueId2);
		
		// 2. Define a list which holds the POJO objects
		List<TypePojo> pojos = new LinkedList<TypePojo>();
		// 3. Construct the name of the named query
		String namedQuery = modelClass.getSimpleName()
				+ ".findBy"	+ valueClass.getSimpleName()
				+ "And" + valueClass2.getSimpleName();
		// 4. Retrieve the requested model objects from database
		List<TypeModel> models = em.createNamedQuery(
				namedQuery,
				modelClass)
				.setParameter("value", tmv)
				.setParameter("value2", tmv2)
				.setFirstResult(offset)
				.setMaxResults(size)
				.getResultList();
		// 5. Map the JPA model objects to POJO objects
		for(TypeModel modelItem : models) {
			pojos.add(mapToPojo(locales, modelItem));
		} // end for
		return pojos;
	}
}
