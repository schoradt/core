package de.btu.openinfra.backend.enums;

/**
 * This enum maps the possible logic operators to the Solr syntax.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public enum LogicOperatorEnum {

    OR("OR"),
    AND("AND");

    private String value;

    private LogicOperatorEnum(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}
