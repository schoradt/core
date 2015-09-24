package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class CountryCodePojo extends OpenInfraPojo {

    private String CountryCode;

    /* Default constructor */
    public CountryCodePojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public CountryCodePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    @Override
    protected void makePrimerHelper() {
        CountryCode = "";
    }

}
