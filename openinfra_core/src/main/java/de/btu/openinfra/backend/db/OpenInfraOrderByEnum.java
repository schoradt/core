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
import de.btu.openinfra.backend.db.jpa.model.meta.DatabaseConnection;
import de.btu.openinfra.backend.db.jpa.model.meta.Databases;
import de.btu.openinfra.backend.db.jpa.model.meta.Level;
import de.btu.openinfra.backend.db.jpa.model.meta.Log;
import de.btu.openinfra.backend.db.jpa.model.meta.Logger;
import de.btu.openinfra.backend.db.jpa.model.meta.Ports;
import de.btu.openinfra.backend.db.jpa.model.meta.Schemas;
import de.btu.openinfra.backend.db.jpa.model.meta.Servers;
import de.btu.openinfra.backend.db.jpa.model.meta.SettingKeys;
import de.btu.openinfra.backend.db.jpa.model.meta.Settings;
import de.btu.openinfra.backend.db.jpa.model.rbac.OpenInfraObject;
import de.btu.openinfra.backend.db.jpa.model.rbac.PasswordBlacklist;
import de.btu.openinfra.backend.db.jpa.model.rbac.Permission;
import de.btu.openinfra.backend.db.jpa.model.rbac.ProjectRelatedRole;
import de.btu.openinfra.backend.db.jpa.model.rbac.Role;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;

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
	        OpenInfraObject.class.getSimpleName(),
	        ProjectRelatedRole.class.getSimpleName(),
	        Role.class.getSimpleName(),
	        Subject.class.getSimpleName(),
	        ValueList.class.getSimpleName(),
	        ValueListValue.class.getSimpleName()))),
	/**
	 * Defines the description column.
	 */
	DESCRIPTION(Collections.unmodifiableList(Arrays.asList(
	        AttributeType.class.getSimpleName(),
	        AttributeTypeGroup.class.getSimpleName(),
	        Permission.class.getSimpleName(),
	        OpenInfraObject.class.getSimpleName(),
	        ProjectRelatedRole.class.getSimpleName(),
	        RelationshipType.class.getSimpleName(),
	        Role.class.getSimpleName(),
	        Subject.class.getSimpleName(),
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
     * Defines the column for login.
     */
    LOGIN(Collections.unmodifiableList(Arrays.asList(
            Subject.class.getSimpleName()))),
    /**
     * Defines the column for credentials.
     */
    USERNAME(Collections.unmodifiableList(Arrays.asList(
            Credentials.class.getSimpleName(),
            Log.class.getSimpleName(),
            DatabaseConnection.class.getSimpleName()))),
    /**
     * Defines the column for password.
     */
    PASSWORD(Collections.unmodifiableList(Arrays.asList(
            PasswordBlacklist.class.getSimpleName()))),
    /**
     * Defines the column for password created on.
     */
    PASSWORD_CREATED_ON(Collections.unmodifiableList(Arrays.asList(
            Subject.class.getSimpleName()))),
    /**
     * Defines the column for databases.
     */
    DATABASE(Collections.unmodifiableList(Arrays.asList(
            Databases.class.getSimpleName(),
            DatabaseConnection.class.getSimpleName()))),
    /**
     * Defines the column for levels.
     */
    LEVEL(Collections.unmodifiableList(Arrays.asList(
            Level.class.getSimpleName(),
            Log.class.getSimpleName()))),
    /**
     * Defines the column for logger.
     */
    LOGGER(Collections.unmodifiableList(Arrays.asList(
            Logger.class.getSimpleName(),
            Log.class.getSimpleName()))),
    /**
     * Defines the column for ports.
     */
    PORT(Collections.unmodifiableList(Arrays.asList(
            Ports.class.getSimpleName(),
            DatabaseConnection.class.getSimpleName()))),
    /**
     * Defines the column for schema.
     */
    SCHEMA(Collections.unmodifiableList(Arrays.asList(
            Schemas.class.getSimpleName(),
            DatabaseConnection.class.getSimpleName()))),
    /**
     * Defines the column for server.
     */
    SERVER(Collections.unmodifiableList(Arrays.asList(
            Servers.class.getSimpleName(),
            DatabaseConnection.class.getSimpleName()))),
    /**
     * Defines the column for setting keys.
     */
    KEY(Collections.unmodifiableList(Arrays.asList(
            Settings.class.getSimpleName(),
            SettingKeys.class.getSimpleName()))),
    /**
     * Defines the column for updated on.
     */
    UPDATED_ON(Collections.unmodifiableList(Arrays.asList(
            Settings.class.getSimpleName(),
            Subject.class.getSimpleName()))),
    /**
     * Defines the column for values.
     */
    VALUE(Collections.unmodifiableList(Arrays.asList(
            Settings.class.getSimpleName()))),
    /**
     * Defines the column for message.
     */
    MESSAGE(Collections.unmodifiableList(Arrays.asList(
            Log.class.getSimpleName()))),
    /**
     * Defines the column for created on.
     */
    CREATED_ON(Collections.unmodifiableList(Arrays.asList(
            Log.class.getSimpleName(),
            Subject.class.getSimpleName()))),
    /**
     * Defines the column for created on.
     */
    PERMISSION(Collections.unmodifiableList(Arrays.asList(
            Permission.class.getSimpleName()))),
    /**
     * Defines the column for mail.
     */
    MAIL(Collections.unmodifiableList(Arrays.asList(
            Subject.class.getSimpleName()))),
	/**
     * Defines the column for status.
     */
    STATUS(Collections.unmodifiableList(Arrays.asList(
            Subject.class.getSimpleName()))),
	/**
     * Defines the column for default language.
     */
    DEFAULT_LANGUAGE(Collections.unmodifiableList(Arrays.asList(
            Subject.class.getSimpleName()))),
	/**
     * Defines the column for last login on.
     */
    LAST_LOGIN_ON(Collections.unmodifiableList(Arrays.asList(
            Subject.class.getSimpleName())));


	private List<String> lst;

    private OpenInfraOrderByEnum(List<String> lst) {
        this.lst = lst;
    }

    public List<String> getList() {
        return this.lst;
    }

}
