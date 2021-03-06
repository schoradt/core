package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.AttributeValueTypes;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class AttributeTypePojo extends OpenInfraPojo {

    private PtFreeTextPojo names;
    private PtFreeTextPojo descriptions;
    private ValueListValuePojo dataType;
    private ValueListValuePojo unit;
    private ValueListPojo domain;
    private AttributeValueTypes type;

    /* Default constructor */
    public AttributeTypePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public AttributeTypePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public AttributeValueTypes getType() {
        return type;
    }

    public void setType(AttributeValueTypes type) {
        this.type = type;
    }

    public PtFreeTextPojo getNames() {
        return names;
    }

    public void setNames(PtFreeTextPojo names) {
        this.names = names;
    }

    public PtFreeTextPojo getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(PtFreeTextPojo descriptions) {
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
