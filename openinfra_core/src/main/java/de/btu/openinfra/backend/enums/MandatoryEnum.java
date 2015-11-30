package de.btu.openinfra.backend.enums;

/**
 * This enum maps the possible mandatory choices to the Solr syntax.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public enum MandatoryEnum {

    NEVER_MIND(""),
    MUST_NOT("-"),
    MUST("+");

    private String value;

    private MandatoryEnum(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}
