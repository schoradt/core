package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttributeTypePojo extends OpenInfraPojo {
	
	private NamePojo names;
	private DescriptionPojo descriptions;
	private ValueListValuePojo dataType;
	private ValueListValuePojo unit;
	private ValueListPojo domain;
	private AttributeValueTypes type;

	public AttributeValueTypes getType() {
		return type;
	}

	public void setType(AttributeValueTypes type) {
		this.type = type;
	}

	public NamePojo getNames() {
		return names;
	}

	public void setNames(NamePojo names) {
		this.names = names;
	}

	public DescriptionPojo getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(DescriptionPojo descriptions) {
		this.descriptions = descriptions;
	}

	public ValueListValuePojo getDataType() {
		return dataType;
	}
	
	public void setDataType(ValueListValuePojo dataType) {
		this.dataType = dataType;
	}
	
	public ValueListValuePojo getUnit() {
		return unit;
	}
	
	public void setUnit(ValueListValuePojo unit) {
		this.unit = unit;
	}
	
	public ValueListPojo getDomain() {
		return domain;
	}
	
	public void setDomain(ValueListPojo domain) {
		this.domain = domain;
	}	

}
