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
 * The persistent class for the country_code database table.
 * 
 */
@Entity
@Table(name="country_code")
@NamedQueries({
	@NamedQuery(name="CountryCode.findAll", 
		query="SELECT c FROM CountryCode c"),
	@NamedQuery(name="CountryCode.findByString",
		query="SELECT c FROM CountryCode c "
				+ "WHERE c.countryCode = :value"),
	@NamedQuery(name="CountryCode.count",
        query="SELECT COUNT(c) FROM CountryCode c ")
})

public class CountryCode implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	@Column(name="country_code")
	private String countryCode;

	//bi-directional many-to-one association to PtLocale
	@OneToMany(mappedBy="countryCode")
	private List<PtLocale> ptLocales;

	public CountryCode() {
	}

	@Override
    public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
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
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}