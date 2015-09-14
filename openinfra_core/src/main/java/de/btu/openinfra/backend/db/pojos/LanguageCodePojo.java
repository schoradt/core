package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class LanguageCodePojo extends OpenInfraPojo {

	private String languageCode;

	/* Default constructor */
    public LanguageCodePojo() {}

    /* Constructor that will set the id, trid and meta data automatically */
    public LanguageCodePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}
