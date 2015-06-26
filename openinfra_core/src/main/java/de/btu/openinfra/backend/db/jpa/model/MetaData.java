package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the meta_data database table.
 *
 */
@Entity
@Table(name="meta_data")
@NamedQueries({
	@NamedQuery(name="MetaData.findAll", query="SELECT m FROM MetaData m"),
	@NamedQuery(name="MetaData.count", query="SELECT COUNT(m) FROM MetaData m")
})
public class MetaData implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="object_id")
	private UUID objectId;

	private String data;

	@Column(name="pk_column")
	private String pkColumn;

	@Column(name="table_name")
	private String tableName;

	public MetaData() {
	}

	public UUID getObjectId() {
		return this.objectId;
	}

	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}

	@Override
	public void setId(UUID id) {
		this.objectId = id;
	}

	@Override
    public UUID getId() {
	    return this.objectId;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPkColumn() {
		return this.pkColumn;
	}

	public void setPkColumn(String pkColumn) {
		this.pkColumn = pkColumn;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}