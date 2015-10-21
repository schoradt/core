package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;

@XmlRootElement
public class MultiplicityPojo extends OpenInfraMetaDataPojo {

    private Integer min;
    private Integer max;

    /* Default constructor */
    public MultiplicityPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public MultiplicityPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    protected void makePrimerHelper(PtLocale locale) {
        min = Integer.valueOf(-1);
        max = Integer.valueOf(-1);
    }

}
