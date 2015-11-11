package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the objects database table.
 *
 */
@Entity
@Table(name="openinfra_objects")
@NamedQueries({
	@NamedQuery(name="OpenInfraObject.findAll",
			query="SELECT o FROM OpenInfraObject o"),
	@NamedQuery(name="OpenInfraObject.count",
			query="SELECT COUNT(o) FROM OpenInfraObject o")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="OpenInfraObject.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM openinfra_objects "
                    + "ORDER BY %s ",
                resultClass=OpenInfraObject.class)
})
public class OpenInfraObject extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	private String name;

	//bi-directional many-to-one association to SubjectObject
	@OneToMany(mappedBy="objectBean")
	private List<SubjectObject> subjectObjects;

	public OpenInfraObject() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubjectObject> getSubjectObjects() {
		return this.subjectObjects;
	}

	public void setSubjectObjects(List<SubjectObject> subjectObjects) {
		this.subjectObjects = subjectObjects;
	}

	public SubjectObject addSubjectObject(SubjectObject subjectObject) {
		getSubjectObjects().add(subjectObject);
		subjectObject.setObjectBean(this);

		return subjectObject;
	}

	public SubjectObject removeSubjectObject(SubjectObject subjectObject) {
		getSubjectObjects().remove(subjectObject);
		subjectObject.setObjectBean(null);

		return subjectObject;
	}

}