package de.btu.openinfra.plugins.solr.enums;

/**
 * This enum maps the possible relational operators to the Solr syntax.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public enum RelationalOperatorEnum {

    EQUAL(""),
    GREATER_THAN("[%s TO *]"),
    SMALLER_THAN("[* TO %s]"),
    BETWEEN("[%s TO %s]");

    private String value;

    private RelationalOperatorEnum(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}
