package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValueListPojo extends OpenInfraPojo {
	
	private NamePojo names;
	private DescriptionPojo descriptions;
	
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
