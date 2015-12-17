package de.btu.openinfra.backend.db.jpa.model.file;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the supported_mime_types database table.
 *
 */
@Entity
@Table(name="supported_mime_types")
@NamedQueries({
	@NamedQuery(name="SupportedMimeType.count",
			query="SELECT COUNT(s) FROM SupportedMimeType s"),
	@NamedQuery(name="SupportedMimeType.findAll",
			query="SELECT s FROM SupportedMimeType s ORDER BY s.id")
})
public class SupportedMimeType
	extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="mime_type")
	private String mimeType;

	public SupportedMimeType() {
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}