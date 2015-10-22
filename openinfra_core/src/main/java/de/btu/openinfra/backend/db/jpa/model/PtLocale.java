package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the pt_locale database table.
 *
 */
@Entity
@Table(name="pt_locale")
@NamedQueries({
	@NamedQuery(name="PtLocale.findAll", query="SELECT p FROM PtLocale p"),
	@NamedQuery(name="PtLocale.count", query="SELECT COUNT(p) FROM PtLocale p"),
	@NamedQuery(name="PtLocale.findByLocale", query=""
			+ "SELECT p "
			+ "FROM PtLocale p "
			+ "WHERE p.countryCode = :countryCode "
			+ "AND p.languageCode = :languageCode "),
    @NamedQuery(name="PtLocale.noCountry", query=""
            + "SELECT p "
            + "FROM PtLocale p "
            + "WHERE p.countryCode IS NULL "
            + "AND p.languageCode = :languageCode "),
	@NamedQuery(name="PtLocale.xx", query=""
            + "SELECT p "
            + "FROM PtLocale p "
            + "WHERE p.countryCode IS NULL ")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="PtLocale.findAllByLocaleAndOrder",
            query="SELECT pl.*, pl.xmin "
                    + "FROM pt_locale AS pl "
                    + "LEFT OUTER JOIN ( "
                        + "SELECT b.id, b.%s "
                        + "FROM pt_locale AS a, %s AS b) AS sq "
                        + "ON (pl.%s_id = sq.id) "
                        + "ORDER BY sq.%s ",
            resultClass=PtLocale.class)
})
public class PtLocale extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to LocalizedCharacterString
	@OneToMany(mappedBy="ptLocale")
	private List<LocalizedCharacterString> localizedCharacterStrings;

	//bi-directional many-to-one association to CharacterCode
	@ManyToOne
	@JoinColumn(name="character_code_id")
	private CharacterCode characterCode;

	//bi-directional many-to-one association to CountryCode
	@ManyToOne
	@JoinColumn(name="country_code_id")
	private CountryCode countryCode;

	//bi-directional many-to-one association to LanguageCode
	@ManyToOne
	@JoinColumn(name="language_code_id")
	private LanguageCode languageCode;

	public PtLocale() {
	}

	public List<LocalizedCharacterString> getLocalizedCharacterStrings() {
		return this.localizedCharacterStrings;
	}

	public void setLocalizedCharacterStrings(List<LocalizedCharacterString> localizedCharacterStrings) {
		this.localizedCharacterStrings = localizedCharacterStrings;
	}

	public LocalizedCharacterString addLocalizedCharacterString(LocalizedCharacterString localizedCharacterString) {
		getLocalizedCharacterStrings().add(localizedCharacterString);
		localizedCharacterString.setPtLocale(this);

		return localizedCharacterString;
	}

	public LocalizedCharacterString removeLocalizedCharacterString(LocalizedCharacterString localizedCharacterString) {
		getLocalizedCharacterStrings().remove(localizedCharacterString);
		localizedCharacterString.setPtLocale(null);

		return localizedCharacterString;
	}

	public CharacterCode getCharacterCode() {
		return this.characterCode;
	}

	public void setCharacterCode(CharacterCode characterCode) {
		this.characterCode = characterCode;
	}

	public CountryCode getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(CountryCode countryCode) {
		this.countryCode = countryCode;
	}

	public LanguageCode getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}

}