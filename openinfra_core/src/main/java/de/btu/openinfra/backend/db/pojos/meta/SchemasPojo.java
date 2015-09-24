package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SchemasPojo extends OpenInfraPojo {

    private String schema;

    /* Default constructor */
    public SchemasPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public SchemasPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public void makePrimer() {
        schema = "";
    }

}
