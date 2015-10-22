package de.btu.openinfra.backend.db.pojos.file;

import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.jpa.model.file.SupportedMimeType;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public class SupportedMimeTypePojo extends OpenInfraPojo {

	private String mimeType;

	public SupportedMimeTypePojo() {}

	public SupportedMimeTypePojo(SupportedMimeType model) {
		super(model);
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	protected void makePrimerHelper(PtLocale locale) {
		mimeType = "";
	}

}
