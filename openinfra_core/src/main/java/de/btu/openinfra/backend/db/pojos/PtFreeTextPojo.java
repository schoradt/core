package de.btu.openinfra.backend.db.pojos;

import java.util.List;

public abstract class PtFreeTextPojo extends OpenInfraPojo {
	
	protected List<LocalizedString> localizedStrings;
	
	public List<LocalizedString> getLocalizedStrings() {
		return localizedStrings;
	}

	public void setLocalizedStrings(List<LocalizedString> localizedStrings) {
		this.localizedStrings = localizedStrings;
	}

}
