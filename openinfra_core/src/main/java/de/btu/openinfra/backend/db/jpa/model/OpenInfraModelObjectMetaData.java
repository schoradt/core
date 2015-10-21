package de.btu.openinfra.backend.db.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class OpenInfraModelObjectMetaData
    extends OpenInfraModelObject {

    @OneToOne(mappedBy="object")
    MetaData metaData;

    public OpenInfraModelObjectMetaData() {
        // TODO Auto-generated constructor stub
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

}
