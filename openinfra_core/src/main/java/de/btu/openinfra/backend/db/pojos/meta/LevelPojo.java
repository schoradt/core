package de.btu.openinfra.backend.db.pojos.meta;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class LevelPojo extends OpenInfraPojo {

    private String level;

    /* Default constructor */
    public LevelPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public LevelPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    protected void makePrimerHelper(PtLocale locale) {
        level = "";
    }

}
