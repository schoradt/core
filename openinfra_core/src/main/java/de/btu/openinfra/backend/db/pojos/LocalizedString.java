package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocalizedString {
	
	protected String characterString;
	protected PtLocalePojo locale;
	
	public String getCharacterString() {
		return characterString;
	}
	
	public void setCharacterString(String characterString) {
		this.characterString = characterString;
	}
	
	public PtLocalePojo getLocale() {
		return locale;
	}
	
	public void setLocale(PtLocalePojo locale) {
		this.locale = locale;
	}

}
