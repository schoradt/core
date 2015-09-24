package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class ValueListAssociationPojo extends OpenInfraMetaDataPojo {

    private ValueListPojo associatedValueList;
    private ValueListValuePojo relationship;

    /* Default constructor */
    public ValueListAssociationPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public ValueListAssociationPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public ValueListPojo getAssociatedValueList() {
        return associatedValueList;
    }

    public void setAssociatedValueList(ValueListPojo associatedValueList) {
        this.associatedValueList = associatedValueList;
    }

    public ValueListValuePojo getRelationship() {
        return relationship;
    }

    public void setRelationship(ValueListValuePojo relationship) {
        this.relationship = relationship;
    }

    @Override
    public void makePrimer() {
        associatedValueList = new ValueListPojo();
        associatedValueList.makePrimer();
        relationship = new ValueListValuePojo();
        relationship.makePrimer();
    }

}
