package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the localized_character_string database table.
 * 
 */
@Embeddable
public class LocalizedCharacterStringPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pt_free_text_id", insertable=false, updatable=false)
	private String ptFreeTextId;

	@Column(name="pt_locale_id", insertable=false, updatable=false)
	private String ptLocaleId;

	public LocalizedCharacterStringPK() {
	}
	public String getPtFreeTextId() {
		return this.ptFreeTextId;
	}
	public void setPtFreeTextId(String ptFreeTextId) {
		this.ptFreeTextId = ptFreeTextId;
	}
	public String getPtLocaleId() {
		return this.ptLocaleId;
	}
	public void setPtLocaleId(String ptLocaleId) {
		this.ptLocaleId = ptLocaleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LocalizedCharacterStringPK)) {
			return false;
		}
		LocalizedCharacterStringPK castOther = (LocalizedCharacterStringPK)other;
		return 
			this.ptFreeTextId.equals(castOther.ptFreeTextId)
			&& this.ptLocaleId.equals(castOther.ptLocaleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ptFreeTextId.hashCode();
		hash = hash * prime + this.ptLocaleId.hashCode();
		
		return hash;
	}
}