package de.btu.openinfra.backend.db.pojos.project;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraMetaDataPojo;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;

@XmlRootElement
public class ProjectPojo extends OpenInfraMetaDataPojo {

    private UUID subprojectOf;
    private PtFreeTextPojo names;
    private PtFreeTextPojo descriptions;
    private long topicCharacteristicsCount;
    private long valueListsCount;

    /* Default constructor */
    public ProjectPojo() {}

    /* Constructor that will set the id, trid and meta data automatically */
    public ProjectPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public UUID getSubprojectOf() {
        return subprojectOf;
    }

    public void setSubprojectOf(UUID subprojectOf) {
        this.subprojectOf = subprojectOf;
    }

    public PtFreeTextPojo getNames() {
        return names;
    }

    public void setNames(PtFreeTextPojo names) {
        this.names = names;
    }

    public PtFreeTextPojo getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(PtFreeTextPojo descriptions) {
        this.descriptions = descriptions;
    }

	public long getTopicCharacteristicsCount() {
		return topicCharacteristicsCount;
	}

	public void setTopicCharacteristicsCount(long topicCharacteristicsCount) {
		this.topicCharacteristicsCount = topicCharacteristicsCount;
	}

	public long getValueListsCount() {
		return valueListsCount;
	}

	public void setValueListsCount(long valueListsCount) {
		this.valueListsCount = valueListsCount;
	}

}
