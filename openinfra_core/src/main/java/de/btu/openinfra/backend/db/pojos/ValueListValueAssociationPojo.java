package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class ValueListValueAssociationPojo extends OpenInfraMetaDataPojo {

    private ValueListValuePojo associatedValueListValue;
    private ValueListValuePojo relationship;

    /* Default constructor */
    public ValueListValueAssociationPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public ValueListValueAssociationPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public ValueListValuePojo getAssociatedValueListValue() {
        return associatedValueListValue;
    }

    public void setAssociatedValueListValue(ValueListValuePojo associatedValueListValue) {
        this.associatedValueListValue = associatedValueListValue;
    }

    public ValueListValuePojo getRelationship() {
        return relationship;
    }

    public void setRelationship(ValueListValuePojo relationship) {
        this.relationship = relationship;
    }

    @Override
    protected void makePrimerHelper() {
        associatedValueListValue = new ValueListValuePojo();
        associatedValueListValue.makePrimer();
        relationship = new ValueListValuePojo();
        relationship.makePrimer();
    }

}
