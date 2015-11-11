package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class CredentialsPojo extends OpenInfraPojo {

    private String username;
    private String password;

    /* Default constructor */
    public CredentialsPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public CredentialsPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
