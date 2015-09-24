package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class CharacterCodePojo extends OpenInfraPojo {

    private String characterCode;

    /* Default constructor */
    public CharacterCodePojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public CharacterCodePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
    }

    @Override
    public void makePrimer() {
        characterCode = "";
    }

}
