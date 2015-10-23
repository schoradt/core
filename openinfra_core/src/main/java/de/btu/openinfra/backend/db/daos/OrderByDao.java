package de.btu.openinfra.backend.db.daos;

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.pojos.OrderByNamesPojo;
import de.btu.openinfra.backend.db.pojos.OrderByPojo;
import de.btu.openinfra.backend.exception.OpenInfraEntityException;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;

public class OrderByDao {

    /* Default constructor */
    public OrderByDao() { }

    /**
     * This method returns a OrderByPojo that represents the name of the
     * requested class and a list of OpenInfraOrderByEnum's that will be
     * supported by the class.
     *
     * @param classString               The name of the class as string
     * @return OrderByPojo              The requested class name and a list of
     *                                  supported enum's
     * @throws OpenInfraEntityException If the passed class name is empty or
     *                                  null or if the class does not support
     *                                  any orderBy types
     */
    public static OrderByPojo read(String classString) {
        // check if the requested object string is set
        if (classString == null || classString.equals("")) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.MISSING_PARAMETER);
        }

        // check if the requested string is a class that support sorting
        if (!OpenInfraOrderByEnum.getAllObjectNames().contains(
                classString)) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.NO_SORT_TYPE);

        }

        OrderByPojo pojo = new OrderByPojo();
        // set the name of the class
        pojo.setClassName(classString);
        // set the list of the retrieved OpenInfraOrderByEnum's
        pojo.setOrderByTypes(
                OpenInfraOrderByEnum.getEnumsByProperty(classString));

        // if no result was returned throw an exception
        if (pojo.getOrderByTypes().size() == 0) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.NO_SORT_TYPE);
        }
        return pojo;
    }

    /**
     * This method returns a OrderByNamesPojo that contains a list of class
     * names that sorting.
     *
     * @return OrderByNamesPojo A list of class names that support sorting
     */
    public static OrderByNamesPojo getNames() {
        OrderByNamesPojo pojo = new OrderByNamesPojo();
        pojo.setClassNames(OpenInfraOrderByEnum.getAllObjectNames());
        return pojo;
    }
}
