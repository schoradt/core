package de.btu.openinfra.backend.db.daos;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import de.btu.openinfra.backend.Reflection;
import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
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
     * @param classString               The name of the class as string.
     * @param schema                    The schema were the request came from.
     * @return OrderByPojo              The requested class name and a list of
     *                                  supported enum's.
     * @throws OpenInfraEntityException If the passed class name is empty or
     *                                  null or if the class does not support
     *                                  any orderBy types.
     */
    public static OrderByPojo read(
            OpenInfraSchemas schema, String classString) {
        // get all classes for the given schema
        List<String> schemaClasses = getClassNames(schema);

        // check if the requested class is part of the schema
        if (!schemaClasses.contains(classString)) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.NO_CLASS_IN_SCHEMA);
        }

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
     * names, depending on the requested schema, that support sorting.
     *
     * @param schema            The schema were the request came from.
     * @return OrderByNamesPojo A list of class names that support sorting.
     */
    public static OrderByNamesPojo getNames(OpenInfraSchemas schema) {
        OrderByNamesPojo pojo = new OrderByNamesPojo();

        // get all classes from the schema
        List<String> schemaClasses = new ArrayList<String>();

        // get all classes from the enum
        List<String> enumClasses = OpenInfraOrderByEnum.getAllObjectNames();

        // run through all classes, that were found in the package of the schema
        for (String c : getClassNames(schema)) {
            // if the enum contains the class
            if (enumClasses.contains(c)) {
                // add it to the list of the schema classes
                schemaClasses.add(c);
            }
        }

        // add the classes from the schema to the pojo
        pojo.setClassNames(schemaClasses);

        return pojo;
    }

    /**
     * This method retrieves all class names for the passed OpenInfraSchema.
     *
     * @param schema The schema name the classes should be retrieved for.
     * @return       A list of class names that are part of the passed schema.
     */
    private static List<String> getClassNames(OpenInfraSchemas schema) {

        String packagePath = "";
        List<Class<? extends OpenInfraPojo>> classes =
                new ArrayList<Class<? extends OpenInfraPojo>>();

        // retrieve all classes for the schema
        switch (schema) {
        case PROJECTS:
            packagePath = "de.btu.openinfra.backend.db.pojos.project";
            classes = Reflection.<OpenInfraPojo>findAllClasses(packagePath);
            // no break, because the project schema contains all classes of the
            // system schema
        case SYSTEM:
            Reflections reflections = new Reflections(
                new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage(
                        "de.btu.openinfra.backend.db.pojos")).
                        filterInputsBy(new FilterBuilder().
                        include("de\\.btu\\.openinfra\\.backend\\.db\\.pojos\\"
                                + "..*\\.class").
                        exclude("de\\.btu\\.openinfra\\.backend\\.db\\.pojos\\"
                                + "..*\\..*\\.class")).
                        setScanners(
                                new SubTypesScanner(false),
                                new ResourcesScanner(),
                                new TypeElementsScanner()));
            classes.addAll(
                    Reflection.<OpenInfraPojo>findAllClasses(reflections));
            break;
        case META_DATA:
            packagePath = "de.btu.openinfra.backend.db.pojos.meta";
            classes = Reflection.<OpenInfraPojo>findAllClasses(packagePath);
            break;
        case RBAC:
            packagePath = "de.btu.openinfra.backend.db.pojos.rbac";
            classes = Reflection.<OpenInfraPojo>findAllClasses(packagePath);
            break;
        default:
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.INTERNAL_SERVER_EXCEPTION);
        }

        List<String> resultClasses = new ArrayList<String>();

        // Remove interfaces and abstract classes from the class list and
        // removes the string "Pojo" from every class name.
        for(Class<? extends OpenInfraPojo> currentClass : classes) {
            if(Modifier.isAbstract(currentClass.getModifiers()) == false &&
                    currentClass.isInterface() == false) {
                resultClasses.add(currentClass.getSimpleName().replaceAll(
                        "Pojo", ""));
            }
        }

        return resultClasses;
    }
}
