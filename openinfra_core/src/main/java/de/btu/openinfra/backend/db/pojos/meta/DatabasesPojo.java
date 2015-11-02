package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class DatabasesPojo extends OpenInfraPojo {

    private String database;

    /* Default constructor */
    public DatabasesPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public DatabasesPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

}
