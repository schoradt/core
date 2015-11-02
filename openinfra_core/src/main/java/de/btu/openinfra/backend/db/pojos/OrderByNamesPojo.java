package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * This POJO represents a special object that shows a list of class names that
 * support sorting.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class OrderByNamesPojo {

    /* A list of names that support sorting */
    private List<String> classNames;

    /* Default constructor */
    public OrderByNamesPojo() { }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }
}
