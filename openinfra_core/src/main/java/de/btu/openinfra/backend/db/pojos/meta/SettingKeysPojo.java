package de.btu.openinfra.backend.db.pojos.meta;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public class SettingKeysPojo extends OpenInfraPojo {

    private String key;

    /* Default constructor */
    public SettingKeysPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public SettingKeysPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    protected void makePrimerHelper() {
        key = "";
    }

}
