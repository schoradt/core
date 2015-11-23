package de.btu.openinfra.backend.db.daos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.jpa.model.Project;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.ValueListValue;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.exception.OpenInfraEntityException;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;

/**
 * This class represents the TopicCharacteristic and is used to access the
 * underlying layer generated by JPA.
 *
 * TODO this class is also very special.. verify that it fits to the new generic
 * class!
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class TopicCharacteristicDao
	extends OpenInfraValueDao<TopicCharacteristicPojo,
	TopicCharacteristic, Project> {

	/**
	 * This is the required constructor which calls the super constructor and
	 * in turn creates the corresponding entity manager.
	 *
	 * @param currentProjectId the current project id (this should be null when
	 *                         the system schema is selected)
	 * @param schema           the required schema
	 */
	public TopicCharacteristicDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema,
				TopicCharacteristic.class, Project.class);
	}

	/**
	 * This method returns a list of topic characteristics based on a text
	 * filter (wildcard: %).
	 *
	 * TODO generalize read method with filter for all available list methods
	 * (insert a generic method in openinfradao)
	 *
	 * @param locale the locale
	 * @param filter the filter as text
	 * @return       an ordered list of topic characteristics
	 */
	public List<TopicCharacteristicPojo> read(Locale locale, String filter) {
		// 1. Get the currently required locale object
		PtLocale pl = new PtLocaleDao(currentProjectId, schema).read(locale);
		// 2. Get the TopicCharacteristics (the logic is embedded in the
		//    named query)
		List<TopicCharacteristic> tcs =
				em.createNamedQuery(
						"TopicCharacteristic.findByDescription",
						TopicCharacteristic.class)
						.setParameter("value", pl)
						.setParameter("filter", filter)
						.getResultList();
		// 3. Map the model objects to POJOs
		Map<UUID, TopicCharacteristicPojo> tcp =
				new HashMap<UUID, TopicCharacteristicPojo>();
		MetaDataDao mdDao = new MetaDataDao(currentProjectId, schema);
		for(TopicCharacteristic tc : tcs) {
		    UUID id = tc.getId();
		    if (!tcp.containsKey(id)) {
		        tcp.put(id, mapToPojoStatically(locale, tc, mdDao));
		    }

		} // end for
		return new LinkedList<TopicCharacteristicPojo>(tcp.values());
	}

	@Override
	public TopicCharacteristicPojo mapToPojo(
			Locale locale,
			TopicCharacteristic tc) {
		return mapToPojoStatically(locale, tc,
		        new MetaDataDao(currentProjectId, schema));
	}

	/**
	 * This method implements the method mapToPojo in a static way.
	 *
	 * @param locale the requested language as Java.util locale
	 * @param tc     the model object
	 * @param mdDao  the meta data DAO
	 * @return       the POJO object when the model object is not null else null
	 */
	public static TopicCharacteristicPojo mapToPojoStatically(
			Locale locale,
			TopicCharacteristic tc,
			MetaDataDao mdDao) {
		if (tc != null) {
		    TopicCharacteristicPojo pojo =
		            new TopicCharacteristicPojo(tc, mdDao);

		    // TODO this will probably disable the count functionality but will
		    // avoid a NullPointerException. This must be reworked!
		    if (mdDao != null) {
    		    pojo.setTopicInstancesCount(
    		    		new TopicInstanceDao(
    		    				mdDao.currentProjectId, mdDao.schema)
    		    		.getCount(tc.getId()));
		    }

            // set the project if exists
            try {
                pojo.setProjectId(tc.getProject().getId());
            } catch (NullPointerException npe) { /* do nothing */ }
    		pojo.setTopic(ValueListValueDao.mapToPojoStatically(
    				locale,
    				tc.getValueListValue(),
    				null));
    		pojo.setDescriptions(PtFreeTextDao.mapToPojoStatically(
    				locale,
    				tc.getPtFreeText()));

    		return pojo;
		} else {
		    return null;
		}
	}

	@Override
	public MappingResult<TopicCharacteristic> mapToModel(
			TopicCharacteristicPojo pojo,
			TopicCharacteristic tc) {

	    PtFreeTextDao ptfDao =
                new PtFreeTextDao(currentProjectId, schema);

	    try {
	        // in case the description value is empty
            if (pojo.getDescriptions().getLocalizedStrings().get(0)
                    .getCharacterString() == "") {
                throw new OpenInfraEntityException(
                        OpenInfraExceptionTypes.MISSING_DESCRIPTION_IN_POJO);
            }

            // set the description
            tc.setPtFreeText(ptfDao.getPtFreeTextModel(pojo.getDescriptions()));
	    } catch (NullPointerException npe) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.MISSING_DESCRIPTION_IN_POJO);
        }

        try {
            // set the topic
            tc.setValueListValue(em.find(
                    ValueListValue.class, pojo.getTopic().getUuid()));

            // set the project (can be null for system database)
            if (pojo.getProjectId() != null) {
                tc.setProject(em.find(Project.class, pojo.getProjectId()));
            } else {
                // reset the project
                tc.setProject(null);
            }
	    } catch (NullPointerException npe) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.MISSING_DATA_IN_POJO);
        }

        // return the model as mapping result
        return new MappingResult<TopicCharacteristic>(tc.getId(), tc);
	}

}
