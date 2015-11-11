package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class LoggerPojo extends OpenInfraPojo {

    private String logger;

    /* Default constructor */
    public LoggerPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public LoggerPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

}
