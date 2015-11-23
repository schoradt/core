package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public abstract class OpenInfraMetaDataPojo extends OpenInfraPojo {

    /**
     * This variable stands for the meta data of the data object stored in
     * PostgreSQL in the meta data table.
     */
    private String metaData;

    /**
     * The definition of the non-argument default constructor.
     */
    public OpenInfraMetaDataPojo() {}

    /**
     * This constructor is used to set the meta data of the POJO object and
     * redirect to the super constructor which will set the id and the trid as
     * well.
     *
     * @param modelObject
     *            the current model object
     * @param MetaDataDao
     *            the meta data DAO
     */
    public OpenInfraMetaDataPojo(
    		OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject);
        try {
            metaData = mdDao.read(modelObject.getId()).getData().toJSONString();
        } catch (NullPointerException npe) {
            /* do nothing */ }

    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

}
