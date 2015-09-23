package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Object;
import de.btu.openinfra.backend.db.pojos.rbac.ObjectPojo;

public class ObjectDao extends OpenInfraDao<ObjectPojo, Object> {

	protected ObjectDao() {
		super(null, OpenInfraSchemas.RBAC, Object.class);
	}

	@Override
	public ObjectPojo mapToPojo(Locale locale, Object modelObject) {
		return mapToPojoStatically(locale, modelObject);
	}
	
	public static ObjectPojo mapToPojoStatically(
			Locale locale, Object modelObject) {
		ObjectPojo pojo = new ObjectPojo(modelObject);
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<Object> mapToModel(ObjectPojo pojoObject,
			Object modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
