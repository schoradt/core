package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class AttributeTypeGroupToTopicCharacteristicPojo extends OpenInfraMetaDataPojo {

    private AttributeTypeGroupPojo attributeTypeGroup;
    private UUID topicCharacteristicId;
    private MultiplicityPojo multiplicity;
    private int order;

    /* Default constructor */
    public AttributeTypeGroupToTopicCharacteristicPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public AttributeTypeGroupToTopicCharacteristicPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public AttributeTypeGroupPojo getAttributeTypeGroup() {
        return attributeTypeGroup;
    }

    public void setAttributeTypeGroup(AttributeTypeGroupPojo attributeTypeGroup) {
        this.attributeTypeGroup = attributeTypeGroup;
    }

    public UUID getTopicCharacteristicId() {
        return topicCharacteristicId;
    }

    public void setTopicCharacteristicId(UUID topicCharacteristicId) {
        this.topicCharacteristicId = topicCharacteristicId;
    }

    public MultiplicityPojo getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityPojo multiplicity) {
        this.multiplicity = multiplicity;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    protected void makePrimerHelper() {
        attributeTypeGroup = new AttributeTypeGroupPojo();
        attributeTypeGroup.makePrimer();
        topicCharacteristicId = null;
        multiplicity = new MultiplicityPojo();
        multiplicity.makePrimer();
        order = -1;
    }

}
