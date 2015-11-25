package de.btu.openinfra.backend.db.pojos.file;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.file.SupportedMimeType;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
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

}
