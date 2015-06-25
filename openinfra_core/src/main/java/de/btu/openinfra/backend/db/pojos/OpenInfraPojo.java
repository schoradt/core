package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

public abstract class OpenInfraPojo {
	
	private UUID uuid;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
