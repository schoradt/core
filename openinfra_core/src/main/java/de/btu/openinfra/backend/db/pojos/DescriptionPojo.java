package de.btu.openinfra.backend.db.pojos;

import java.util.List;
import java.util.UUID;

public class DescriptionPojo extends PtFreeTextPojo {
	
	/**
	 * Default non-arg constructor necessary for jaxb.
	 */
	public DescriptionPojo() {}
	
	public DescriptionPojo(List<LocalizedString> localizedStrings, UUID uuid) {
		this.localizedStrings = localizedStrings;
		this.setUuid(uuid);
	}
	
}
