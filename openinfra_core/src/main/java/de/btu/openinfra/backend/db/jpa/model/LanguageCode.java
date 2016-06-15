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
 * The persistent class for the language_code database table.
 *
 */
@Entity
@Table(name="language_code")
@NamedQueries({
	@NamedQuery(name="LanguageCode.findAll",
		query="SELECT l FROM LanguageCode l ORDER BY l.id"),
	@NamedQuery(name="LanguageCode.findByString",
		query="SELECT l FROM LanguageCode l "
				+ "WHERE l.languageCode = :value ORDER BY l.id"),
	@NamedQuery(name="LanguageCode.count",
        query="SELECT COUNT(l) FROM LanguageCode l ")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="LanguageCode.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM language_code "
                    + "ORDER BY %s ",
                resultClass=LanguageCode.class)
})
public class LanguageCode extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="language_code")
	private String languageCode;

	//bi-directional many-to-one association to PtLocale
	@OneToMany(mappedBy="languageCode")
	private List<PtLocale> ptLocales;

	public LanguageCode() {
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public List<PtLocale> getPtLocales() {
		return this.ptLocales;
	}

	public void setPtLocales(List<PtLocale> ptLocales) {
		this.ptLocales = ptLocales;
	}

	public PtLocale addPtLocale(PtLocale ptLocale) {
		getPtLocales().add(ptLocale);
		ptLocale.setLanguageCode(this);

		return ptLocale;
	}

	public PtLocale removePtLocale(PtLocale ptLocale) {
		getPtLocales().remove(ptLocale);
		ptLocale.setLanguageCode(null);

		return ptLocale;
	}

}