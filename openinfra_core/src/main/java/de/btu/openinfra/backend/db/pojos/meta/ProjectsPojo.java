package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class ProjectsPojo extends OpenInfraPojo {

    private boolean isSubproject;
    private DatabaseConnectionPojo databaseConnection;

    /* Default constructor */
    public ProjectsPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public ProjectsPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public boolean getIsSubproject() {
        return isSubproject;
    }

    public void setIsSubproject(boolean isSubproject) {
        this.isSubproject = isSubproject;
    }

    public DatabaseConnectionPojo getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(DatabaseConnectionPojo databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    protected void makePrimerHelper() {
        isSubproject = false;
        databaseConnection = new DatabaseConnectionPojo();
        databaseConnection.makePrimerHelper();
    }

}
