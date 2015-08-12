package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the level database table.
 * 
 */
@Entity
@Table(schema="meta_data")
@NamedQuery(name="Level.findAll", query="SELECT l FROM Level l")
public class Level implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	private String level;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="levelBean")
	private List<Log> logs;

	public Level() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setLevelBean(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setLevelBean(null);

		return log;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}