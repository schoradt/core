package de.btu.openinfra.backend.db.pojos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class PasswordBlacklistPojo extends OpenInfraPojo {

    private String password;

    public PasswordBlacklistPojo() {
    }

    public PasswordBlacklistPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void makePrimerHelper() {
        password = "";
    }

}