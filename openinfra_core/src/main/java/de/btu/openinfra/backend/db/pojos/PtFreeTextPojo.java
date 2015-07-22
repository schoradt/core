package de.btu.openinfra.backend.db.pojos;

import java.util.List;
import java.util.UUID;

public class PtFreeTextPojo extends OpenInfraPojo {
	
	protected List<LocalizedString> localizedStrings;
	
	public PtFreeTextPojo() {}
	
	public PtFreeTextPojo(List<LocalizedString> ls, UUID id) {
		this.setUuid(id);
		this.localizedStrings = ls;
	}
	
	public List<LocalizedString> getLocalizedStrings() {
		return localizedStrings;
	}

	public void setLocalizedStrings(List<LocalizedString> localizedStrings) {
		this.localizedStrings = localizedStrings;
	}

}
