package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class DatabaseConnectionPojo extends OpenInfraPojo {

    private ServersPojo server;
    private PortsPojo port;
    private DatabasesPojo database;
    private SchemasPojo schema;
    private CredentialsPojo credentials;

    /* Default constructor */
    public DatabaseConnectionPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public DatabaseConnectionPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public ServersPojo getServer() {
        return server;
    }

    public void setServer(ServersPojo server) {
        this.server = server;
    }

    public PortsPojo getPort() {
        return port;
    }

    public void setPort(PortsPojo port) {
        this.port = port;
    }

    public DatabasesPojo getDatabase() {
        return database;
    }

    public void setDatabase(DatabasesPojo database) {
        this.database = database;
    }

    public SchemasPojo getSchema() {
        return schema;
    }

    public void setSchema(SchemasPojo schema) {
        this.schema = schema;
    }

    public CredentialsPojo getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsPojo credentials) {
        this.credentials = credentials;
    }

    @Override
    protected void makePrimerHelper() {
        server = new ServersPojo();
        server.makePrimerHelper();
        port = new PortsPojo();
        port.makePrimerHelper();
        database = new DatabasesPojo();
        database.makePrimerHelper();
        schema = new SchemasPojo();
        schema.makePrimerHelper();
        credentials = new CredentialsPojo();
        credentials.makePrimerHelper();
    }

}
