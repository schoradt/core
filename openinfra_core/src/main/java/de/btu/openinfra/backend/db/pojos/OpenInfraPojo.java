package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

public abstract class OpenInfraPojo {

    private UUID uuid;
    /**
     * This variable stands for the transaction id of the data object stored in
     * PostgreSQL in the xmin system column.
     */
    private int trid;

    /**
     * The definition of the non-argument default constructor.
     */
    protected OpenInfraPojo() {
    }

    /**
     * This constructor is used to set the id and the trid of the POJO object
     * automatically.
     *
     * @param modelObject
     *            the current model object
     */
    protected OpenInfraPojo(OpenInfraModelObject modelObject) {
        this.uuid = modelObject.getId();
        this.trid = modelObject.getXmin();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getTrid() {
        return trid;
    }

    public void setTrid(int trid) {
        this.trid = trid;
    }

    /**
     * This method converts the pojo object into a primer object.
     * A primer object is an empty but structured pojo object. The primer
     * object represents the structure of the pojo object without content.
     * This method and and the method 'makePrimerHelper' implement the
     * template pattern.
     */
    public void makePrimer() {
        uuid = null;
        trid = -1;
        // call primer method of the subclass
        makePrimerHelper();
    }

    /**
     * This method converts the subclass pojo object into a primer object.
     * A primer object is an empty but structured pojo object. The primer
     * object represents the structure of the pojo object without content.
     * This method and and the method 'makePrimer' implement the
     * template pattern.
     */
    protected abstract void makePrimerHelper();
}
