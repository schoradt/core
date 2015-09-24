package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class ValueListPojo extends OpenInfraMetaDataPojo {

    private PtFreeTextPojo names;
    private PtFreeTextPojo descriptions;

    /* Default constructor */
    public ValueListPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public ValueListPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
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

    @Override
    protected void makePrimerHelper() {
        names = new PtFreeTextPojo();
        names.makePrimerHelper();
        descriptions = new PtFreeTextPojo();
        descriptions.makePrimerHelper();
    }

}
