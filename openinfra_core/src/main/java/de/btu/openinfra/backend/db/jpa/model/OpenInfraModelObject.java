package de.btu.openinfra.backend.db.jpa.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.annotations.ReturnInsert;
import org.eclipse.persistence.annotations.ReturnUpdate;

/**
 * This abstract class is manually added to the set of automatically generated
 * model classes by JPA/EclipseLink. This class provides attributes and
 * methods, which are equal for all generated subclasses. The database tables
 * of the generated subclasses must have the specified attributes (same name)
 * as columns.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@MappedSuperclass
public abstract class OpenInfraModelObject {

    @Id
    private UUID id;

    @ReturnInsert(returnOnly=true)
    // TODO updates the attribute 'xmin' of the model object after an update
    // operation of the model object (remove if it is not necessary)
    @ReturnUpdate
    @Convert(converter = de.btu.openinfra.backend.db.PostgresIntegerConverter.class)
    @Column(insertable = false)
    private Integer xmin;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getXmin() {
        return xmin;
    }

    public void setXmin(Integer xmin) {
        this.xmin = xmin;
    }

}
