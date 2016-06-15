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
 * The persistent class for the country_code database table.
 *
 */
@Entity
@Table(name="country_code")
@NamedQueries({
	@NamedQuery(name="CountryCode.findAll",
		query="SELECT c FROM CountryCode c ORDER BY c.id"),
	@NamedQuery(name="CountryCode.findByString",
		query="SELECT c FROM CountryCode c "
				+ "WHERE c.countryCode = :value ORDER BY c.id"),
	@NamedQuery(name="CountryCode.count",
        query="SELECT COUNT(c) FROM CountryCode c ")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="CountryCode.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM country_code "
                    + "ORDER BY %s ",
                resultClass=CountryCode.class)
})
public class CountryCode extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="country_code")
	private String countryCode;

	//bi-directional many-to-one association to PtLocale
	@OneToMany(mappedBy="countryCode")
	private List<PtLocale> ptLocales;

	public CountryCode() {
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<PtLocale> getPtLocales() {
		return this.ptLocales;
	}

	public void setPtLocales(List<PtLocale> ptLocales) {
		this.ptLocales = ptLocales;
	}

	public PtLocale addPtLocale(PtLocale ptLocale) {
		getPtLocales().add(ptLocale);
		ptLocale.setCountryCode(this);

		return ptLocale;
	}

	public PtLocale removePtLocale(PtLocale ptLocale) {
		getPtLocales().remove(ptLocale);
		ptLocale.setCountryCode(null);

		return ptLocale;
	}

}