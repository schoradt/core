package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the character_code database table.
 * 
 */
@Entity
@Table(name="character_code")

@NamedQueries({
    @NamedQuery(name="CharacterCode.findAll",
            query="SELECT c FROM CharacterCode c"),
    @NamedQuery(name="CharacterCode.count",
            query="SELECT COUNT(c) FROM CharacterCode c ")
})
public class CharacterCode implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	@Column(name="character_code")
	private String characterCode;

	//bi-directional many-to-one association to PtLocale
	@OneToMany(mappedBy="characterCode")
	private List<PtLocale> ptLocales;

	public CharacterCode() {
	}

	@Override
    public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getCharacterCode() {
		return this.characterCode;
	}

	public void setCharacterCode(String characterCode) {
		this.characterCode = characterCode;
	}

	public List<PtLocale> getPtLocales() {
		return this.ptLocales;
	}

	public void setPtLocales(List<PtLocale> ptLocales) {
		this.ptLocales = ptLocales;
	}

	public PtLocale addPtLocale(PtLocale ptLocale) {
		getPtLocales().add(ptLocale);
		ptLocale.setCharacterCode(this);

		return ptLocale;
	}

	public PtLocale removePtLocale(PtLocale ptLocale) {
		getPtLocales().remove(ptLocale);
		ptLocale.setCharacterCode(null);

		return ptLocale;
	}

}