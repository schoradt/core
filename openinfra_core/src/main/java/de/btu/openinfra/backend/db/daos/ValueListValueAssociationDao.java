package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.ValueListValue;
import de.btu.openinfra.backend.db.jpa.model.ValueListValuesXValueListValue;
import de.btu.openinfra.backend.db.pojos.ValueListValueAssociationPojo;

public class ValueListValueAssociationDao
	extends OpenInfraValueValueDao<ValueListValueAssociationPojo,
	ValueListValuesXValueListValue, ValueListValue>{

	public ValueListValueAssociationDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, ValueListValuesXValueListValue.class,
				ValueListValue.class);
	}

	@Override
	public ValueListValueAssociationPojo mapToPojo(
			Locale locale,
			ValueListValuesXValueListValue association) {
		return mapToPojoStatically(locale, association);
	}

	@Override
	public MappingResult<ValueListValuesXValueListValue> mapToModel(
			ValueListValueAssociationPojo pojoObject,
			ValueListValuesXValueListValue modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ValueListValueAssociationPojo mapToPojoStatically(
			Locale locale,
			ValueListValuesXValueListValue association) {
		
		if(association != null) {
			ValueListValueAssociationPojo pojo =
					new ValueListValueAssociationPojo();
			
			pojo.setUuid(association.getId());
			pojo.setRelationship(ValueListValueDao.mapToPojoStatically(locale,
					association.getValueListValue1()));
			pojo.setAssociatedId(association.getValueListValue3().getId());
			
			return pojo;
		}
		else {
			return null;
		}
	}
}
