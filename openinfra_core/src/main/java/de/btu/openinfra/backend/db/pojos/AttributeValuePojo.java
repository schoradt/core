package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttributeValuePojo extends OpenInfraPojo {

	private UUID attributeTypeId;
	private AttributeValueTypes attributeValueType;
	private AttributeValueDomainPojo attributeValueDomain;
	private AttributeValueGeomPojo attributeValueGeom;
	private AttributeValueGeomzPojo attributeValueGeomz;
	private AttributeValueValuePojo attributeValueValue;
	
	public AttributeValueDomainPojo getAttributeValueDomain() {
		return attributeValueDomain;
	}

	public void setAttributeValueDomain(
			AttributeValueDomainPojo attributeValueDomain) {
		this.attributeValueDomain = attributeValueDomain;
	}

	public AttributeValueGeomPojo getAttributeValueGeom() {
		return attributeValueGeom;
	}

	public void setAttributeValueGeom(AttributeValueGeomPojo attributeValueGeom) {
		this.attributeValueGeom = attributeValueGeom;
	}

	public AttributeValueGeomzPojo getAttributeValueGeomz() {
		return attributeValueGeomz;
	}

	public void setAttributeValueGeomz(AttributeValueGeomzPojo attributeValueGeomz) {
		this.attributeValueGeomz = attributeValueGeomz;
	}

	public AttributeValueValuePojo getAttributeValueValue() {
		return attributeValueValue;
	}

	public void setAttributeValueValue(AttributeValueValuePojo attributeValueValue) {
		this.attributeValueValue = attributeValueValue;
	}

	public UUID getAttributeTypeId() {
		return attributeTypeId;
	}

	public void setAttributeTypeId(UUID attributeTypeId) {
		this.attributeTypeId = attributeTypeId;
	}

	public AttributeValueTypes getAttributeValueType() {
		return attributeValueType;
	}

	public void setAttributeValueType(AttributeValueTypes attributeValueType) {
		this.attributeValueType = attributeValueType;
	}

}
