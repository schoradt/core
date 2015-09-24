package de.btu.openinfra.backend.db.pojos.meta;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SettingsPojo extends OpenInfraPojo {

    private SettingKeysPojo key;
    private String value;
    private Date updatedOn;
    private ProjectsPojo project;

    /* Default constructor */
    public SettingsPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public SettingsPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public SettingKeysPojo getKey() {
        return key;
    }

    public void setKey(SettingKeysPojo key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ProjectsPojo getProject() {
        return project;
    }

    public void setProject(ProjectsPojo project) {
        this.project = project;
    }

    @Override
    public void makePrimer() {
        key = new SettingKeysPojo();
        key.makePrimer();
        value = "";
        updatedOn = new Date();
        project = new ProjectsPojo();
        project.makePrimer();
    }

}
