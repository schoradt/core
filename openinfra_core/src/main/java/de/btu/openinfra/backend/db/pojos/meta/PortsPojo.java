package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class PortsPojo extends OpenInfraPojo {

    private Integer port;

    /* Default constructor */
    public PortsPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public PortsPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    protected void makePrimerHelper() {
        port = Integer.valueOf(-1);
    }

}
