package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.jpa.model.ValueListXValueList;
import de.btu.openinfra.backend.db.pojos.ValueListAssociationPojo;

public class ValueListAssociationDao 
	extends OpenInfraValueDao<ValueListAssociationPojo,
	ValueListXValueList, ValueList> {

	public ValueListAssociationDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, ValueListXValueList.class,
				ValueList.class);
	}

	@Override
	public ValueListAssociationPojo mapToPojo(
			Locale locale,
			ValueListXValueList association) {
		return mapToPojoStatically(locale, association);
	}

	@Override
	public MappingResult<ValueListXValueList> mapToModel(
			ValueListAssociationPojo pojoObject,
			ValueListXValueList modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ValueListAssociationPojo mapToPojoStatically(
			Locale locale,
			ValueListXValueList association) {
		
		if(association != null) {
			ValueListAssociationPojo pojo =
					new ValueListAssociationPojo();
			
			pojo.setUuid(association.getId());
			pojo.setRelationship(ValueListValueDao.mapToPojoStatically(locale,
					association.getValueListValue()));
			pojo.setAssociatedId(association.getValueList2Bean().getId());
			pojo.setValueList(ValueListDao.mapToPojoStatically(locale,
					association.getValueList1Bean()));
			
			return pojo;
		}
		else {
			return null;
		}
	}
}
