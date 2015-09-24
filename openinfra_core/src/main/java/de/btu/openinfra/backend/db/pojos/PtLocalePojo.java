package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class PtLocalePojo extends OpenInfraPojo {

    private String languageCode;
    private String countryCode;
    private String characterCode;

    /* Default constructor */
    public PtLocalePojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public PtLocalePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    @Override
    protected void makePrimerHelper() {
        languageCode = "";
        countryCode = "";
        characterCode = "";
    }

}
