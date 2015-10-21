package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import javax.persistence.NoResultException;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.jpa.model.MetaData;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;
import de.btu.openinfra.backend.exception.OpenInfraEntityException;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;

/**
 * This class represents the MetaData and is used to access the underlying layer
 * generated by JPA.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */

public class MetaDataDao extends OpenInfraDao<MetaDataPojo, MetaData> {

    /**
     * This is the required constructor which calls the super constructor and in
     * turn creates the corresponding entity manager.
     *
     * @param currentProjectId the current project id (this should be null when
     *                         the system schema is selected)
     * @param schema           the required schema
     */
    public MetaDataDao(UUID currentProjectId, OpenInfraSchemas schema) {
        super(currentProjectId, schema, MetaData.class);
    }

    /**
     * This is a special read method for extracting a meta data model from the
     * database to a corresponding object id.
     *
     * @param objectId the object id
     * @return         the meta data model for the object id
     */
    public MetaData read(UUID objectId) {
        MetaData md = null;
        try {
            md = em.createNamedQuery(
                    "MetaData.findByObjectId", MetaData.class)
                    .setParameter("oId", objectId)
                    .getSingleResult();
        } catch (NoResultException nre) { /* do nothing */ }
        return md;
    }

    @Override
    public MetaDataPojo mapToPojo(Locale locale, MetaData md) {
        return mapPojoStatically(md);
    }

    /**
     * This method implements the method mapToPojo in a static way.
     *
     * @param at     the model object
     * @return       the POJO object when the model object is not null else null
     */
    public static MetaDataPojo mapPojoStatically(MetaData md) {
        if(md != null) {
            MetaDataPojo pojo = new MetaDataPojo(md);
            pojo.setObjectId(md.getObjectId());
            pojo.setTableName(md.getTableName());
            pojo.setPkColumn(md.getPkColumn());
            pojo.setData(md.getData());
            return pojo;
        } else {
            return null;
        }
    }

    @Override
    public MappingResult<MetaData> mapToModel(MetaDataPojo pojo, MetaData md) {
        mapToModelStatically(pojo, md);
        // return the model as mapping result
        return new MappingResult<MetaData>(md.getId(), md);
    }

    /**
     * This method implements the method mapToModel in a static way.
     * @param pojo the POJO object
     * @param md   the pre initialized model object
     * @return     return a corresponding JPA model object
     * @throws     OpenInfraEntityException
     */
    public static MetaData mapToModelStatically(
            MetaDataPojo pojo,
            MetaData md) {
        // create the return object
        MetaData resultM = null;

        try {
            // set the passed model object to the return object
            resultM = md;
            // create a new model object if the passed model object is null
            if (resultM == null) {
                resultM = new MetaData();
                resultM.setId(pojo.getUuid());
            }
            // set the object id
            resultM.setObjectId(pojo.getObjectId());
            // set the data
            resultM.setData(pojo.getData());
            // set the primary key column
            resultM.setPkColumn(pojo.getPkColumn());
            // set the table name
            resultM.setTableName(pojo.getTableName());
        } catch (NullPointerException npe) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.MISSING_DATA_IN_POJO);
        }
        return resultM;
    }

    /**
     * Creates an empty MetaDataPojo.
     * @return an empty MetaDataPojo
     */
    public MetaDataPojo newMetaData() {
       return new MetaDataPojo();
    }

}
