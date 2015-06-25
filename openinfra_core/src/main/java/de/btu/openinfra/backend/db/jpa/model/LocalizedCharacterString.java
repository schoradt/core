package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the localized_character_string database table.
 * 
 */
@Entity
@Table(name="localized_character_string")
@NamedQuery(name="LocalizedCharacterString.findAll", 
		query="SELECT l FROM LocalizedCharacterString l")

public class LocalizedCharacterString implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocalizedCharacterStringPK id;

	@Column(name="free_text")
	private String freeText;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne
	@JoinColumn(name="pt_free_text_id")
	private PtFreeText ptFreeText;

	//bi-directional many-to-one association to PtLocale
	@ManyToOne
	@JoinColumn(name="pt_locale_id")
	private PtLocale ptLocale;

	public LocalizedCharacterString() {
	}

	public LocalizedCharacterStringPK getId() {
		return this.id;
	}

	public void setId(LocalizedCharacterStringPK id) {
		this.id = id;
	}

	public String getFreeText() {
		return this.freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public PtFreeText getPtFreeText() {
		return this.ptFreeText;
	}

	public void setPtFreeText(PtFreeText ptFreeText) {
		this.ptFreeText = ptFreeText;
	}

	public PtLocale getPtLocale() {
		return this.ptLocale;
	}

	public void setPtLocale(PtLocale ptLocale) {
		this.ptLocale = ptLocale;
	}

}