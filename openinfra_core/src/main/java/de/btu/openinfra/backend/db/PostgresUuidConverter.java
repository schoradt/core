package de.btu.openinfra.backend.db;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Since the UUID type isn't part of the SQL standard, it is required to provide
 * an specific mapping or converter, respectively. This implementation is taken
 * from <a href="http://blog-ungarida.rhcloud.com/persisting-uuid-in-postgresql-using-jpa-eclipselink">
 * http://blog-ungarida.rhcloud.com/persisting-uuid-in-postgresql-using-jpa-eclipselink</a>
 * <p>
 * Set the annotation &#64;Converter(autoApply = true) in order to convert any 
 * UUID automatically. 
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Converter(autoApply = true)
public class PostgresUuidConverter implements AttributeConverter<UUID, UUID> {
	
	public UUID convertToDatabaseColumn(UUID uuid) {
		return uuid;
	}

	public UUID convertToEntityAttribute(UUID uuid) {
		return uuid;
	}
	
}
