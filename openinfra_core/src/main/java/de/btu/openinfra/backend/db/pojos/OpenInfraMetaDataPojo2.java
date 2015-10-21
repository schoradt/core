package de.btu.openinfra.backend.db.pojos;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

public class OpenInfraMetaDataPojo2 extends OpenInfraPojo {

    private MetaDataPojo metaData;

    public OpenInfraMetaDataPojo2() {
        // TODO Auto-generated constructor stub
    }

    public OpenInfraMetaDataPojo2(OpenInfraModelObject modelObject) {
        super(modelObject);
        // TODO Auto-generated constructor stub
    }

    public MetaDataPojo getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataPojo metaData) {
        this.metaData = metaData;
    }

}
