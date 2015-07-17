package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TopicCharacteristicPojo extends OpenInfraPojo {
	
	private PtFreeTextPojo descriptions;
	private ValueListValuePojo topic;
	private String projectId;
	private List<String> settings;

	public List<String> getSettings() {
		return settings;
	}

	public void setSettings(List<String> settings) {
		this.settings = settings;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
