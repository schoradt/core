package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class ServersPojo extends OpenInfraPojo {

    private String server;

    /* Default constructor */
    public ServersPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public ServersPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    protected void makePrimerHelper() {
        server = "";
    }

}
