package de.btu.openinfra.backend.db.pojos.file;

import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.jpa.model.file.File;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public class FilePojo extends OpenInfraPojo {

	private String mimeType;
	private String originFileName;
	private UUID subject;
	private String uploadedOn;

	public FilePojo() {}

	public FilePojo(File model) {
		super(model);
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public UUID getSubject() {
		return subject;
	}

	public void setSubject(UUID subject) {
		this.subject = subject;
	}

	public String getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(String uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	@Override
	protected void makePrimerHelper(PtLocale locale) {
		mimeType = "";
		originFileName = "";
		subject = null;
		uploadedOn = "";
	}
}
