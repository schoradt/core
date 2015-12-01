package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class TopicCharacteristicPojo extends OpenInfraPojo {

    private PtFreeTextPojo descriptions;
    private ValueListValuePojo topic;
    private UUID projectId;
    private long topicInstancesCount;

    /* Default constructor */
    public TopicCharacteristicPojo() {}

    /* Constructor that will set the id and trid automatically */
    public TopicCharacteristicPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public PtFreeTextPojo getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(PtFreeTextPojo descriptions) {
        this.descriptions = descriptions;
    }

    public ValueListValuePojo getTopic() {
        return topic;
    }

    public void setTopic(ValueListValuePojo topic) {
        this.topic = topic;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

	public long getTopicInstancesCount() {
		return topicInstancesCount;
	}

	public void setTopicInstancesCount(long topicInstancesCount) {
		this.topicInstancesCount = topicInstancesCount;
	}

}
