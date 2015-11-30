package de.btu.openinfra.backend.enums;

/**
 * This enum maps the possible data types that are required for indexing.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public enum DataTypeEnum {

    BOOLEAN("boolean"),
    DATE("date"),
    FILE("file"),
    GEOMETRY("geometry(Geometry)"),
    GEOMETRYZ("geometry(GeometryZ)"),
    IMAGE("image"),
    INTEGER("integer"),
    NUMERIC("numeric"),
    TEXT("text"),
    URL("url"),
    VARCHAR("varchar");



    private String value;

    private DataTypeEnum(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}
