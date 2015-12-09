package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
            query="SELECT COUNT(c) FROM CharacterCode c "),
    @NamedQuery(name="CharacterCode.findByString",
    query="SELECT c FROM CharacterCode c "
            + "WHERE c.characterCode = :value"),
})
@NamedNativeQueries({
    @NamedNativeQuery(name="CharacterCode.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM character_code "
                    + "ORDER BY %s ",
                resultClass=CharacterCode.class)
})
public class CharacterCode extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="character_code")
	private String characterCode;

	//bi-directional many-to-one association to PtLocale
	@OneToMany(mappedBy="characterCode")
	private List<PtLocale> ptLocales;

	public CharacterCode() {
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