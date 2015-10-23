package de.btu.openinfra.backend.db.pojos.file;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.jpa.model.file.File;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class FilePojo extends OpenInfraPojo {

	private String mimeType;
	private String originFileName;
	private UUID subject;
	private String uploadedOn;
	private String exifData;
	private String middleDimension;
	private String originDimension;
	private String popupDimension;
	private String thumbnailDimension;
	private String signature;

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

	public String getExifData() {
		return exifData;
	}

	public void setExifData(String exifData) {
		this.exifData = exifData;
	}

	public String getMiddleDimension() {
		return middleDimension;
	}

	public void setMiddleDimension(String middleDimension) {
		this.middleDimension = middleDimension;
	}

	public String getOriginDimension() {
		return originDimension;
	}

	public void setOriginDimension(String originDimension) {
		this.originDimension = originDimension;
	}

	public String getPopupDimension() {
		return popupDimension;
	}

	public void setPopupDimension(String popupDimension) {
		this.popupDimension = popupDimension;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getThumbnailDimension() {
		return thumbnailDimension;
	}

	public void setThumbnailDimension(String thumbnailDimension) {
		this.thumbnailDimension = thumbnailDimension;
	}

	@Override
	protected void makePrimerHelper(PtLocale locale) {
		mimeType = "";
		originFileName = "";
		subject = null;
		uploadedOn = "";
	}
}
