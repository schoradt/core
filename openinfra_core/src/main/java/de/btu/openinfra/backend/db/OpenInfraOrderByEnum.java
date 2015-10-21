package de.btu.openinfra.backend.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.jpa.model.Multiplicity;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.jpa.model.ValueListValue;

/**
 * This enumeration defines table columns which should be ordered. In addition a
 * list of model classes must be specified that will support this column for
 * sorting.
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
	        TopicCharacteristic.class.getSimpleName(),
	        ValueList.class.getSimpleName(),
	        ValueListValue.class.getSimpleName()))),
    /**
     * Defines the column for minimum multiplicity values
     */
    MIN_VALUE(Collections.unmodifiableList(Arrays.asList(
            Multiplicity.class.getSimpleName()))),
    /**
     * Defines the column for maximum multiplicity values
     */
    MAX_VALUE(Collections.unmodifiableList(Arrays.asList(
            Multiplicity.class.getSimpleName())));

	private List<String> lst;

    private OpenInfraOrderByEnum(List<String> lst) {
        this.lst = lst;
    }

    public List<String> getList() {
        return this.lst;
    }

}
