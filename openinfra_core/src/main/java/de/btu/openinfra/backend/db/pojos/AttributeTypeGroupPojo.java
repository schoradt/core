package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttributeTypeGroupPojo extends OpenInfraPojo {
	
	// Fields of the object AttributeTypeGroup
	private NamePojo names;
	private DescriptionPojo descriptions;
	private UUID subgroupOf;
	
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

	public UUID getSubgroupOf() {
		return subgroupOf;
	}
	
	public void setSubgroupOf(UUID subgroupOf) {
		this.subgroupOf = subgroupOf;
	}

}
