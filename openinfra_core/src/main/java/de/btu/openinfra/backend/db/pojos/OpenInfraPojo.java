package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

public abstract class OpenInfraPojo {
	
	private UUID uuid;
	/**
	 * This variable stands for the transaction id of the data object stored
	 * in PostgreSQL in the xmin system column.
	 */
	private int trid;

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
}
