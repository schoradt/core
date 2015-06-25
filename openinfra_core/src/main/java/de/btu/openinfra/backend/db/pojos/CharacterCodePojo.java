package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CharacterCodePojo extends OpenInfraPojo {
	
	private String characterCode;

	public String getCharacterCode() {
		return characterCode;
	}

	public void setCharacterCode(String characterCode) {
		this.characterCode = characterCode;
	}

}
