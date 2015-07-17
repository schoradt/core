package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeXAttributeType;
import de.btu.openinfra.backend.db.pojos.AttributeTypeAssociationPojo;

public class AttributeTypeAssociationDao
	extends OpenInfraValueValueDao<AttributeTypeAssociationPojo,
	AttributeTypeXAttributeType, AttributeType, AttributeType> {

	public AttributeTypeAssociationDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeTypeXAttributeType.class,
				AttributeType.class, AttributeType.class);
	}

	@Override
	public AttributeTypeAssociationPojo mapToPojo(
			Locale locale,
			AttributeTypeXAttributeType association) {
		return mapToPojoStatically(locale, association);
	}

	@Override
	public MappingResult<AttributeTypeXAttributeType> mapToModel(
			AttributeTypeAssociationPojo pojoObject,
			AttributeTypeXAttributeType modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static AttributeTypeAssociationPojo mapToPojoStatically(
			Locale locale,
			AttributeTypeXAttributeType association) {
		
		if(association != null) {
			AttributeTypeAssociationPojo pojo =
					new AttributeTypeAssociationPojo();
			
			pojo.setUuid(association.getId());
			pojo.setRelationship(ValueListValueDao.mapToPojoStatically(locale,
					association.getValueListValue()));
			pojo.setAssociatedAttributeType(
				AttributeTypeDao.mapToPojoStatically(locale,
				association.getAttributeType2Bean()));
			
			return pojo;
		}
		else {
			return null;
		}
	}

}
