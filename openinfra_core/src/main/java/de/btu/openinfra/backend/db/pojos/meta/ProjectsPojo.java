package de.btu.openinfra.backend.db.pojos.meta;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class ProjectsPojo extends OpenInfraPojo {

    private boolean isSubproject;
    private UUID projectId;
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

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
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
