package de.btu.openinfra.backend.db.pojos;

import java.util.List;
import java.util.UUID;

public class NamePojo extends PtFreeTextPojo {
	
	/**
	 * Default non-argument constructor necessary for jaxb.
	 */
	public NamePojo() {}
	
	public NamePojo(List<LocalizedString> localizedStrings, UUID uuid) {
		this.localizedStrings = localizedStrings;
		this.setUuid(uuid);
	}
	
}
