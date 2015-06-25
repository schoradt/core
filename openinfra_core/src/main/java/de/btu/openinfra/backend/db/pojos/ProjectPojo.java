package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectPojo extends OpenInfraPojo {
	
	private UUID subprojectOf;
	private NamePojo names;
	private DescriptionPojo descriptions;
	
	public UUID getSubprojectOf() {
		return subprojectOf;
	}
	
	public void setSubprojectOf(UUID subprojectOf) {
		this.subprojectOf = subprojectOf;
	}

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

}
