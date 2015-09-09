package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the project database table.
 *
 */
@Entity
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="subproject_of")
	private Project project;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="project")
	private List<Project> projects;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="description")
	private PtFreeText ptFreeText1;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="name")
	private PtFreeText ptFreeText2;

	//bi-directional many-to-one association to TopicCharacteristic
	@OneToMany(mappedBy="project")
	private List<TopicCharacteristic> topicCharacteristics;

	public Project() {
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setProject(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setProject(null);

		return project;
	}

	public PtFreeText getPtFreeText1() {
		return this.ptFreeText1;
	}

	public void setPtFreeText1(PtFreeText ptFreeText1) {
		this.ptFreeText1 = ptFreeText1;
	}

	public PtFreeText getPtFreeText2() {
		return this.ptFreeText2;
	}

	public void setPtFreeText2(PtFreeText ptFreeText2) {
		this.ptFreeText2 = ptFreeText2;
	}

	public List<TopicCharacteristic> getTopicCharacteristics() {
		return this.topicCharacteristics;
	}

	public void setTopicCharacteristics(List<TopicCharacteristic> topicCharacteristics) {
		this.topicCharacteristics = topicCharacteristics;
	}

	public TopicCharacteristic addTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		getTopicCharacteristics().add(topicCharacteristic);
		topicCharacteristic.setProject(this);

		return topicCharacteristic;
	}

	public TopicCharacteristic removeTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		getTopicCharacteristics().remove(topicCharacteristic);
		topicCharacteristic.setProject(null);

		return topicCharacteristic;
	}

}