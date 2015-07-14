package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TopicInstanceParentPojo {
	
	private TopicInstancePojo parent;
	
	public TopicInstanceParentPojo() {}
	public TopicInstanceParentPojo(TopicInstancePojo parent) {
		this.parent = parent;
	}

	public TopicInstancePojo getParent() {
		return parent;
	}

	public void setParent(TopicInstancePojo parent) {
		this.parent = parent;
	}

}
