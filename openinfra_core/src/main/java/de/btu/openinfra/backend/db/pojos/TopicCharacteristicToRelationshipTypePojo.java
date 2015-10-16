package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class TopicCharacteristicToRelationshipTypePojo extends OpenInfraMetaDataPojo {

    private UUID relationshipType;
    private MultiplicityPojo multiplicity;
    private TopicCharacteristicPojo topicCharacteristic;

    /* Default constructor */
    public TopicCharacteristicToRelationshipTypePojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public TopicCharacteristicToRelationshipTypePojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public UUID getRelationshipe() {
        return relationshipType;
    }

    public void setRelationshipe(UUID relationShipe) {
        this.relationshipType = relationShipe;
    }

    public MultiplicityPojo getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityPojo multiplicity) {
        this.multiplicity = multiplicity;
    }

    public TopicCharacteristicPojo getTopicCharacteristic() {
        return topicCharacteristic;
    }

    public void setTopicCharacteristic(TopicCharacteristicPojo topicCharacteristic) {
        this.topicCharacteristic = topicCharacteristic;
    }

    @Override
    protected void makePrimerHelper() {
        relationshipType = null;
        multiplicity = new MultiplicityPojo();
        multiplicity.makePrimer();
        topicCharacteristic = new TopicCharacteristicPojo();
        topicCharacteristic.makePrimer();
    }

}
