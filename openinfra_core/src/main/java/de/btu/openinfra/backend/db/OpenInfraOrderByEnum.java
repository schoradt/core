package de.btu.openinfra.backend.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.jpa.model.CharacterCode;
import de.btu.openinfra.backend.db.jpa.model.CountryCode;
import de.btu.openinfra.backend.db.jpa.model.LanguageCode;
import de.btu.openinfra.backend.db.jpa.model.Multiplicity;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.jpa.model.RelationshipType;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.jpa.model.ValueListValue;
import de.btu.openinfra.backend.db.jpa.model.meta.Credentials;
import de.btu.openinfra.backend.db.jpa.model.meta.Databases;
import de.btu.openinfra.backend.db.jpa.model.meta.Level;
import de.btu.openinfra.backend.db.jpa.model.meta.Logger;
import de.btu.openinfra.backend.db.jpa.model.meta.Ports;
import de.btu.openinfra.backend.db.jpa.model.meta.Schemas;
import de.btu.openinfra.backend.db.jpa.model.meta.Servers;
import de.btu.openinfra.backend.db.jpa.model.meta.SettingKeys;

/**
 * This enumeration defines table columns which should be ordered. In addition a
 * list of model classes must be specified that will support this column for
 * sorting.
 *
 * The enum must be written in the same way as the table column name that should
 * be sorted.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum OpenInfraOrderByEnum {

	/**
	 * Defines the name column.
	 */
	NAME(Collections.unmodifiableList(Arrays.asList(
	        AttributeType.class.getSimpleName(),
	        AttributeTypeGroup.class.getSimpleName(),
	        ValueList.class.getSimpleName(),
	        ValueListValue.class.getSimpleName()))),
	/**
	 * Defines the description column.
	 */
	DESCRIPTION(Collections.unmodifiableList(Arrays.asList(
	        AttributeType.class.getSimpleName(),
	        AttributeTypeGroup.class.getSimpleName(),
	        RelationshipType.class.getSimpleName(),
	        TopicCharacteristic.class.getSimpleName(),
	        ValueList.class.getSimpleName(),
	        ValueListValue.class.getSimpleName()))),
    /**
     * Defines the column for minimum multiplicity.
     */
    MIN_VALUE(Collections.unmodifiableList(Arrays.asList(
            Multiplicity.class.getSimpleName()))),
    /**
     * Defines the column for maximum multiplicity.
     */
    MAX_VALUE(Collections.unmodifiableList(Arrays.asList(
            Multiplicity.class.getSimpleName()))),
    /**
     * Defines the column for character codes.
     */
    CHARACTER_CODE(Collections.unmodifiableList(Arrays.asList(
            CharacterCode.class.getSimpleName(),
            PtLocale.class.getSimpleName()))),
    /**
     * Defines the column for country codes.
     */
    COUNTRY_CODE(Collections.unmodifiableList(Arrays.asList(
            CountryCode.class.getSimpleName(),
            PtLocale.class.getSimpleName()))),
	/**
     * Defines the column for language codes.
     */
    LANGUAGE_CODE(Collections.unmodifiableList(Arrays.asList(
            LanguageCode.class.getSimpleName(),
            PtLocale.class.getSimpleName()))),
    /**
     * Defines the column for credentials.
     */
    USERNAME(Collections.unmodifiableList(Arrays.asList(
            Credentials.class.getSimpleName()))),
    /**
     * Defines the column for databases.
     */
    DATABASE(Collections.unmodifiableList(Arrays.asList(
            Databases.class.getSimpleName()))),
    /**
     * Defines the column for levels.
     */
    LEVEL(Collections.unmodifiableList(Arrays.asList(
            Level.class.getSimpleName()))),
    /**
     * Defines the column for logger.
     */
    LOGGER(Collections.unmodifiableList(Arrays.asList(
            Logger.class.getSimpleName()))),
    /**
     * Defines the column for ports.
     */
    PORT(Collections.unmodifiableList(Arrays.asList(
            Ports.class.getSimpleName()))),
    /**
     * Defines the column for schema.
     */
    SCHEMA(Collections.unmodifiableList(Arrays.asList(
            Schemas.class.getSimpleName()))),
    /**
     * Defines the column for server.
     */
    SERVER(Collections.unmodifiableList(Arrays.asList(
            Servers.class.getSimpleName()))),
    /**
     * Defines the column for setting keys.
     */
    KEY(Collections.unmodifiableList(Arrays.asList(
            SettingKeys.class.getSimpleName())));


	private List<String> lst;

    private OpenInfraOrderByEnum(List<String> lst) {
        this.lst = lst;
    }

    public List<String> getList() {
        return this.lst;
    }

}
