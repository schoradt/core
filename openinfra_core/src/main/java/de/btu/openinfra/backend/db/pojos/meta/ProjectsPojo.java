package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class ProjectsPojo extends OpenInfraPojo {

    private boolean isSubproject;
    private SettingsPojo settings;
    private DatabaseConnectionPojo databaseConnection;
    
    public boolean getIsSubproject() {
        return isSubproject;
    }
    
    public void setIsSubproject(boolean isSubproject) {
        this.isSubproject = isSubproject;
    }
    
    public SettingsPojo getSettings() {
        return settings;
    }

    public void setSettings(SettingsPojo settings) {
        this.settings = settings;
    }
    
    public DatabaseConnectionPojo getDatabaseConnection() {
        return databaseConnection;
    }
    
    public void setDatabaseConnection(DatabaseConnectionPojo databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
}
