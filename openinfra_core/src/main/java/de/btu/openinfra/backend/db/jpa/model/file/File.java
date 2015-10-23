package de.btu.openinfra.backend.db.jpa.model.file;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the files database table.
 *
 */
@Entity
@Table(name="files")
@NamedQueries({
	@NamedQuery(name="File.findAll", query="SELECT f FROM File f"),
	@NamedQuery(name="File.count", query="SELECT COUNT(f) FROM File f"),
	@NamedQuery(name="File.findBySubject",
		query="SELECT f "
				+ "FROM File f "
				+ "WHERE f.subject = :subject"),
	@NamedQuery(name="File.countBySubject",
		query="SELECT COUNT(f) "
				+ "FROM File f "
				+ "WHERE f.subject = :subject")
})
public class File extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="exif_data")
	private String exifData;

	@Column(name="middle_dimension")
	private String middleDimension;

	@Column(name="origin_dimension")
	private String originDimension;

	@Column(name="popup_dimension")
	private String popupDimension;

	@Column(name="thumbnail_dimension")
	private String thumbnailDimension;

	private String signature;

	@Column(name="mime_type")
	private String mimeType;

	@Column(name="origin_file_name")
	private String originFileName;

	private UUID subject;

	@Column(name="uploaded_on")
	private Timestamp uploadedOn;

	public File() {
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getOriginFileName() {
		return this.originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public UUID getSubject() {
		return this.subject;
	}

	public void setSubject(UUID subject) {
		this.subject = subject;
	}

	public Timestamp getUploadedOn() {
		return this.uploadedOn;
	}

	public void setUploadedOn(Timestamp uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getExifData() {
		return this.exifData;
	}

	public void setExifData(String exifData) {
		this.exifData = exifData;
	}

	public String getMiddleDimension() {
		return this.middleDimension;
	}

	public void setMiddleDimension(String middleDimension) {
		this.middleDimension = middleDimension;
	}

	public String getOriginDimension() {
		return this.originDimension;
	}

	public void setOriginDimension(String originDimension) {
		this.originDimension = originDimension;
	}

	public String getPopupDimension() {
		return this.popupDimension;
	}

	public void setPopupDimension(String popupDimension) {
		this.popupDimension = popupDimension;
	}

	public String getSignature() {
		return this.signature;
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

}