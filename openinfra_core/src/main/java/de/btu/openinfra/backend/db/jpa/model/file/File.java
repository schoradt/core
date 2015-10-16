package de.btu.openinfra.backend.db.jpa.model.file;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the files database table.
 * 
 */
@Entity
@Table(name="files")
@NamedQuery(name="File.findAll", query="SELECT f FROM File f")
public class File extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

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

}