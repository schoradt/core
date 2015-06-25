package de.btu.openinfra.backend.db.pojos;

import java.util.List;
import java.util.UUID;

public class ValuePojo extends PtFreeTextPojo {
	
	public ValuePojo() {}
	
	public ValuePojo(List<LocalizedString> localizedStrings, UUID uuid) {
		this.localizedStrings = localizedStrings;
		this.setUuid(uuid);
	}
		
}
