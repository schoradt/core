package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValueListValuePojo extends OpenInfraPojo {
	
	private boolean visibility;
	private UUID belongsToValueList;
	private NamePojo names;
	private DescriptionPojo descriptions;
	
	public boolean getVisibility() {
		return visibility;
	}
	
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	public UUID getBelongsToValueList() {
		return belongsToValueList;
	}
	
	public void setBelongsToValueList(UUID belongsToValueList) {
		this.belongsToValueList = belongsToValueList;
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
