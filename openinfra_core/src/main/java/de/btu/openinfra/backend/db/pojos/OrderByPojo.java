package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;

@XmlRootElement
/**
 * This POJO represents a special object that shows the orderBy possibilities
 * for the requested class.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class OrderByPojo {

    /* The name of the class that was requested */
    private String className;

    /* A list of OpenInfraOrderByEnum's that will be supported by the requested
       class */
    private List<OpenInfraOrderByEnum> OrderByTypes;

    /* Default constructor */
    public OrderByPojo() { }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<OpenInfraOrderByEnum> getOrderByTypes() {
        return OrderByTypes;
    }

    public void setOrderByTypes(List<OpenInfraOrderByEnum> orderByTypes) {
        OrderByTypes = orderByTypes;
    }
}
